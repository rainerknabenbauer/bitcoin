package de.nykon.bitcoin.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling


@SpringBootApplication
@EnableScheduling
open class BackendApplication

fun main() {
    runApplication<BackendApplication>()
}