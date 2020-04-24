package de.nykon.bitcoin.backend.seller
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
open class SellerSchedulConfig {

    var isActive = false
    val consideredOrderSize = 5

    @Value(value = "\${api.isLive}")
    var isLiveChange: Boolean = false

}