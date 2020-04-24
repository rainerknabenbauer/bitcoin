package de.nykon.bitcoin.backend.gatherer.repository

import de.nykon.bitcoin.backend.gatherer.value.LongTermTrade
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * Total public trades that were recorded.
 */
@Repository
interface LongTradeHistoryRepository : MongoRepository<LongTermTrade, String> {

}