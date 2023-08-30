package dev.jombi.knuserver.service

import dev.jombi.knuserver.entity.Place
import dev.jombi.knuserver.repository.PlaceRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Transactional(readOnly = true)
@Service
class PlaceService(private val placeRepository: PlaceRepository) {
    fun getBFPlaces(): List<Place> {
        return placeRepository.getVerifiedPlace(true)
    }

    fun getPlace(uuid: UUID): Place? {
        return placeRepository.findByIdOrNull(uuid)
    }

    fun getPlaces(): List<Place> {
        return placeRepository.getVerifiedPlace(false)
    }

    @Transactional
    fun writeNewPlace(name: String, latitude: Double, longitude: Double): Place? {
        return placeRepository.save(Place(placeName = name, longitude = longitude, latitude = latitude))
    }
}