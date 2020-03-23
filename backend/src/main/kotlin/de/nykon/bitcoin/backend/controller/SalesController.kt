package de.nykon.bitcoin.backend.controller

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SalesController {

    @PostMapping(path = ["/sales/exitPrice/{price}"])
    fun setExitPrice(@PathVariable price: Int): String {
        return "Not implemented"
    }



}