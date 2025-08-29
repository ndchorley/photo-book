package com.xyphias.photobook

import com.xyphias.photobook.adding.addPhotoHandlerFrom
import com.xyphias.photobook.storage.Repository
import com.xyphias.photobook.adding.HomePage
import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.template.TemplateRenderer

class PhotoBookApp(
    private val renderTemplate: TemplateRenderer,
    repository: Repository
) : HttpHandler {
    override fun invoke(request: Request): Response = routes(request)

    private val routes = routes(
        "/" bind GET to { _ -> 
            Response(OK).body(renderTemplate(HomePage)) 
        },
        
        "/" bind POST to addPhotoHandlerFrom(repository),
        
        "/photo/{id}" bind GET to viewPhotoHandlerFrom(repository, renderTemplate),

        "/list" bind GET to listHandler(repository, renderTemplate)
    )
}
