package dev.jombi.knuserver.repository

import dev.jombi.knuserver.entity.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ReviewRepository : JpaRepository<Review, Int> {
    fun findReviewsByPlaceId(placeId: UUID): List<Review>
}