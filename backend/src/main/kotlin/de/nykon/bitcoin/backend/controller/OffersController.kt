package de.nykon.bitcoin.backend.controller

import de.nykon.bitcoin.OrdersRoot
import de.nykon.bitcoin.backend.OffersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
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