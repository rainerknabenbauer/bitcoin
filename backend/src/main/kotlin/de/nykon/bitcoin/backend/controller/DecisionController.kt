package de.nykon.bitcoin.backend.controller

import de.nykon.bitcoin.backend.domain.PredictionService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
open class DecisionController(
        private val predictionService: PredictionService
) {

    private val log: Logger = LoggerFactory.getLogger(DecisionController::class.java)

    @GetMapping(path = ["/predictions/buy"])
    fun getBuyDecision(): Boolean {
        log.info(LocalDateTime.now().toString() + " : Buy request received")
        return predictionService.getBuyDecision()
    }

    @GetMapping(path = ["/predictions/sell"])
    fun getSellDecision(): Boolean {
        log.info(LocalDateTime.now().toString() + " : Sell request received")
        return predictionService.getSellDecision()
    }


}