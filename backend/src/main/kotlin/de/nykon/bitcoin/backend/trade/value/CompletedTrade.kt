package de.nykon.bitcoin.backend.trade.value

import de.nykon.bitcoin.sdk.value.CryptoCurrency
import de.nykon.bitcoin.sdk.value.bitcoinde.TransactionType
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * Currently a workaround as there is no automatic buy history available through API
 * nor is storing a successful trade currently implemented.
 */
@Document(collection = "myTradeHistory")
data class CompletedTrade(
        val cryptoCurrency: CryptoCurrency,
        val dateTime: LocalDateTime,
        val type: TransactionType,
        val price: BigDecimal,
        val coins: BigDecimal,
        val moneyAmount: BigDecimal
)
