package dev.jombi.knuserver.dto.request

data class NewReviewRequest(
    val ramp: Boolean,
    val elevator: Boolean,
    val handiParking: Boolean,
    val handiToilet: Boolean,
    val brailleBlock: Boolean,
    val brailleMap: Boolean,
    val comment: String,
)