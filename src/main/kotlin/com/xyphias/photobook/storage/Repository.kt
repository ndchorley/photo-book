package com.xyphias.photobook.storage

import com.xyphias.photobook.Id
import com.xyphias.photobook.adding.NewPhoto
import com.xyphias.photobook.Photo

interface Repository {
    fun add(photo: NewPhoto): Id

    fun find(id: Id): Photo?
    fun all(): List<Photo>
}
