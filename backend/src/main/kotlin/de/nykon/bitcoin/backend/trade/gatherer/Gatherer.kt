package de.nykon.bitcoin.backend.trade.gatherer

import de.nykon.bitcoin.backend.trade.gatherer.repository.BuyOrderbookRepository
import de.nykon.bitcoin.backend.trade.gatherer.repository.CompactBuyOrderbookRepository
import de.nykon.bitcoin.backend.trade.gatherer.repository.CompactSellOrderbookRepository
import de.nykon.bitcoin.backend.trade.gatherer.repository.KrakenSummaryRepository
import de.nykon.bitcoin.backend.trade.gatherer.repository.LongTradeHistoryRepository
import de.nykon.bitcoin.backend.trade.gatherer.repository.SellOrderbookRepository
import de.nykon.bitcoin.backend.trade.gatherer.repository.ShortTradeHistoryRepository
import de.nykon.bitcoin.backend.trade.gatherer.value.BuyOrderbook
import de.nykon.bitcoin.backend.trade.gatherer.value.CompactBuyOrderbook
import de.nykon.bitcoin.backend.trade.gatherer.value.CompactSellOrderbook
import de.nykon.bitcoin.backend.trade.gatherer.value.LongTermTrade
import de.nykon.bitcoin.backend.trade.gatherer.value.SellOrderbook
import de.nykon.bitcoin.backend.trade.gatherer.value.ShortTermTrade
import de.nykon.bitcoin.backend.trade.MyTradeRepository
import de.nykon.bitcoin.backend.trade.gatherer.value.KrakenSummary
import de.nykon.bitcoin.backend.trade.gatherer.value.Offer
import de.nykon.bitcoin.backend.trade.gatherer.value.Trade
import de.nykon.bitcoin.backend.trade.value.CompletedTrade
import de.nykon.bitcoin.sdk.bitcoinDe.ShowMyTrades
import de.nykon.bitcoin.sdk.bitcoinDe.ShowOrderbook
import de.nykon.bitcoin.sdk.bitcoinDe.ShowPublicTradeHistory
import de.nykon.bitcoin.sdk.cryptowatch.ShowKrakenSummary
import de.nykon.bitcoin.sdk.value.CryptoCurrency
import de.nykon.bitcoin.sdk.value.bitcoinde.Response
import de.nykon.bitcoin.sdk.value.bitcoinde.TransactionType
import de.nykon.bitcoin.sdk.value.bitcoinde.showMyTrades.TradeState
import de.nykon.bitcoin.sdk.value.bitcoinde.showOrderbook.Order
import de.nykon.bitcoin.sdk.value.bitcoinde.showOrderbook.ShowOrderbookBody
import org.jetbrains.kotlin.utils.addToStdlib.ifNotEmpty
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Collects data and stores it in the database for further processing.
 */
@Component
open class Gatherer(
        private val showOrderbook: ShowOrderbook,
        private val showPublicTradeHistory: ShowPublicTradeHistory,
        private val showMyTrades: ShowMyTrades,
        private val shortTradeHistoryRepository: ShortTradeHistoryRepository,
        private val longTradeHistoryRepository: LongTradeHistoryRepository,
        private val buyOrderbookRepository: BuyOrderbookRepository,
        private val sellOrderbookRepository: SellOrderbookRepository,
        private val compactBuyOrderbookRepository: CompactBuyOrderbookRepository,
        private val compactSellOrderbookRepository: CompactSellOrderbookRepository,
        private val myTradeRepository: MyTradeRepository,
        private val showKrakenSummary: ShowKrakenSummary,
        private val krakenSummaryRepository: KrakenSummaryRepository
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Scheduled(fixedDelay = 30000)
    fun storeMyTrades() {
        val lastTrade = myTradeRepository.findFirstByOrderByDateTimeDesc()

        val myTrades = showMyTrades.execute(lastTrade.dateTime.plusSeconds(1), TradeState.SUCCESSFUL)

        if (myTrades.body.trades.isNullOrEmpty()) {
            log.info("Stored 0 new trades of myself.")
        } else {
            myTrades.body.trades!!
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

            log.info("Collected ${myTrades.body.trades!!.size} new trades of myself.")
        }
    }

    /**
     * Collects the Public Trade History and appends it to the current log once a day.
     */
    @Scheduled(cron = "0 0 0 * * *")
    fun longTermPublicTradeHistory() {

        val publicTradeHistory = showPublicTradeHistory.all()

        val tradeHistory = publicTradeHistory.body.trades
                .map { trade ->
                    Trade(trade.date, trade.amount_currency_to_trade,
                            trade.price.setScale(2, RoundingMode.HALF_UP), trade.tid)
                }.toList()

        val longTermTrade = LongTermTrade(LocalDateTime.now(), tradeHistory)
        longTradeHistoryRepository.save(longTermTrade)

        log.info("Saved ${publicTradeHistory.body.trades.size} in Public Trade History " +
                "| credits: ${publicTradeHistory.body.credits}")
    }

    /**
     * Collects the last 24 hours of public trades.
     */
    @Scheduled(cron = "0 */5 * * * *" )
    fun shortTermPublicTradeHistory() {

        shortTradeHistoryRepository.deleteAll()
        val publicTradeHistory = showPublicTradeHistory.all()

        val tradeHistory = publicTradeHistory.body.trades
                .map { trade ->
                    Trade(trade.date, trade.amount_currency_to_trade,
                            trade.price.setScale(2, RoundingMode.HALF_UP), trade.tid)
                }
                .toList()

        val shortTermTrade = ShortTermTrade(LocalDateTime.now(), tradeHistory)
        shortTradeHistoryRepository.save(shortTermTrade)

        log.info("Saved ${publicTradeHistory.body.trades.size} in Public Trade History " +
                "| credits: ${publicTradeHistory.body.credits}")
    }

    /**
     * Collects the current BUY offers and stores the raw data.
     */
    @Scheduled(fixedDelay = 10000)
    fun buy() {
        val showOrderbook = showOrderbook.buy()

        val updatedOrderList = fixNumberFormatting(showOrderbook)

        val buyOrderbook = BuyOrderbook(LocalDateTime.now(), updatedOrderList)
        buyOrderbookRepository.save(buyOrderbook)

        log.info("Saved Raw Buy Orderbook | credits: ${showOrderbook.body.credits}")

        compactBuy(buyOrderbook)
    }

    /**
     * Collects the current SELL offers and stores the raw data.
     */
    @Scheduled(fixedDelay = 10000)
    fun sell() {
        val showOrderbook = showOrderbook.sell()

        val updatedOrderList = fixNumberFormatting(showOrderbook)

        val sellOrderbook = SellOrderbook(LocalDateTime.now(), updatedOrderList)
        sellOrderbookRepository.save(sellOrderbook)

        log.info("Saved Raw Sell Orderbook | credits: ${showOrderbook.body.credits}")

        compactSell(sellOrderbook)
    }

    private fun fixNumberFormatting(showOrderbook: Response<ShowOrderbookBody>): List<Order> {
        return showOrderbook.body.orders
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
    }

    /**
     * Extracts the most relevant fields and stores them separately.
     */
    @Async
    open fun compactBuy(buyOrderbook: BuyOrderbook) {

        val compactList = buyOrderbook.orders
                .map { order ->
                    Offer(order.trading_partner_information.username,
                            order.price,
                            order.max_amount_currency_to_trade)
                }.toList()

        val calculateWeightedAverage = calculateWeightedAverage(compactList)
        val compactTradeHistory = CompactBuyOrderbook(buyOrderbook.dateTime, calculateWeightedAverage, compactList)
        compactBuyOrderbookRepository.save(compactTradeHistory)

        log.info("Saved Compact Buy Orderbook")
    }

    /**
     * Extracts the most relevant fields and stores them separately.
     */
    @Async
    open fun compactSell(sellOrderbook: SellOrderbook) {

        val compactList = sellOrderbook.orders
                .map { order ->
                    Offer(order.trading_partner_information.username,
                            order.price,
                            order.max_amount_currency_to_trade)
                }.toList()

        val calculateWeightedAverage = calculateWeightedAverage(compactList)
        val compactTradeHistory = CompactSellOrderbook(sellOrderbook.dateTime, calculateWeightedAverage, compactList)
        compactSellOrderbookRepository.save(compactTradeHistory)

        log.info("Saved Compact Sell Orderbook")
    }

    /**
     * Calculate weighted average price.
     */
    fun calculateWeightedAverage(offers: List<Offer>): BigDecimal {
        val minimumSizeOffers = offers
                .filter { offer -> offer.amount.multiply(offer.price) > BigDecimal.valueOf(1000) }
                .subList(0, 3)

        val dividend = minimumSizeOffers.stream()
                .map { offer -> offer.price.multiply(offer.amount) }
                .reduce(BigDecimal.ZERO, BigDecimal::add)

        val divisor = minimumSizeOffers.stream()
                .map { offer -> offer.amount }
                .reduce(BigDecimal.ZERO, BigDecimal::add)

        return dividend.divide(divisor, 2, RoundingMode.HALF_UP)
    }

    /**
     * Collects the summary of Kraken BTC.
     */
    @Scheduled(fixedDelay = 60000)
    fun kraken() {

        val summary = showKrakenSummary.execute().body.result

        val krakenSummary = KrakenSummary(LocalDateTime.now(), summary.price.high, summary.price.last,
                summary.price.low, summary.price.change, summary.volume)

        krakenSummaryRepository.save(krakenSummary)
    }

}
