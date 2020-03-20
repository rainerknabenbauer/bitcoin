package de.nykon.bitcoin.backend.domain

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.math.BigDecimal

/**
 * Contains all limits the decision logic relies upon.
 */
@Component
class FixedLimits(
        @Value("limits.fixed.buy.short") private val buyShort: String,
        @Value("limits.fixed.buy.medium") private val buyMedium: String,
        @Value("limits.fixed.buy.long") private val buyLong: String,

        @Value("limits.fixed.sell.short") private val sellShort: String,
        @Value("limits.fixed.sell.medium") private val sellMedium: String,
        @Value("limits.fixed.sell.long") private val sellLong: String,

        @Value("limits.fixed.supplyAndDemandDiff") private val supplyAndDemandDiffString: String) {


    fun getBuyShort(): BigDecimal {
        return BigDecimal.valueOf(buyShort.toDouble())
    }

    fun getBuyMedium(): BigDecimal {
        return BigDecimal.valueOf(buyMedium.toDouble())
    }

    fun getBuyLong(): BigDecimal {
        return BigDecimal.valueOf(buyLong.toDouble())
    }

    fun getSellShort(): BigDecimal {
        return BigDecimal.valueOf(sellShort.toDouble())
    }

    fun getSellMedium(): BigDecimal {
        return BigDecimal.valueOf(sellMedium.toDouble())
    }

    fun getSellLong(): BigDecimal {
        return BigDecimal.valueOf(sellLong.toDouble())
    }

    fun getSupplyAndDemand(): BigDecimal {
        return BigDecimal.valueOf(supplyAndDemandDiffString.toDouble())
    }

}

