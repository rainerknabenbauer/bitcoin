package de.nykon.bitcoin.backend.trading

import de.nykon.bitcoin.backend.trading.value.FlattenedSellOrderbook
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * Records current BUY offers.
 */
@Repository
interface FlattenedSellOrderbookRepository : MongoRepository<FlattenedSellOrderbook, String> {

}