package com.xyphias.photobook

import com.xyphias.photobook.storage.Repository
import com.xyphias.photobook.views.NotFound
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.path
import org.http4k.template.TemplateRenderer
import java.time.format.DateTimeFormatter

fun viewPhotoHandlerFrom(repository: Repository, renderTemplate: TemplateRenderer) =
    { request: Request ->
        val id = Id(request.path("id")!!)

        when (val photo = repository.find(id)) {
            null -> Response(Status.NOT_FOUND).body(renderTemplate(NotFound))
            else -> Response(Status.OK).body(renderTemplate(photo.toViewModel()))
        }
    }

private fun Photo.toViewModel(): com.xyphias.photobook.views.Photo {
    val dateTime =
        DateTimeFormatter.ofPattern("d MMMM YYYY 'at' HH:mm")
            .format(takenOn)

    return com.xyphias.photobook.views.Photo(url, title, notes, dateTime)
}
