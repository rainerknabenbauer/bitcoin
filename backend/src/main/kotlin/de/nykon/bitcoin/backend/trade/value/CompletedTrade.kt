package de.nykon.bitcoin.backend.trade.value

import de.nykon.bitcoin.sdk.value.CryptoCurrency
import de.nykon.bitcoin.sdk.value.bitcoinde.TransactionType
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Currently a workaround as there is no automatic buy history available through API
 * nor is storing a successful trade currently implemented.
 */
@Document(collection = "myTradeHistory")
data class CompletedTrade(
        private val cryptoCurrency: CryptoCurrency,
        private val dateTime: LocalDateTime,
        private val type: TransactionType,
        private val price: BigDecimal,
        private val coins: BigDecimal,
        private val moneyAmount: BigDecimal
)
