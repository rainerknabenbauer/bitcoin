package de.nykon.bitcoin.backend.reporting

import de.nykon.bitcoin.backend.trade.MyTradeRepository
import de.nykon.bitcoin.backend.trade.value.CompletedTrade
import org.springframework.stereotype.Service

@Service
class MyTradeHistoryService(private val myTradeRepository: MyTradeRepository) {

    fun getNewestTrades(): MutableList<CompletedTrade> {
        return myTradeRepository.findAll().subList(0, 5)
    }

}