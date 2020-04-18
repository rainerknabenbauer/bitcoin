package de.nykon.bitcoin.backend.controller

import de.nykon.bitcoin.backend.trading.schedules.config.BuyerSchedulConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BuyController(private val buyerSchedulConfig: BuyerSchedulConfig) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping(path = ["/buy/exitPrice/{price}"])
    fun setExitPrice(@PathVariable price: Int): String {
        return "Not implemented"
    }

    @GetMapping(path = ["/buy/activate"])
    fun activateBuyer() {
        this.buyerSchedulConfig.isActive = true
        log.info("Set seller schedule to ${this.buyerSchedulConfig.isActive}")
    }

    @GetMapping(path = ["/buy/deactivate"])
    fun deactivateBuyer() {
        this.buyerSchedulConfig.isActive = false
        log.info("Set seller schedule to ${this.buyerSchedulConfig.isActive}")
    }

}