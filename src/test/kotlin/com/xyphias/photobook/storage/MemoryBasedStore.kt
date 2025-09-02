package com.xyphias.photobook.storage

import com.xyphias.photobook.Id
import com.xyphias.photobook.adding.NewPhoto
import com.xyphias.photobook.Photo
import com.xyphias.photobook.listing.PhotoSummary
import kotlin.random.Random

class MemoryBasedStore : Repository {
    override fun add(photo: NewPhoto): Id {
        val id = Id(Random.Default.nextInt().toString())

        val newPhoto =
            Photo(
                id,
                photo.url,
                photo.title,
                photo.notes,
                photo.takenOn
            )

        photos.add(newPhoto)

        return id
    }

    override fun find(id: Id): Photo? =
        photos.find { photo -> photo.id == id }

    override fun all(): List<PhotoSummary> =
        photos.map { 
            photo -> PhotoSummary(photo.id, photo.title, photo.takenOn)
        }

    private val photos = mutableListOf<Photo>()
}
