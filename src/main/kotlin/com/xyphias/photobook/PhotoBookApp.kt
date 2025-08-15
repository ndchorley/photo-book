package com.xyphias.photobook

import com.xyphias.photobook.views.HomePage
import com.xyphias.photobook.views.Photo
import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.body.form
import org.http4k.lens.location
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.template.TemplateRenderer

class PhotoBookApp(
    private val renderTemplate: TemplateRenderer
) : HttpHandler {
    override fun invoke(request: Request): Response = routes(request)

    private val routes = routes(
        "/" bind GET to { _ -> 
            Response(Status.OK).body(renderTemplate(HomePage)) 
        },
        
        "/" bind POST to { 
            request ->
                val photo =
                    NewPhoto(
                        url = request.form("url")!!,
                        title = request.form("title")!!
                    )
    
                repository.add(photo)
    
                Response(Status.SEE_OTHER).location(Uri.of("/photo/some-id"))
        },
        
        "/photo/{id}" bind GET to { _ ->
            val photo = repository.find()
            
            Response(Status.OK).body(renderTemplate(photo!!.toViewModel()))
        }
    )
    
    private val repository = Repository()
}

private fun NewPhoto.toViewModel(): Photo = Photo(url, title)

