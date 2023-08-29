package dev.jombi.knuserver.service

import dev.jombi.knuserver.entity.Place
import dev.jombi.knuserver.repository.PlaceRepository
import org.springframework.stereotype.Service

@Service
class PlaceService(private val placeRepository: PlaceRepository) {
    fun getBFPlaces(): List<Place> {
        return placeRepository.getPlacesByBFVerifiedIs(true)
    }
}