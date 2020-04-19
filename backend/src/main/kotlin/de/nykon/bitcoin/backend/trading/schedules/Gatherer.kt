package de.nykon.bitcoin.backend.trading.schedules

import de.nykon.bitcoin.backend.trading.PublicTradeHistoryRepository
import de.nykon.bitcoin.backend.trading.value.LongTermTrade
import de.nykon.bitcoin.backend.trading.value.ShortTermTrade
import de.nykon.bitcoin.sdk.bitcoinDe.ShowMyOrders
import de.nykon.bitcoin.sdk.bitcoinDe.ShowOrderbook
import de.nykon.bitcoin.sdk.bitcoinDe.ShowPublicTradeHistory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.math.RoundingMode

@Component
class Gatherer(
        private val showMyOrders: ShowMyOrders,
        private val showOrderbook: ShowOrderbook,
        private val showPublicTradeHistory: ShowPublicTradeHistory,
        private val publicTradeHistoryRepository: PublicTradeHistoryRepository
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Scheduled(cron = "0 0 0 * * *")
    fun longTermPublicTradeHistory() {

        val publicTradeHistory = showPublicTradeHistory.all()

        publicTradeHistory.body.trades
                .map { trade -> LongTermTrade(trade.amount_currency_to_trade, trade.date,
                        trade.price.setScale(2, RoundingMode.HALF_UP), trade.tid)  }
                .forEach { trade -> publicTradeHistoryRepository.save(trade) }

        log.info("Saved ${publicTradeHistory.body.trades.size} in Public Trade History")
    }

    @Scheduled(cron = "0 * * * * *" )
    fun shortTermPublicTradeHistory() {
        publicTradeHistoryRepository.deleteAll()

        val publicTradeHistory = showPublicTradeHistory.all()

        publicTradeHistory.body.trades
                .map { trade -> ShortTermTrade(trade.amount_currency_to_trade, trade.date,
                        trade.price.setScale(2, RoundingMode.HALF_UP), trade.tid)  }
                .forEach { trade -> publicTradeHistoryRepository.save(trade) }

        log.info("Saved ${publicTradeHistory.body.trades.size} in Public Trade History")
    }


}
