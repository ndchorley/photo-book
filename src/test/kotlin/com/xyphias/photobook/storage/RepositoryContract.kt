package com.xyphias.photobook.storage

import com.xyphias.photobook.Id
import com.xyphias.photobook.adding.NewPhoto
import com.xyphias.photobook.Photo
import com.xyphias.photobook.listing.SummarisedPhoto
import org.junit.jupiter.api.Test
import strikt.api.expect
import strikt.api.expectThat
import strikt.assertions.containsExactlyInAnyOrder
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import java.time.LocalDateTime

abstract class RepositoryContract {
    abstract val repository: Repository

    @Test
    fun `photos can be added and retrieved by ID`() {
        val id = repository.add(aPhoto)
        repository.add(anotherPhoto)

        val retrievedPhoto: Photo? = repository.find(id)

        expect {
            that(retrievedPhoto).isNotNull()
            that(retrievedPhoto?.url).isEqualTo(aPhoto.url)
            that(retrievedPhoto?.title).isEqualTo(aPhoto.title)
            that(retrievedPhoto?.notes).isEqualTo(aPhoto.notes)
            that(retrievedPhoto?.takenOn).isEqualTo(aPhoto.takenOn)
        }
    }

    @Test
    fun `null is returned when there is no photo with the given ID`() {
        val photo = repository.find(Id("does-not-exist"))

        expectThat(photo).isNull()
    }

    @Test
    fun `the list of photos is a summary for each one`() {
        val id1 = repository.add(aPhoto)
        val id2 = repository.add(anotherPhoto)

        val listOfPhotos = repository.all()

        expectThat(listOfPhotos)
            .containsExactlyInAnyOrder(
                SummarisedPhoto(id1, aPhoto.title, aPhoto.takenOn),
                SummarisedPhoto(id2, anotherPhoto.title, anotherPhoto.takenOn)
            )
    }

    private val aPhoto =
        NewPhoto(
            url = "https://example.com/bus.jpg",
            title = "London bus",
            notes = "Angle was OK, but shot has distraction from buildings",
            takenOn = LocalDateTime.MIN
        )

    private val anotherPhoto =
        NewPhoto(
            url = "https://example.com/box.jpg",
            title = "A box",
            notes = "Good",
            takenOn = LocalDateTime.MIN
        )
}
