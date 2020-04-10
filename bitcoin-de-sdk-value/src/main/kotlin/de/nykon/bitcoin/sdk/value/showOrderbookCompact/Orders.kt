package de.nykon.bitcoin.sdk.value.showOrderbookCompact

data class Orders(
    val asks: List<Ask>,
    val bids: List<Bid>
)