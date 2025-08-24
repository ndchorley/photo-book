package com.xyphias.photobook.views

import org.http4k.template.ViewModel

data class ListingPage(val photos: List<Photo>) : ViewModel
