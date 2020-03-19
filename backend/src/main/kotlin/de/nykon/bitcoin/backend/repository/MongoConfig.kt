package de.nykon.bitcoin.backend.repository

import com.mongodb.reactivestreams.client.MongoClients
import de.nykon.bitcoin.backend.repository.BitcoinRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration
@EnableReactiveMongoRepositories(
        basePackageClasses = [BitcoinRepository::class])
open class MongoConfig : AbstractReactiveMongoConfiguration() {

    override fun getDatabaseName() = "bitcoin"

    override fun reactiveMongoClient() = mongoClient()

    @Bean
    open fun mongoClient() = MongoClients.create()

    @Bean
    override fun reactiveMongoTemplate()
            = ReactiveMongoTemplate(mongoClient(), databaseName)
}