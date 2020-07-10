package de.nykon.bitcoin.backend.trade.seller
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.math.BigDecimal

@Configuration
open class SellerConfig {

    @Value(value = "\${bitcoin.trading.selling.active}")
    var isActive: Boolean = false

    @Value(value = "\${api.isLive}")
    var isLiveChange: Boolean = false

    @Value(value = "\${bitcoin.trading.selling.automatized}")
    var isAutomatized: Boolean = false

    @Value(value = "\${bitcoin.trading.selling.start}")
    var targetPrice: BigDecimal = BigDecimal.ZERO

    @Value(value = "\${bitcoin.trading.selling.minVolume}")
    var minVolume: BigDecimal = BigDecimal.ZERO
}