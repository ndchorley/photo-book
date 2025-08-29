package com.xyphias.photobook.adding

import java.time.LocalDateTime

data class NewPhoto(
    val url: String,
    val title: String,
    val notes: String,
    val takenOn: LocalDateTime
)
