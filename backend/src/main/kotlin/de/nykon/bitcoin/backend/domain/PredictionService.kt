package de.nykon.bitcoin.backend.domain

import de.nykon.bitcoin.backend.repository.BitcoinRepository
import de.nykon.bitcoin.backend.repository.value.PriceBatch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
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

        return shortBuyPrediction() && mediumBuyPrediction() && longBuyPrediction()
    }

    /**
     * Takes the minimum amount (shortest time box) of data to predict price development.
     */
    private fun shortBuyPrediction(): Boolean {
        val top5 = repository.findTop5ByOrderByTimestampDesc()

        val start = top5.stream().min(Comparator.comparingLong { it?.timestamp!! }).get()
        val end = top5.stream().max(Comparator.comparingLong { it?.timestamp!! }).get()

        val averageDiff = start.average.minus(end.average)

        if (averageDiff > fixedLimits.getBuyShort()) {
            log.info("BUY because price has declined by $averageDiff")
            return true;
        }

        return false;

        return compute(top5)
    }

    /**
     * Takes the medium amount (medium time box) of data to predict price development.
     */
    private fun mediumBuyPrediction(): Boolean {
        val top10 = repository.findTop10ByOrderByTimestampDesc()

        return compute(top10)
    }

    /**
     * Takes the maximum amount (maximum time box) of data to predict price development.
     */
    private fun longBuyPrediction(): Boolean {
        val top20 = repository.findTop20ByOrderByTimestampDesc()

        return compute(top20)
    }

    /**
     * Compares both buy and sell offers to predict price development.
     */
    fun supplyAndDemandPrediction() {
        TODO("Not yet implemented")
    }


    /**
     *
     */
    private fun compute(batch: List<PriceBatch>): Boolean {

        val start = batch.stream().min(Comparator.comparingLong { it?.timestamp!! }).get()
        val end = batch.stream().max(Comparator.comparingLong { it?.timestamp!! }).get()

        val averageDiff = start.average.minus(end.average)

        if (averageDiff > fixedLimits.getBuyShort()) {
            log.info("BUY because price has declined by $averageDiff")
            return true;
        }

        return false;
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