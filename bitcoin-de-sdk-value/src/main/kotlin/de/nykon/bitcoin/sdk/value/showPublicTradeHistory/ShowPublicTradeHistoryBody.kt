package de.nykon.bitcoin.sdk.value.showPublicTradeHistory

open class ShowPublicTradeHistoryBody(
    val credits: Int,
    val errors: List<Any>,
    val trades: List<Trade>,
    val trading_pair: String
)