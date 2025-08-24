package com.xyphias.photobook

import com.xyphias.photobook.storage.Repository
import com.xyphias.photobook.views.ListingPage
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.template.TemplateRenderer
import java.time.format.DateTimeFormatter

fun listHandler(repository: Repository, renderTemplate: TemplateRenderer): HttpHandler =
    { _: Request ->
        val photos =
            repository.all().toViewModels()

        Response(Status.OK)
            .body(renderTemplate(ListingPage(photos)))
    }

private fun List<Photo>.toViewModels(): List<com.xyphias.photobook.views.Photo> {
    val dateTimeFormatter =
        DateTimeFormatter.ofPattern("d-MM-YYYY HH:mm")

    return map { photo ->
        com.xyphias.photobook.views.Photo(
            photo.url,
            photo.title,
            photo.notes,
            dateTimeFormatter.format(photo.takenOn)
        )
    }
}
