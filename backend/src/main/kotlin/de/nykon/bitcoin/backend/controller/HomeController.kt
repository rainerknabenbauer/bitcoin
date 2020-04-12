package de.nykon.bitcoin.backend.controller

import de.nykon.bitcoin.sdk.bitcoinDe.ShowOrderbook
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController() {

    @Value("\${bitcoin.apiKey}")
    private lateinit var apiKey: String

    @Value("\${bitcoin.apiSecret}")
    private lateinit var apiSecret: String

    var requestCounter = 0

    @GetMapping(path = ["/", ""])
    fun saveOffers(): String {
        requestCounter++
        return "hello world requested $requestCounter times"
    }

    @GetMapping(path = ["/showOrderbook"])
    fun showOrderbook(): ShowOrderbook {
        return ShowOrderbook(apiKey, apiSecret)
    }

}