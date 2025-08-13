package com.xyphias.photobook.views

import org.http4k.template.ViewModel

data class Photo(val url: String, val title: String) : ViewModel
