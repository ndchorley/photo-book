package com.xyphias.photobook

import org.junit.jupiter.api.Test
import strikt.api.expect
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import java.time.LocalDateTime

class RepositoryTests {
    private val repository = Repository()

    @Test
    fun `photos can be added and retrieved by ID`() {
        val photo =
            NewPhoto(
                url = "https://example.com/bus.jpg",
                title = "London bus",
                notes = "Angle was OK, but shot has distraction from buildings",
                takenOn = LocalDateTime.MIN
            )

        val id = repository.add(photo)
        repository.add(anotherPhoto)

        val retrievedPhoto: Photo? = repository.find(id)

        expect {
            that(retrievedPhoto).isNotNull()
            that(retrievedPhoto?.url).isEqualTo(photo.url)
            that(retrievedPhoto?.title).isEqualTo(photo.title)
            that(retrievedPhoto?.notes).isEqualTo(photo.notes)
            that(retrievedPhoto?.takenOn).isEqualTo(photo.takenOn)
        }
    }

    private val anotherPhoto =
        NewPhoto(
            url = "https://example.com/box.jpg",
            title = "A box",
            notes = "Good",
            takenOn = LocalDateTime.MIN
        )
}