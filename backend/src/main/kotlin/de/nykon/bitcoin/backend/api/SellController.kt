package de.nykon.bitcoin.backend.api

import de.nykon.bitcoin.backend.sdk.SdkService
import de.nykon.bitcoin.backend.seller.SellerSchedulConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SellController(
        private val sellerSchedulConfig: SellerSchedulConfig,
        private val sdkService: SdkService
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping(path = ["/sell/{price}"])
    fun setExitPrice(@PathVariable price: Int): String {
        return "Not implemented"
    }

    /**
     * Starts selling all available Bitcoins.
     */
    @GetMapping(path = ["/sell/activate"])
    fun activateSeller() {
        this.sellerSchedulConfig.isActive = true
        log.info("Set seller schedule to ${this.sellerSchedulConfig.isActive}")
    }

    @GetMapping(path = ["/sell/deactivate"])
    fun deactivateSeller() {
        this.sellerSchedulConfig.isActive = false
        log.info("Set seller schedule to ${this.sellerSchedulConfig.isActive}")
        cleanup()
    }

    private fun cleanup() {
        sdkService.deleteAllOrders()
    }

}