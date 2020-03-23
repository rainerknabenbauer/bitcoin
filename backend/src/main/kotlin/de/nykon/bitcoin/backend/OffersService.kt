package de.nykon.bitcoin.backend

import de.nykon.bitcoin.backend.repository.BitcoinRepository
import de.nykon.bitcoin.backend.value.OrdersRoot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OffersService {

    @Autowired
    private lateinit var priceProcessor: PriceProcessor

    @Autowired
    private lateinit var bitcoinRepository: BitcoinRepository

    fun storePrice(ordersRoot: OrdersRoot) {
        val priceBatch = priceProcessor.process(ordersRoot)!!
        bitcoinRepository.save(priceBatch)
    }


}