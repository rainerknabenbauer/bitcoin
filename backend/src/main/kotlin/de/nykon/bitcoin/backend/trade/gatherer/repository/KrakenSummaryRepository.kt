package de.nykon.bitcoin.backend.trade.gatherer.repository

import de.nykon.bitcoin.sdk.value.cryptowatch.summary.KrakenSummaryBody
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * Records current BUY offers.
 */
@Repository
interface KrakenSummaryRepository : MongoRepository<KrakenSummaryBody, String> {

}