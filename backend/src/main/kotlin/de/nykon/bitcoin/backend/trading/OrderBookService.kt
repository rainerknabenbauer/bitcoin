package de.nykon.bitcoin.backend.trading

import com.intellij.openapi.components.Service
import de.nykon.bitcoin.sdk.bitcoinDe.ShowMyOrders
import de.nykon.bitcoin.sdk.bitcoinDe.ShowOrderbook
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Service
class OrderBookService(
        private val showMyOrders: ShowMyOrders,
        private val showOrderbook: ShowOrderbook
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    //TODO wraps database entries to domain
}
