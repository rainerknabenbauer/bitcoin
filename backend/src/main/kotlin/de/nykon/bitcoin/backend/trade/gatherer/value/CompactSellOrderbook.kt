package de.nykon.bitcoin.backend.trade.gatherer.value

import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDateTime

@Document(collection = "orderbook.sell.compact")
data class CompactSellOrderbook(
        val dateTime: LocalDateTime,
        val weightedAverage: BigDecimal,
        val offers: List<Offer>
)