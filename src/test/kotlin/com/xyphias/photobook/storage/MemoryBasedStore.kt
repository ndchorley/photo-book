package com.xyphias.photobook.storage

import com.xyphias.photobook.Id
import com.xyphias.photobook.NewPhoto
import com.xyphias.photobook.Photo
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

    private val photos = mutableListOf<Photo>()
}
