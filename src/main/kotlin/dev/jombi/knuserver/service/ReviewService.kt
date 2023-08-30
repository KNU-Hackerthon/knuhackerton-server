package dev.jombi.knuserver.service

import dev.jombi.knuserver.entity.Place
import dev.jombi.knuserver.entity.Review
import dev.jombi.knuserver.repository.ReviewRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Transactional(readOnly = true)
@Service
class ReviewService(private val reviewRepository: ReviewRepository) {
    fun getReviewsByPlaceId(uuid: UUID): List<Review> {
        return reviewRepository.findReviewsByPlaceId(uuid)
    }

    @Transactional
    fun writeNewReview(place: Place, facility: Int, comment: String): Review? {
        return reviewRepository.save(Review(place = place, disabilityFacility = facility, comment = comment))
    }
}