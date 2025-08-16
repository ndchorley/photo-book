package com.xyphias.photobook

class Repository {
    fun add(photo: NewPhoto) {
        this.photo =
            Photo(
                photo.url,
                photo.title,
                photo.notes,
                photo.takenOn
            )
    }

    fun find(): Photo? {
        return photo
    }

    private var photo: Photo? = null
}
