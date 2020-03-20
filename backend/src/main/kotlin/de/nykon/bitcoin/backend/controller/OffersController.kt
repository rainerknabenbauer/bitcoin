package de.nykon.bitcoin.backend.controller

import de.nykon.bitcoin.backend.OffersService
import de.nykon.bitcoin.backend.value.OrdersRoot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.lang.NonNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OffersController {

    @Autowired
    lateinit var offersService: OffersService

    @GetMapping(path = ["/offers"])
    fun saveOffers(): String {

        return "hello world"
    }

    @PostMapping(path = ["/offers"], produces= ["application/json"])
    fun saveOffers(@RequestBody @NonNull ordersRoot: OrdersRoot) {

        offersService.storePrice(ordersRoot)
    }

}