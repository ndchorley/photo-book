package com.xyphias.photobook

import org.http4k.template.HandlebarsTemplates
import org.http4k.template.TemplateRenderer

class TestDependencies : Dependencies {
    override val renderTemplate: TemplateRenderer =
        HandlebarsTemplates().HotReload("src/main/resources")

    override val repository: Repository = MemoryBasedStore()
}
