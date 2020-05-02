package de.nykon.bitcoin.backend.trade.seller
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.math.BigDecimal

@Configuration
open class SellerSchedulConfig {

    var isActive = false
    val consideredOrderSize = 5

    @Value(value = "\${api.isLive}")
    var isLiveChange: Boolean = false

    @Value(value = "\${bitcoin.trading.selling.start}")
    var threshold: BigDecimal = BigDecimal.ZERO

}