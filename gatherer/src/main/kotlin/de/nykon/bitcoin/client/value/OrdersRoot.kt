package de.nykon.bitcoin.client.value

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class OrdersRoot {
    var orders: List<Order>? = null
    var errors: List<String>? = null
    var credits = 0

}