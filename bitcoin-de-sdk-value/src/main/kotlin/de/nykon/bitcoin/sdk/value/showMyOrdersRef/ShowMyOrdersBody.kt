package de.nykon.bitcoin.sdk.value.showMyOrdersRef

data class ShowMyOrdersBody(
    val credits: Int,
    val errors: List<Any>,
    val orders: List<Order>,
    val page: Page
)