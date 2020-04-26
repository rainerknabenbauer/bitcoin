package de.nykon.bitcoin.backend.reporting.value

import java.math.BigDecimal

/**
 * Currently a workaround as there is no automatic buy history available through API
 * nor is storing a successful trade currently implemented.
 */
data class CompletedTrade(
        private val cryptoCurrency: String = "BTC",
        private val price: BigDecimal,
        private val coins: BigDecimal,
        private val moneyAmount: BigDecimal
)