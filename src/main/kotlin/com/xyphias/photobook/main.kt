package com.xyphias.photobook

import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main() {
    createApp().asServer(Jetty(port = 8080)).start()
}
