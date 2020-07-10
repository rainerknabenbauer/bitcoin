package de.nykon.bitcoin.backend.sdk

import de.nykon.bitcoin.backend.users.value.Authentication
import de.nykon.bitcoin.sdk.bitcoinDe.ShowOrderbook
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SdkController(private val sdkService: SdkService) {

    @PostMapping(path = ["/showOrderbook"])
    fun showOrderbook(@RequestBody authentication: Authentication): ShowOrderbook? {
        return sdkService.showOrderbook(authentication)
    }

    @GetMapping(path = ["/cleanup"])
    fun cleanup() {
        sdkService.deleteAllOrders()
    }
}