package de.nykon.bitcoin.backend.trading.schedules.config
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
open class SellerSchedulConfig {

    var isActive = false
    val consideredOrderSize = 5

    @Value(value = "\${api.active}")
    var isLiveChange: Boolean = false

}