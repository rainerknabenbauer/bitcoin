package de.nykon.bitcoin.backend.gatherer.repository

import de.nykon.bitcoin.backend.gatherer.value.FlattenedBuyOrderbook
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * Records current BUY offers.
 */
@Repository
interface FlattenBuyOrderbookRepository : MongoRepository<FlattenedBuyOrderbook, String> {

}