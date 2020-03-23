package de.nykon.bitcoin.backend.controller

import de.nykon.bitcoin.backend.OffersService
import de.nykon.bitcoin.backend.value.OrdersRoot
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.lang.NonNull
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class OffersController {

    private val log: Logger = LoggerFactory.getLogger(OffersController::class.java)

    @Autowired
    lateinit var offersService: OffersService

    @PostMapping(path = ["/offers"], produces= ["application/json"])
    fun saveOffers(@RequestBody @NonNull ordersRoot: OrdersRoot) {

        val size = ordersRoot.orders!!.size
        log.info("Received $size orders.")

        offersService.storePrice(ordersRoot)
    }

}