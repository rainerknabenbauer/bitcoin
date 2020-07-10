package de.nykon.stundenzettel.stunden

import de.nykon.stundenzettel.stunden.value.Uberstunden
import org.springframework.stereotype.Service

@Service
class UberstundenService (
        val uberstundenRepository: UberstundenRepository
) {

    fun save(uberstunden: Uberstunden): String {

        uberstundenRepository.save(uberstunden)
        return "saved $uberstunden"
    }


}