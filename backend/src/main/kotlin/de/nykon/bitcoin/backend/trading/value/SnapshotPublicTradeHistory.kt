package de.nykon.bitcoin.backend.trading.value

import de.nykon.bitcoin.sdk.value.showPublicTradeHistory.ShowPublicTradeHistoryBody
import de.nykon.bitcoin.sdk.value.showPublicTradeHistory.Trade
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "snapshotPublicTradeHistory")
class SnapshotPublicTradeHistory(credits: Int, errors: List<Any>, trades: List<Trade>, trading_pair: String)
    : ShowPublicTradeHistoryBody(credits, errors, trades, trading_pair) {
}