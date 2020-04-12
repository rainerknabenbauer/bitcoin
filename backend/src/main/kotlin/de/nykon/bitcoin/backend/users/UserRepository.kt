package de.nykon.bitcoin.backend.repository

import de.nykon.bitcoin.backend.users.value.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User, String> {

    fun findUserByName(name: String): User?

}