package dev.jombi.knuserver.initializer

import dev.jombi.knuserver.entity.Place
import dev.jombi.knuserver.entity.Review
import dev.jombi.knuserver.repository.PlaceRepository
import dev.jombi.knuserver.repository.ReviewRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import kotlin.io.path.Path
import kotlin.io.path.readLines

val LOGGER = LoggerFactory.getLogger(DataInitializer::class.java)

@Component
class DataInitializer(val placeRepository: PlaceRepository, val reviewRepository: ReviewRepository) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        val measure = System.currentTimeMillis()
        val n = Path("bf.csv").readLines().drop(1).map { it.split(',') }
        val places = arrayListOf<Place>()
        for ((name, latitude, longitude) in n) {
            if (placeRepository.existsPlaceByPlaceNameContains(name)) {
                LOGGER.warn("Data already exists: {}. ignoring...", name)
                continue
            }
            places.add(placeRepository.save(Place(placeName = name, latitude = latitude.toDouble(), longitude = longitude.toDouble(), isBFVerified = true)))
            LOGGER.info("Added place: {}.", name)
        }
        val reviews = arrayListOf<Review>()
        for (place in places) {
            if (reviewRepository.findReviewsByPlaceId(place.id).isEmpty())
                reviews.add(Review(place = place, disabilityFacility = 0.inv(), comment = ""))
        }
        reviewRepository.saveAllAndFlush(reviews)
        LOGGER.info("Initialize finished with {} ms", System.currentTimeMillis() - measure)
    }
}