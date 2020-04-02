package de.nykon.bitcoin.backend.controller

import de.nykon.bitcoin.backend.SupplyService
import de.nykon.bitcoin.backend.value.OrdersRoot
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.lang.NonNull
import org.springframework.web.bind.annotation.*

@RestController
class TradeController {

    private val log: Logger = LoggerFactory.getLogger(TradeController::class.java)

    @Autowired
    lateinit var supplyService: SupplyService

    @PostMapping(path = ["/supply"], produces= ["application/json"])
    fun saveOffers(@RequestBody @NonNull ordersRoot: OrdersRoot) {

        val size = ordersRoot.orders!!.size
        log.info("Received $size sales orders.")

        supplyService.storeSupply(ordersRoot)
    }

}