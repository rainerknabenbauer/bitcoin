package de.nykon.bitcoin.backend.trade.seller
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.math.BigDecimal

@Configuration
open class SellerConfig {

    var isActive = false
    val consideredOrderSize = 5

    @Value(value = "\${api.isLive}")
    var isLiveChange: Boolean = false

    @Value(value = "\${bitcoin.trading.selling.start}")
    var targetPrice: BigDecimal = BigDecimal.ZERO

}