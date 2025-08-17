package com.xyphias.photobook

import com.xyphias.photobook.storage.Repository
import org.http4k.template.TemplateRenderer

interface Dependencies {
    val renderTemplate: TemplateRenderer

    val repository: Repository
}