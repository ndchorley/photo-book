package com.xyphias.photobook

import org.http4k.template.TemplateRenderer

interface Dependencies {
    val renderTemplate: TemplateRenderer

    val repository: Repository
}