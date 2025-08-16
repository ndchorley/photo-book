package com.xyphias.photobook

interface Repository {
    fun add(photo: NewPhoto): Id

    fun find(id: Id): Photo?
}
