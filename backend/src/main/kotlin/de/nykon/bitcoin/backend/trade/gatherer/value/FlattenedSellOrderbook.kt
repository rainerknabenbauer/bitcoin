package de.nykon.bitcoin.backend.trade.gatherer.value

import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDateTime

@Document(collection = "compactSellOrderbook")
data class FlattenedSellOrderbook(
        val dateTime: LocalDateTime,
        val username: String,
        val price: BigDecimal,
        val amount: BigDecimal
)