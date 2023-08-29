package dev.jombi.knuserver.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.UUID

@Entity
data class Place(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(),
    val placeName: String,
    val latitude: Double,
    val longitude: Double,
    @Column(length = 2048, nullable = true)
    val location: String? = null,
    @Column(name = "is_bf_verified")
    val isBFVerified: Boolean = false,
)