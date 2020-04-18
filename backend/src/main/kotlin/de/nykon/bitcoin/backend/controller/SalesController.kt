package de.nykon.bitcoin.backend.controller

import de.nykon.bitcoin.backend.trading.schedules.config.SellerSchedulConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SalesController(private val sellerSchedulConfig: SellerSchedulConfig) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping(path = ["/sales/exitPrice/{price}"])
    fun setExitPrice(@PathVariable price: Int): String {
        return "Not implemented"
    }

    @GetMapping(path = ["/sales/activate"])
    fun activateSeller() {
        this.sellerSchedulConfig.isActive = true
        log.info("Set seller schedule to ${this.sellerSchedulConfig.isActive}")
    }

    @GetMapping(path = ["/sales/deactivate"])
    fun deactivateSeller() {
        this.sellerSchedulConfig.isActive = false
        log.info("Set seller schedule to ${this.sellerSchedulConfig.isActive}")
    }

}