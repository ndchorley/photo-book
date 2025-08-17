package com.xyphias.photobook

import org.http4k.template.HandlebarsTemplates
import org.http4k.template.TemplateRenderer

class ProductionDependencies : Dependencies {
    override val renderTemplate: TemplateRenderer =
        HandlebarsTemplates().CachingClasspath()

    override val repository: Repository = run {
        val homeDirectory = System.getenv("HOME")
        SQLiteBasedStore.createFor("jdbc:sqlite:$homeDirectory/photo-book.db")
    }
}
