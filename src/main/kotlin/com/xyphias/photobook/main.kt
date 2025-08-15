package com.xyphias.photobook

import com.xyphias.photobook.Environment.*
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main() {
    val environment =
        when (System.getenv("ENVIRONMENT")) {
            "DEVELOPMENT" -> DEVELOPMENT
            else -> PRODUCTION
        }
    
    createAppFor(environment).asServer(Jetty(port = 8080)).start()
}
