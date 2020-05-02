package de.nykon.bitcoin.backend.trade.buyer

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.math.BigDecimal

@Configuration
open class BuyerBuyerConfig {

    var isActive = false
    val consideredOrderSize = 3

    @Value(value = "\${api.isLive}")
    var isLiveChange: Boolean = false

    @Value(value = "\${bitcoin.trading.buying.start}")
    var targetPrice: BigDecimal = BigDecimal.ZERO
}