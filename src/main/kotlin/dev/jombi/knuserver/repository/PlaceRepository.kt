package dev.jombi.knuserver.repository

import dev.jombi.knuserver.entity.Place
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PlaceRepository : JpaRepository<Place, UUID> {
    @Query("SELECT place FROM Place place")
    fun getPlacesByBFVerifiedIs(@Param("isBF") isBF: Boolean): List<Place>
}