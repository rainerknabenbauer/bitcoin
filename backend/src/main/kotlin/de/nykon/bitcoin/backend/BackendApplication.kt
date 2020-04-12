package de.nykon.bitcoin.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.servlet.config.annotation.CorsRegistry

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@SpringBootApplication
@EnableScheduling
open class BackendApplication

fun main() {
    runApplication<BackendApplication>()
}