package de.nykon.bitcoin.sdk.value.bitcoinde.showMyTrades

data class ShowMyTradesBody(
    val credits: Int,
    val errors: List<Any>,
    val trades: List<Trade>?
)