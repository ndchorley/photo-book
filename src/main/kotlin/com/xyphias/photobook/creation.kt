package com.xyphias.photobook

import com.xyphias.photobook.Environment.*
import org.http4k.template.HandlebarsTemplates

enum class Environment {
    DEVELOPMENT,
    PRODUCTION
}

fun createAppFor(environment: Environment): PhotoBookApp {
    val renderTemplate =
        when (environment) {
            DEVELOPMENT -> HandlebarsTemplates().HotReload("src/main/resources")
            PRODUCTION -> HandlebarsTemplates().CachingClasspath()
        }
    
    return PhotoBookApp(renderTemplate)
}
