package de.nykon.bitcoin.backend.domain

import de.nykon.bitcoin.backend.repository.BitcoinRepository
import de.nykon.bitcoin.backend.repository.value.PriceBatch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.stream.Collectors


/**
 * If value has dropped and now equals a plateau / slightly changes in price.
 */
@Service
class PredictionService(
        private val repository: BitcoinRepository,
        private val fixedLimits: FixedLimits,
        private val relativeLimits: RelativeLimits) {

    private val log: Logger = LoggerFactory.getLogger(PredictionService::class.java)

    fun getBuyDecision(): Boolean {

        val top5 = repository.findTop5ByOrderByTimestampDesc()
        val shortBuyPrediction = shortBuyPrediction(top5)

        val top10 = repository.findTop10ByOrderByTimestampDesc()
        val mediumBuyPrediction = mediumBuyPrediction(top10)

        val top20 = repository.findTop20ByOrderByTimestampDesc()
        val longBuyPrediction = longBuyPrediction(top20)

        return shortBuyPrediction && mediumBuyPrediction && longBuyPrediction
    }

    /**
     * Sells all available coins once price drop is registered.
     *
     * verb - used in conversation to stop someone from talking or to end oneselfs bad story.
     * - Urban Dictionary
     */
    fun getSellDecision(): Boolean {

        val top5 = repository.findTop5ByOrderByTimestampDesc()
        val shortSellPrediction = shortSellPrediction(top5)

        val top10 = repository.findTop10ByOrderByTimestampDesc()
        val mediumSellPrediction = mediumSellPrediction(top10)

        val top20 = repository.findTop20ByOrderByTimestampDesc()
        val longSellPrediction = longSellPrediction(top20)

        return shortSellPrediction && mediumSellPrediction && longSellPrediction
    }

    /**
     * Takes the minimum amount (shortest time box) of data to predict price development.
     */
    fun shortBuyPrediction(top5: List<PriceBatch>): Boolean {

        val start = top5.stream().min(Comparator.comparingLong { it?.timestamp!! }).get()
        val end = top5.stream().max(Comparator.comparingLong { it?.timestamp!! }).get()
        val threshold = fixedLimits.getBuyShort()

        val averageDiff = end.average.minus(start.average)

        if (averageDiff > threshold) {
            log.info("[YES][BUY_][SHORT_] because price has risen by $averageDiff with threshold $threshold")
            return true;
        }

        log.info("[NO_][BUY_][SHORT_] because price has risen by $averageDiff  with threshold $threshold")

        return false;
    }

    /**
     * Takes the medium amount (medium time box) of data to predict price development.
     */
    fun mediumBuyPrediction(top10: List<PriceBatch>): Boolean {

        val start = top10.stream().min(Comparator.comparingLong { it?.timestamp!! }).get()
        val end = top10.stream().max(Comparator.comparingLong { it?.timestamp!! }).get()
        val threshold = fixedLimits.getBuyMedium()

        val averageDiff = end.average.minus(start.average)

        if (averageDiff > threshold) {
            log.info("[YES][BUY_][MEDIUM] because price has risen by $averageDiff with threshold $threshold")
            return true;
        }

        log.info("[NO_][BUY_][MEDIUM] because price has risen by $averageDiff with threshold $threshold")

        return false;
    }

    /**
     * Takes the maximum amount (maximum time box) of data to predict price development.
     */
    fun longBuyPrediction(top20: List<PriceBatch>): Boolean {

        // Accumulate average over all data points
        // Decline if tendency is negative

        var accumulated = BigDecimal.ZERO
        top20.forEach { batch -> accumulated = accumulated.add(batch.average) }

        val end = top20.stream().max(Comparator.comparingLong { it?.timestamp!! }).get()

        val average = accumulated.divide(BigDecimal.valueOf(top20.size.toDouble()), 2, RoundingMode.HALF_UP)

        val averageDiff = end.average.minus(average).setScale(2, RoundingMode.HALF_UP)
        val stepsize = averageDiff.divide(BigDecimal.valueOf(top20.size.toDouble()), 2, RoundingMode.HALF_UP)

        val threshold = fixedLimits.getBuyStepsize()
        if (stepsize > threshold) {
            log.info("[YES][BUY_][LONG__] because price has risen each step by $stepsize with step size $threshold")
            return true;
        }

        log.info("[NO_][BUY_][LONG__] because price flattened with step size $stepsize and threshold at $threshold")

        return false;
    }

    /**
     * Takes the minimum amount (shortest time box) of data to predict price development.
     */
    fun shortSellPrediction(top5: List<PriceBatch>): Boolean {

        val start = top5.stream().min(Comparator.comparingLong { it?.timestamp!! }).get()
        val end = top5.stream().max(Comparator.comparingLong { it?.timestamp!! }).get()
        val threshold = fixedLimits.getSellShort()

        val averageDiff = end.average.minus(start.average)

        if (averageDiff < threshold) {
            log.info("[YES][SELL][SHORT_] because price has declined by $averageDiff with threshold $threshold")
            return true;
        }

        log.info("[NO_][SELL][SHORT_] because price has risen by $averageDiff with threshold $threshold")

        return false;
    }

    /**
     * Takes the medium amount (shortest time box) of data to predict price development.
     */
    fun mediumSellPrediction(top10: List<PriceBatch>): Boolean {

        val start = top10.stream().min(Comparator.comparingLong { it?.timestamp!! }).get()
        val end = top10.stream().max(Comparator.comparingLong { it?.timestamp!! }).get()
        val threshold = fixedLimits.getSellMedium()

        val averageDiff = end.average.minus(start.average)

        if (averageDiff < threshold) {
            log.info("[YES][SELL][MEDIUM] because price has declined by $averageDiff with threshold $threshold")
            return true;
        }

        log.info("[NO_][SELL][MEDIUM] because price has risen by $averageDiff with threshold $threshold")

        return false;
    }

    /**
     * Takes the long amount (shortest time box) of data to predict price development.
     */
    fun longSellPrediction(top20: List<PriceBatch>): Boolean {

        var accumulated = BigDecimal.ZERO
        top20.forEach { batch -> accumulated = accumulated.add(batch.average) }

        val end = top20.stream().max(Comparator.comparingLong { it?.timestamp!! }).get()

        val average = accumulated.divide(BigDecimal.valueOf(top20.size.toDouble()), 2, RoundingMode.HALF_UP)

        val averageDiff = end.average.minus(average)
        val stepsize = averageDiff.divide(BigDecimal.valueOf(top20.size.toDouble()), 2, RoundingMode.HALF_UP)

        val threshold = fixedLimits.getSellStepsize()
        if (stepsize < threshold) {
            log.info("[YES][SELL][LONG__] because price has declined each step by $stepsize with step size $threshold")
            return true;
        }

        log.info("[NO_][SELL][LONG__] because price flattened with step size $stepsize with threshold at $threshold")

        return false;
    }

    /**
     * Compares both buy and sell offers to predict price development.
     */
    fun supplyAndDemandPrediction() {
        TODO("Not yet implemented")
    }

    /**
     * Curve instability.
     */
    private fun volatility(batch: List<PriceBatch?>?) {

        val peak = batch?.stream()?.map {
            batchItem -> batchItem!!.average }?.collect(Collectors.toList())?.max()!!

        val valley = batch.stream().map {
            batchItem -> batchItem!!.average }?.collect(Collectors.toList())?.min()!!

    }

}