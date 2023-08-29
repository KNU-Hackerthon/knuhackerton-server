package dev.jombi.knuserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KNUApplication

fun main(args: Array<String>) {
    runApplication<KNUApplication>(*args)
}
