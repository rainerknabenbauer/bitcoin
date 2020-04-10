package de.nykon.bitcoin.sdk.value.showOrderbook

data class ShowOrderbookBody(
    val credits: Int,
    val errors: List<Any>,
    val orders: List<Order>
)