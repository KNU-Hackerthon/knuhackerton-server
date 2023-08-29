package dev.jombi.knuserver.controller

import dev.jombi.knuserver.dto.response.BFListResponse
import dev.jombi.knuserver.service.PlaceService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class PlaceController(val placeService: PlaceService) {
    @GetMapping("/bf")
    fun getBFList(): ResponseEntity<BFListResponse> {
        return ResponseEntity.ok(BFListResponse(placeService.getBFPlaces()))
    }
}