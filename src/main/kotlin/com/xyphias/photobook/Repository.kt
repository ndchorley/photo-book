package com.xyphias.photobook

class Repository {
    fun add(photo: NewPhoto): Id {
        val newPhoto =
            Photo(
                photo.url,
                photo.title,
                photo.notes,
                photo.takenOn
            )
        this.photo = newPhoto

        photos.addLast(newPhoto)

        return Id(photos.lastIndex.toString())
    }

    fun find(): Photo? {
        return photo
    }

    fun find(id: Id): Photo? {
        return photos[id.value.toInt()]
    }

    private val photos = mutableListOf<Photo>()
    private var photo: Photo? = null
}
