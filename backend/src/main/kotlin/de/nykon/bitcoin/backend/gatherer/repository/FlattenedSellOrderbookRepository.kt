package de.nykon.bitcoin.backend.gatherer.repository

import de.nykon.bitcoin.backend.gatherer.value.FlattenedSellOrderbook
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * Records current BUY offers.
 */
@Repository
interface FlattenedSellOrderbookRepository : MongoRepository<FlattenedSellOrderbook, String> {

}