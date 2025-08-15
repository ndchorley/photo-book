package com.xyphias.photobook

import com.xyphias.photobook.Environment.PRODUCTION
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main() {
    createAppFor(PRODUCTION).asServer(Jetty(port = 8080)).start()
}
