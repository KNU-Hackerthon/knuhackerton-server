package dev.jombi.knuserver.entity

import jakarta.persistence.*

@Entity
data class Review(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0L,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "placeId")
    val place: Place,
    val disabilityFacility: Int,
    @Column(length = 500)
    val comment: String,
)