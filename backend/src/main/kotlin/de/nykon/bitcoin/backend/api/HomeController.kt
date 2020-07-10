package de.nykon.bitcoin.backend.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {

    private var requestCounter = 0

    @GetMapping(path = ["/", ""])
    fun saveOffers(): String {
        requestCounter++
        return "hello world requested $requestCounter times"
    }

}