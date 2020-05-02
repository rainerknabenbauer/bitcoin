package de.nykon.bitcoin.backend.trade.gatherer.value

import de.nykon.bitcoin.sdk.value.bitcoinde.showPublicTradeHistory.ShowPublicTradeHistoryBody
import de.nykon.bitcoin.sdk.value.bitcoinde.showPublicTradeHistory.Trade
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "snapshotPublicTradeHistory")
class SnapshotPublicTradeHistory(credits: Int, errors: List<Any>, trades: List<Trade>, trading_pair: String)
    : ShowPublicTradeHistoryBody(credits, errors, trades, trading_pair) {
}