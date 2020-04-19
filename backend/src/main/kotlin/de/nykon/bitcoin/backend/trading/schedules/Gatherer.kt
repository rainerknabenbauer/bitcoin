package de.nykon.bitcoin.backend.trading.schedules

import de.nykon.bitcoin.backend.trading.BuyOrderbookRepository
import de.nykon.bitcoin.backend.trading.FlattenBuyOrderbookRepository
import de.nykon.bitcoin.backend.trading.FlattenedSellOrderbookRepository
import de.nykon.bitcoin.backend.trading.LongTradeHistoryRepository
import de.nykon.bitcoin.backend.trading.SellOrderbookRepository
import de.nykon.bitcoin.backend.trading.ShortTradeHistoryRepository
import de.nykon.bitcoin.backend.trading.value.BuyOrderbook
import de.nykon.bitcoin.backend.trading.value.FlattenedBuyOrderbook
import de.nykon.bitcoin.backend.trading.value.FlattenedSellOrderbook
import de.nykon.bitcoin.backend.trading.value.LongTermTrade
import de.nykon.bitcoin.backend.trading.value.SellOrderbook
import de.nykon.bitcoin.backend.trading.value.ShortTermTrade
import de.nykon.bitcoin.sdk.bitcoinDe.ShowOrderbook
import de.nykon.bitcoin.sdk.bitcoinDe.ShowPublicTradeHistory
import de.nykon.bitcoin.sdk.value.Response
import de.nykon.bitcoin.sdk.value.showOrderbook.Order
import de.nykon.bitcoin.sdk.value.showOrderbook.ShowOrderbookBody
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.math.RoundingMode
import java.time.LocalDateTime

@Component
open class Gatherer(
        private val showOrderbook: ShowOrderbook,
        private val showPublicTradeHistory: ShowPublicTradeHistory,
        private val shortTradeHistoryRepository: ShortTradeHistoryRepository,
        private val longTradeHistoryRepository: LongTradeHistoryRepository,
        private val buyOrderbookRepository: BuyOrderbookRepository,
        private val sellOrderbookRepository: SellOrderbookRepository,
        private val flattenBuyOrderbookRepository: FlattenBuyOrderbookRepository,
        private val flattenedSellOrderbookRepository: FlattenedSellOrderbookRepository
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Collects the Public Trade History and appends it to the current log once a day.
     */
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

    /**
     * Collects the last 24 hours every minute.
     */
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

    /**
     * Collects the current BUY offers and stores the raw data.
     */
    @Scheduled(fixedDelay = 15000)
    fun buy() {
        val showOrderbook = showOrderbook.buy()

        val updatedOrderList = fixNumberFormatting(showOrderbook)

        val buyOrderbook = BuyOrderbook(LocalDateTime.now(), updatedOrderList)
        buyOrderbookRepository.save(buyOrderbook)

        log.info("Saved Raw Buy Orderbook | credits: ${showOrderbook.body.credits}")

        flattenBuy(buyOrderbook)
    }

    /**
     * Collects the current SELL offers and stores the raw data.
     */
    @Scheduled(fixedDelay = 15000)
    fun sell() {
        val showOrderbook = showOrderbook.sell()

        val updatedOrderList = fixNumberFormatting(showOrderbook)

        val sellOrderbook = SellOrderbook(LocalDateTime.now(), updatedOrderList)
        sellOrderbookRepository.save(sellOrderbook)

        log.info("Saved Raw Sell Orderbook | credits: ${showOrderbook.body.credits}")

        flattenSell(sellOrderbook)
    }

    private fun fixNumberFormatting(showOrderbook: Response<ShowOrderbookBody>): List<Order> {
        val updatedOrderList = showOrderbook.body.orders
                .map { order ->
                    Order(
                            order.is_external_wallet_order,
                            order.max_amount_currency_to_trade,
                            order.max_volume_currency_to_pay.setScale(2, RoundingMode.HALF_UP),
                            order.min_amount_currency_to_trade,
                            order.min_volume_currency_to_pay.setScale(2, RoundingMode.HALF_UP),
                            order.order_id,
                            order.order_requirements,
                            order.order_requirements_fullfilled,
                            order.price.setScale(2, RoundingMode.HALF_UP),
                            order.trading_pair,
                            order.trading_partner_information,
                            order.type
                    )
                }.toList()
        return updatedOrderList
    }

    /**
     * Extracts the most relevant fields and stores them separately.
     */
    @Async
    open fun flattenBuy(buyOrderbook: BuyOrderbook) {

        buyOrderbook.orders
                .map { order ->
                    FlattenedBuyOrderbook(buyOrderbook.dateTime,
                            order.trading_partner_information.username,
                            order.price,
                            order.max_amount_currency_to_trade)
                }.forEach { order -> flattenBuyOrderbookRepository.save(order) }

        log.info("Saved Compact Buy Orderbook")
    }

    /**
     * Extracts the most relevant fields and stores them separately.
     */
    @Async
    open fun flattenSell(sellOrderbook: SellOrderbook) {

        sellOrderbook.orders
                .map { order ->
                    FlattenedSellOrderbook(sellOrderbook.dateTime,
                            order.trading_partner_information.username,
                            order.price,
                            order.max_amount_currency_to_trade)
                }.forEach { order -> flattenedSellOrderbookRepository.save(order) }

        log.info("Saved Compact Sell Orderbook")
    }

}
