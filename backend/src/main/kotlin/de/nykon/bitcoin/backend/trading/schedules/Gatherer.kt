package de.nykon.bitcoin.backend.trading.schedules

import de.nykon.bitcoin.backend.trading.LongTradeHistoryRepository
import de.nykon.bitcoin.backend.trading.ShortTradeHistoryRepository
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
        private val shortTradeHistoryRepository: ShortTradeHistoryRepository,
        private val longTradeHistoryRepository: LongTradeHistoryRepository
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Scheduled(cron = "0 0 0 * * *")
    fun longTermPublicTradeHistory() {

        val publicTradeHistory = showPublicTradeHistory.all()

        publicTradeHistory.body.trades
                .map { trade -> LongTermTrade(trade.amount_currency_to_trade, trade.date,
                        trade.price.setScale(2, RoundingMode.HALF_UP), trade.tid)  }
                .forEach { trade -> longTradeHistoryRepository.save(trade) }

        log.info("Saved ${publicTradeHistory.body.trades.size} in Public Trade History " +
                "| credits: ${publicTradeHistory.body.credits}")
    }

    @Scheduled(cron = "0 * * * * *" )
    fun shortTermPublicTradeHistory() {

        shortTradeHistoryRepository.deleteAll()
        val publicTradeHistory = showPublicTradeHistory.all()

        publicTradeHistory.body.trades
                .map { trade -> ShortTermTrade(trade.amount_currency_to_trade, trade.date,
                        trade.price.setScale(2, RoundingMode.HALF_UP), trade.tid)  }
                .forEach { trade -> shortTradeHistoryRepository.save(trade) }

        log.info("Saved ${publicTradeHistory.body.trades.size} in Public Trade History " +
                "| credits: ${publicTradeHistory.body.credits}")
    }


}
