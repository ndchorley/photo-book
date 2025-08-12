package com.xyphias.photobook

import org.http4k.core.*
import org.http4k.core.Method.*
import org.http4k.lens.location
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.template.HandlebarsTemplates

class PhotoBookApp : HttpHandler {
    override fun invoke(request: Request): Response = routes(request)

    private val routes = routes(
        "/" bind GET to { _ -> 
            Response(Status.OK).body(templateRenderer(HomePage)) 
        },
        
        "/" bind POST to { _ ->
            Response(Status.SEE_OTHER).location(Uri.of("/photo/some-id"))
        }
    )
    
    private val templateRenderer = HandlebarsTemplates().CachingClasspath()
}
