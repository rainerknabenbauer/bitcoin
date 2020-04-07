package de.nykon.bitcoin.backend.repository.value

import java.math.BigDecimal

data class Trade (
        val price: BigDecimal,
        val amount: BigDecimal
)