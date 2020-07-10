package de.nykon.bitcoin.backend.trade

import de.nykon.bitcoin.backend.trade.value.CompletedTrade
import de.nykon.bitcoin.sdk.bitcoinDe.ShowMyTrades
import de.nykon.bitcoin.sdk.value.CryptoCurrency
import de.nykon.bitcoin.sdk.value.bitcoinde.TransactionType
import de.nykon.bitcoin.sdk.value.bitcoinde.showMyTrades.TradeState
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.annotation.PostConstruct

@Component
class MyTradeInitializer(
        private var myTradeRepository: MyTradeRepository,
        private val showMyTrades: ShowMyTrades
) {

    @Value("\${bitcoin.account.created}")
    private lateinit var startDate: String

    @PostConstruct
    fun loadData() {

        val trade = myTradeRepository.count()

        if (trade == 0L) {

            val successfulTrades = showMyTrades.execute(startDate, TradeState.SUCCESSFUL)

            successfulTrades.body.trades!!
                    .map { trade -> CompletedTrade(
                            CryptoCurrency.BTC,
                            LocalDateTime.parse(trade.successfully_finished_at, DateTimeFormatter.ISO_DATE_TIME),
                            TransactionType.valueOf(trade.type.toUpperCase()),
                            trade.price.setScale(2, RoundingMode.HALF_UP),
                            trade.amount_currency_to_trade_after_fee.setScale(8, RoundingMode.HALF_UP),
                            trade.volume_currency_to_pay.setScale(2, RoundingMode.HALF_UP)) }
                    .forEach {
                        myTradeRepository.save(it)
                    }
        }
    }
}