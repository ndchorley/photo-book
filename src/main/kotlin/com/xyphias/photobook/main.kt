package com.xyphias.photobook

import com.xyphias.photobook.Environment.*
import org.http4k.server.SunHttp
import org.http4k.server.asServer

fun main() {
    val dependencies =
        when (System.getenv("ENVIRONMENT")) {
            "DEVELOPMENT" -> DevelopmentDependencies()
            else -> ProductionDependencies()
        }

    createAppFrom(dependencies)
        .asServer(SunHttp(port = 8080))
        .start()
}
