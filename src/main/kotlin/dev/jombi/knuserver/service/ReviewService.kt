package dev.jombi.knuserver.service

import dev.jombi.knuserver.entity.Place
import dev.jombi.knuserver.entity.Review
import dev.jombi.knuserver.repository.ReviewRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ReviewService(private val reviewRepository: ReviewRepository) {
    fun getReviewsByPlaceId(uuid: UUID): List<Review> {
        return reviewRepository.findReviewsByPlaceId(uuid)
    }

    fun writeNewReview(place: Place, facility: Int, comment: String): Review? {
        return reviewRepository.save(Review(place = place, disabilityFacility = facility, comment = comment))
    }
}