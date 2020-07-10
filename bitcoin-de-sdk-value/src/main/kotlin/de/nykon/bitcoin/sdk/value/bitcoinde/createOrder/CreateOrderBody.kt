package de.nykon.bitcoin.sdk.value.bitcoinde.createOrder

data class CreateOrderBody(
    val credits: Int,
    val errors: List<Any>,
    val order_id: String
)