package de.nykon.bitcoin.sdk.value.bitcoinde.showOrderbookCompact

data class Orders(
    val asks: List<Ask>,
    val bids: List<Bid>
)