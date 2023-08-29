package dev.jombi.knuserver.initializer

import dev.jombi.knuserver.entity.Place
import dev.jombi.knuserver.repository.PlaceRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import kotlin.io.path.Path
import kotlin.io.path.readLines

@Component
class DataInitializer(val placeRepository: PlaceRepository) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        placeRepository.deleteAll()
        val n = Path("bf.csv").readLines().drop(1).map { it.split(',') }
            .map { Place(placeName = it[0], latitude = it[1].toDouble(), longitude = it[2].toDouble(), isBFVerified = true) }
        placeRepository.saveAll(n)
    }
}