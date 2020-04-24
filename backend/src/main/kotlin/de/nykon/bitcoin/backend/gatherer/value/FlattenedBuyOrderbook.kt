package de.nykon.bitcoin.backend.gatherer.value

import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDateTime

@Document(collection = "compactBuyOrderbook")
data class FlattenedBuyOrderbook(
        val dateTime: LocalDateTime,
        val username: String,
        val price: BigDecimal,
        val amount: BigDecimal
)