package de.nykon.bitcoin.sdk.value.createOrder

data class CreateOrderBody(
    val credits: Int,
    val errors: List<Any>,
    val order_id: String
)