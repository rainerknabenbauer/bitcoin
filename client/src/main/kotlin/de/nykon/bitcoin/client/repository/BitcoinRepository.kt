package de.nykon.bitcoin.client.repository

import de.nykon.bitcoin.client.repository.value.PriceBatch
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BitcoinRepository : ReactiveCrudRepository<PriceBatch, Int>{
}