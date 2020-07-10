package de.nykon.stundenzettel.api

import de.nykon.stundenzettel.stunden.UberstundenService
import de.nykon.stundenzettel.stunden.value.Uberstunden
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UberstundenController(
        val uberstundenService: UberstundenService
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping(path = ["/buy/{price}"])
    fun setExitPrice(@PathVariable uberstunden: Uberstunden): String {
        return uberstundenService.save(uberstunden)
    }

}