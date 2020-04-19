package de.nykon.bitcoin.backend.trading

import de.nykon.bitcoin.sdk.value.showPublicTradeHistory.Trade
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PublicTradeHistoryRepository : MongoRepository<Trade, String> {

}