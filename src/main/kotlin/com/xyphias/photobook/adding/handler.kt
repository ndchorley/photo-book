package com.xyphias.photobook.adding

import com.xyphias.photobook.storage.Repository
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Uri
import org.http4k.core.body.form
import org.http4k.lens.location
import java.time.LocalDateTime

fun addPhotoHandlerFrom(repository: Repository) =
    { request: Request ->
        val newPhoto = photoFrom(request)

        val id = repository.add(newPhoto)

        Response(Status.SEE_OTHER)
            .location(Uri.of("/photo/${id.value}"))
    }

private fun photoFrom(request: Request): NewPhoto =
    NewPhoto(
        url = request.form("url")!!,
        title = request.form("title")!!,
        notes = request.form("notes")!!,
        takenOn =
            LocalDateTime.parse(request.form("taken-on")!!)
    )
