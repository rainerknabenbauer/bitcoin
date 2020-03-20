package de.nykon.bitcoin.backend.controller

import de.nykon.bitcoin.backend.domain.TradingService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
open class TradingController(
        private val tradingService: TradingService
) {

    private val log: Logger = LoggerFactory.getLogger(TradingController::class.java)

    @GetMapping("/buy")
    fun getBuyDecision(): Boolean {
        return tradingService.getBuyDecision()
    }


}