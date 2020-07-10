package de.nykon.bitcoin.backend

import com.mongodb.MongoClient
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoConfiguration

@Configuration
open class MongoConfig : AbstractMongoConfiguration() {
    override fun getDatabaseName(): String {
        return "bitcoin"
    }

    override fun mongoClient(): MongoClient {
        return MongoClient("127.0.0.1", 27017)
    }

    override fun getMappingBasePackage(): String {
        return "de.nykon.bitcoin"
    }
}