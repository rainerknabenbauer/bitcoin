package de.nykon.bitcoin.backend.sdk

import de.nykon.bitcoin.backend.SdkConfig
import de.nykon.bitcoin.backend.users.authentication.AuthenticationService
import de.nykon.bitcoin.backend.users.value.Authentication
import de.nykon.bitcoin.sdk.bitcoinDe.DeleteOrder
import de.nykon.bitcoin.sdk.bitcoinDe.ShowMyOrders
import de.nykon.bitcoin.sdk.bitcoinDe.ShowOrderbook
import de.nykon.bitcoin.sdk.value.bitcoinde.Response
import de.nykon.bitcoin.sdk.value.bitcoinde.deleteOrder.OrderId
import de.nykon.bitcoin.sdk.value.bitcoinde.showMyOrders.ShowMyOrdersBody
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SdkService(
        private val commandConfig: SdkConfig,
        private val authenticationService: AuthenticationService
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    private val showMyOrders = ShowMyOrders(commandConfig.apiKey, commandConfig.apiSecret)
    private val deleteOrder = DeleteOrder(commandConfig.apiKey, commandConfig.apiSecret)


    fun showOrderbook(authentication: Authentication): ShowOrderbook? {
        return if (authenticationService.authenticate(authentication)) {
            ShowOrderbook(commandConfig.apiKey, commandConfig.apiSecret)
        } else {
            null
        }
    }

    fun deleteAllOrders() {
        val myOrders = showMyOrders.all()

        deleteOrders(myOrders)
    }

    /* Remove all active orders */
    fun deleteOrders(myOrders: Response<ShowMyOrdersBody>) {
        if (myOrders.body.orders != null) {
            myOrders.body.orders!!
                    .forEach { order ->
                        run {
                            deleteOrder.execute(OrderId(order.order_id))
                            log.info("eleted order ${order.order_id}")
                        }
                    }
        }
    }

}