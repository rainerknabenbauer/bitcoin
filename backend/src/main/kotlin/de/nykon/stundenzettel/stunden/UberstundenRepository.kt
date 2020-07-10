package de.nykon.stundenzettel.stunden

import de.nykon.stundenzettel.stunden.value.Uberstunden
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UberstundenRepository : MongoRepository<Uberstunden, String> {

}