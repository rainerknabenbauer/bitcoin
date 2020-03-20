package de.nykon.bitcoin.backend.controller

import de.nykon.bitcoin.backend.OffersService
import de.nykon.bitcoin.backend.value.OrdersRoot
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.lang.NonNull
import org.springframework.web.bind.annotation.*

@RestController
class OffersController {

    private val log: Logger = LoggerFactory.getLogger(OffersController::class.java)

    @Autowired
    lateinit var offersService: OffersService

    @GetMapping(path = ["/offers"])
    fun saveOffers(): String {

        return "hello world"
    }

    @PostMapping(path = ["/offers"], produces= ["application/json"])
    fun saveOffers(@RequestHeader("cycle-in-ms") @NonNull cycleInMs: Long,
                   @RequestBody @NonNull ordersRoot: OrdersRoot) {

        val size = ordersRoot.orders!!.size
        log.info("Received $size orders.")

        val cycleInSeconds: Int = cycleInMs.div(60000).toInt()

        offersService.storePrice(ordersRoot, cycleInSeconds)
    }

}