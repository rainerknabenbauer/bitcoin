package de.nykon.bitcoin.backend

import de.nykon.bitcoin.backend.repository.BitcoinRepository
import de.nykon.bitcoin.backend.value.OrdersRoot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SupplyService {

    @Autowired
    private lateinit var tradeProcessor: PriceProcessor

    @Autowired
    private lateinit var bitcoinRepository: BitcoinRepository

    fun storeSupply(ordersRoot: OrdersRoot) {
        val priceBatch = tradeProcessor.process(ordersRoot)!!
        bitcoinRepository.save(priceBatch)
    }


}