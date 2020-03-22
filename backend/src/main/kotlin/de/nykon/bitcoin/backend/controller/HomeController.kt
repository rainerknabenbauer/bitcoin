package de.nykon.bitcoin.backend.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {

    @GetMapping(path = ["/", ""])
    fun saveOffers(): String {

        return "hello world"
    }

}