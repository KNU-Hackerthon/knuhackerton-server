package dev.jombi.knuserver.controller

import dev.jombi.knuserver.DisabilityFacility
import dev.jombi.knuserver.dto.request.NewPlaceRequest
import dev.jombi.knuserver.dto.request.NewReviewRequest
import dev.jombi.knuserver.dto.response.PlacesResponse
import dev.jombi.knuserver.dto.response.ReviewResponse
import dev.jombi.knuserver.entity.Place
import dev.jombi.knuserver.service.PlaceService
import dev.jombi.knuserver.service.ReviewService
import dev.jombi.knuserver.util.GEOCoding
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/place")
class PlaceController(val placeService: PlaceService, val reviewService: ReviewService) {
    @GetMapping("/bf")
    fun getBFList(): ResponseEntity<PlacesResponse> {
        return ResponseEntity.ok(PlacesResponse(placeService.getBFPlaces()))
    }

    @GetMapping("/{latitude}/{longitude}")
    fun getPlaces(@PathVariable latitude: String, @PathVariable longitude: String, @RequestParam km: Int = 2): ResponseEntity<PlacesResponse> {
        val list = placeService.getPlaces()
        val lat = latitude.toDoubleOrNull() ?: return ResponseEntity.badRequest().build()
        val lon = longitude.toDoubleOrNull() ?: return ResponseEntity.badRequest().build()
        val kmMapped = list.filter { GEOCoding.measureDistance(lat, lon, it.latitude, it.longitude) > km * 1000.0 }
        return ResponseEntity.ok(PlacesResponse(kmMapped))
    }

    @GetMapping("/{id}")
    fun getPlace(@PathVariable id: String): ResponseEntity<Place> {
        val uuid = try {
            UUID.fromString(id)
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.badRequest().build()
        }
        val data = placeService.getPlace(uuid)
        return data?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()
    }

    @PostMapping
    fun addNewPlace(@RequestBody request: NewPlaceRequest): ResponseEntity<Place> {
        return try {
            val place = placeService.writeNewPlace(request.placeName, request.latitude, request.longitude)
            place?.let { ResponseEntity.ok(it) } ?: ResponseEntity.unprocessableEntity().build()
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.internalServerError().build()
        }
    }

    @GetMapping("/{id}/review")
    fun getReviewById(@PathVariable id: String): ResponseEntity<ReviewResponse> {
        val uuid = try {
            UUID.fromString(id)
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.badRequest().build()
        }

        val place = placeService.getPlace(uuid)
            ?: return ResponseEntity.notFound().build()

        val reviews = reviewService.getReviewsByPlaceId(uuid)
        val avg = Array(6) { 0.0f }
        for (n in reviews.map { it.disabilityFacility }) {
            for (i in 0 until 6)
                avg[i] = avg[i] + if (DisabilityFacility.has(n, 1 shl i)) 1 else 0
        }
        val reviewSize = reviews.size.toFloat()
        for (i in 0 until 6)
            avg[i] = avg[i] / reviewSize

        val response = ReviewResponse(
            place.placeName,
            avg[0],
            avg[1],
            avg[2],
            avg[3],
            avg[4],
            avg[5],
            reviews.map { it.comment }
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping("/{id}/review")
    fun newReviewInId(@PathVariable id: String, @RequestBody request: NewReviewRequest): ResponseEntity<Unit> {
        val uuid = try {
            UUID.fromString(id)
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.badRequest().build()
        }

        val place = placeService.getPlace(uuid)
            ?: return ResponseEntity.notFound().build()

        var offset = 0
        if (request.ramp) offset = DisabilityFacility.add(offset, DisabilityFacility.RAMP_SAVED)
        if (request.elevator) offset = DisabilityFacility.add(offset, DisabilityFacility.ELEVATOR_SAVED)
        if (request.handiParking) offset = DisabilityFacility.add(offset, DisabilityFacility.HANDICAPPED_PARKING_LOT_SAVED)
        if (request.handiToilet) offset = DisabilityFacility.add(offset, DisabilityFacility.HANDICAPPED_TOILET_SAVED)
        if (request.brailleBlock) offset = DisabilityFacility.add(offset, DisabilityFacility.BRAILLE_BLOCK_SAVED)
        if (request.brailleMap) offset = DisabilityFacility.add(offset, DisabilityFacility.BRAILLE_MAP_SAVED)

        return try {
            val review = reviewService.writeNewReview(place, offset, request.comment)
            review?.let { ResponseEntity.ok(Unit) } ?: ResponseEntity.unprocessableEntity().build()
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.internalServerError().build()
        }

    }
}