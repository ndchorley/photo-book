package com.xyphias.photobook

import com.xyphias.photobook.views.ListingPage
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.template.TemplateRenderer

fun listHandler(renderTemplate: TemplateRenderer): HttpHandler =
    { _: Request -> Response(Status.OK).body(renderTemplate(ListingPage)) }
