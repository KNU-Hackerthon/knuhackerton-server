package dev.jombi.knuserver.entity

import jakarta.persistence.Column
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

data class Review(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "placeId")
    val place: Place,
    val disabilityFacility: Int,
    @Column(length = 500)
    val comment: String,
)