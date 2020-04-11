package de.nykon.bitcoin.backend.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {

    var requestCounter = 0

    @GetMapping(path = ["/", ""])
    fun saveOffers(): String {
        requestCounter++
        return "hello world requested $requestCounter times"
    }

}