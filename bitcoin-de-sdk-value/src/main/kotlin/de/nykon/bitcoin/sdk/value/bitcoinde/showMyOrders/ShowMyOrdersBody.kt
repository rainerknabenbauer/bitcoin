package de.nykon.bitcoin.sdk.value.bitcoinde.showMyOrders

data class ShowMyOrdersBody(
    val credits: Int,
    val errors: List<Any>,
    val orders: List<MyOrder>?
)