package de.nykon.bitcoin.sdk.value.showMyOrders

data class ShowMyOrdersBody(
    val credits: Int,
    val errors: List<Any>,
    val orders: List<MyOrder>?
)