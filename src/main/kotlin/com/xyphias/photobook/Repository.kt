package com.xyphias.photobook

import kotlin.random.Random

class Repository {
    fun add(photo: NewPhoto): Id {
        val newPhoto =
            Photo(
                photo.url,
                photo.title,
                photo.notes,
                photo.takenOn
            )

        val id = Id(Random.nextInt().toString())
        photos.put(id, newPhoto)

        return id
    }

    fun find(id: Id): Photo? {
        return photos[id]
    }

    private val photos = mutableMapOf<Id, Photo>()
}
