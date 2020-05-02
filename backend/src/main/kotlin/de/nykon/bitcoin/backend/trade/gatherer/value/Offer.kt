package de.nykon.bitcoin.backend.trade.gatherer.value

import java.math.BigDecimal

data class Offer (
    val username: String,
    val price: BigDecimal,
    val amount: BigDecimal
)