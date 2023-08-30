package dev.jombi.knuserver.dto.response

data class ReviewResponse(
    val placeName: String,
    val ramp: Float,
    val elevator: Float,
    val handiParking: Float,
    val handiToilet: Float,
    val brailleBlock: Float,
    val brailleMap: Float,
    val reviews: List<String>
)