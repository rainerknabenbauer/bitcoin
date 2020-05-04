package de.nykon.bitcoin.backend.trade.buyer

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.math.BigDecimal

@Configuration
open class BuyerConfig {

    @Value(value = "\${bitcoin.trading.buying.active}")
    var isActive: Boolean = false

    @Value(value = "\${api.isLive}")
    var isLiveChange: Boolean = false

    @Value(value = "\${bitcoin.trading.buying.automatized}")
    var isAutomatized: Boolean = false

    @Value(value = "\${bitcoin.trading.buying.start}")
    var targetPrice: BigDecimal = BigDecimal.ZERO

    @Value(value = "\${bitcoin.trading.buying.minVolume}")
    var minVolume: BigDecimal = BigDecimal.ZERO
}