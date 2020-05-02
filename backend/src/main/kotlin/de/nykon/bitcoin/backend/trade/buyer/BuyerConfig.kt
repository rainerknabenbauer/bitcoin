package de.nykon.bitcoin.backend.trade.buyer

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.math.BigDecimal

@Configuration
open class BuyerConfig {

    var isActive = false

    @Value(value = "\${api.isLive}")
    var isLiveChange: Boolean = false

    @Value(value = "\${bitcoin.trading.buying.start}")
    var targetPrice: BigDecimal = BigDecimal.ZERO

    @Value(value = "\${bitcoin.trading.buying.automatized}")
    var isAutomatized: Boolean = false
}