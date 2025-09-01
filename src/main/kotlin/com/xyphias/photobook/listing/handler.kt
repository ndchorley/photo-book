package com.xyphias.photobook.listing

import com.xyphias.photobook.storage.Repository
import com.xyphias.photobook.views.ListingPage
import com.xyphias.photobook.views.ListingPhoto
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.template.TemplateRenderer
import java.time.format.DateTimeFormatter

fun listHandler(repository: Repository, renderTemplate: TemplateRenderer): HttpHandler =
    { _: Request ->
        val photos = repository.all()

        val views = photos.toViewModels()

        Response(Status.OK)
            .body(renderTemplate(ListingPage(views)))
    }

private fun List<SummarisedPhoto>.toViewModels(): List<ListingPhoto> =
    map { photo ->
        ListingPhoto(
            photo.id.value,
            photo.title,
            dateTimeFormatter.format(photo.takenOn)
        )
    }

private val dateTimeFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("d-MM-YYYY HH:mm")
