package de.nykon.bitcoin.backend.controller

import de.nykon.bitcoin.OrdersRoot
import de.nykon.bitcoin.backend.OffersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OffersController {

    @Autowired
    lateinit var offersService: OffersService

    @PostMapping(path = ["/offers"])
    fun saveOffers(@RequestBody ordersRoot: OrdersRoot) {

        offersService.storePrice(ordersRoot)
    }

}