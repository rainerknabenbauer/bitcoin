package de.nykon.bitcoin.backend.domain

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.math.BigDecimal

/**
 * Contains all limits the decision logic relies upon.
 */
@Component
class FixedLimits(
        @Value("limits.fixed.buyDiff") private val buyDiffString: String,
        @Value("limits.fixed.sellDiff") private val sellDiffString: String,
        @Value("limits.fixed.supplyAndDemandDiff") private val supplyAndDemandDiffString: String) {


    fun getBuyDiff(): BigDecimal {
        return BigDecimal.valueOf(buyDiffString.toDouble())
    }

    fun getSellDiff(): BigDecimal {
        return BigDecimal.valueOf(sellDiffString.toDouble())
    }

    fun getSupplyAndDemand(): BigDecimal {
        return BigDecimal.valueOf(supplyAndDemandDiffString.toDouble())
    }

}

