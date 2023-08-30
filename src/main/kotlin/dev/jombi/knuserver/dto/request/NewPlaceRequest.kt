package dev.jombi.knuserver.dto.request

data class NewPlaceRequest(
    val placeName: String,
    val latitude: Double,
    val longitude: Double,
)