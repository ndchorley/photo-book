package com.xyphias.photobook

import com.xyphias.photobook.views.HomePage
import com.xyphias.photobook.views.Photo
import org.http4k.core.*
import org.http4k.core.Method.*
import org.http4k.core.body.form
import org.http4k.lens.location
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.template.HandlebarsTemplates

class PhotoBookApp : HttpHandler {
    override fun invoke(request: Request): Response = routes(request)

    private val routes = routes(
        "/" bind GET to { _ -> 
            Response(Status.OK).body(renderTemplate(HomePage)) 
        },
        
        "/" bind POST to { request ->
            photoUrl = request.form("url")
            title = request.form("title")
            
            Response(Status.SEE_OTHER).location(Uri.of("/photo/some-id"))
        },
        
        "/photo/{id}" bind GET to { _ ->
            Response(Status.OK).body(renderTemplate(Photo(photoUrl!!, title!!)))
        }
    )
    
    private var photoUrl: String? = null
    private var title: String? = null
    
    private val renderTemplate = HandlebarsTemplates().CachingClasspath()
}
