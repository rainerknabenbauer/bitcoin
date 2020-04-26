package de.nykon.bitcoin.sdk.value.bitcoinde.showOrderbookCompact

data class ShowOrderbookCompactBody(
    val credits: Int,
    val errors: List<Any>,
    val orders: Orders,
    val trading_pair: String
)