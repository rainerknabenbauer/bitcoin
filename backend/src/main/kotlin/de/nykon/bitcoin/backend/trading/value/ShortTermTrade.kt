package de.nykon.bitcoin.backend.trading.value

import de.nykon.bitcoin.sdk.value.showPublicTradeHistory.Trade
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document(collection = "dailyPublicTradeHistory")
class ShortTermTrade(amount_currency_to_trade: BigDecimal, date: String, price: BigDecimal, tid: Int)
    : Trade(amount_currency_to_trade, date, price, tid) {
}