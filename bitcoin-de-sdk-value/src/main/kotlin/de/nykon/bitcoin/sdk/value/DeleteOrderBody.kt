package de.nykon.bitcoin.sdk.value

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class DeleteOrderResponse {
    var errors: List<String>? = null
    var credits = 0
}