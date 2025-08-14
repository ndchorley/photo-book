package com.xyphias.photobook

class Repository {
    fun add(photo: NewPhoto) {
        this.photo = photo
    }

    fun find(): NewPhoto? {
        return photo
    }

    private var photo: NewPhoto? = null
}
