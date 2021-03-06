package de.nykon.bitcoin.backend.api

import de.nykon.bitcoin.backend.trade.buyer.BuyerConfig
import de.nykon.bitcoin.backend.sdk.SdkService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BuyController(
        private val buyerConfig: BuyerConfig,
        private val sdkService: SdkService
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping(path = ["/buy/{price}"])
    fun setExitPrice(@PathVariable price: Int): String {
        return "Not implemented"
    }

    /**
     * Start selling all available bitcoins.
     */
    @GetMapping(path = ["/buy/activate"])
    fun activateBuyer() {
        this.buyerConfig.isActive = true
        log.info("Set buyer schedule to ${this.buyerConfig.isActive}")
    }

    /**
     * Stop selling all available bitcoins.
     */
    @GetMapping(path = ["/buy/deactivate"])
    fun deactivateBuyer() {
        this.buyerConfig.isActive = false
        log.info("Set buyer schedule to ${this.buyerConfig.isActive}")
        cleanup()
    }

    private fun cleanup() {
        sdkService.deleteAllOrders()
    }

}