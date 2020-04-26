package de.nykon.bitcoin.sdk.value.bitcoinde.showOrderbook

data class ShowOrderbookBody(
    val credits: Int,
    val errors: List<Any>,
    val orders: List<Order>
)

