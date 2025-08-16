package com.xyphias.photobook

import com.xyphias.photobook.views.HomePage
import com.xyphias.photobook.views.NotFound
import com.xyphias.photobook.views.Photo as PhotoView
import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.core.body.form
import org.http4k.lens.location
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.template.TemplateRenderer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PhotoBookApp(
    private val renderTemplate: TemplateRenderer
) : HttpHandler {
    override fun invoke(request: Request): Response = routes(request)

    private val routes = routes(
        "/" bind GET to { _ -> 
            Response(OK).body(renderTemplate(HomePage)) 
        },
        
        "/" bind POST to { 
            request ->
                val newPhoto = photoFrom(request)
    
                repository.add(newPhoto)
    
                Response(Status.SEE_OTHER).location(Uri.of("/photo/some-id"))
        },
        
        "/photo/{id}" bind GET to { _ ->
            when (val photo = repository.find()) {
                null -> Response(NOT_FOUND).body(renderTemplate(NotFound))
                else -> Response(OK).body(renderTemplate(photo.toViewModel()))
            }
        }
    )
    
    private val repository = Repository()
}

private fun photoFrom(request: Request): NewPhoto =
    NewPhoto(
        url = request.form("url")!!,
        title = request.form("title")!!,
        notes = request.form("notes")!!,
        takenOn =
            LocalDateTime
                .parse(request.form("taken-on")!!)
    )

private fun Photo.toViewModel(): PhotoView {
    val dateTime =
        DateTimeFormatter
            .ofPattern("d MMMM YYYY 'at' HH:mm")
            .format(takenOn)

    return PhotoView(url, title, notes, dateTime)
}
