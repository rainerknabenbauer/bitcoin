package de.nykon.bitcoin.client.repository.value

import java.math.BigDecimal

data class Offer (
        val price: BigDecimal,
        val amount: BigDecimal
)