package com.xyphias.photobook

import java.time.LocalDateTime

data class Photo(
    val id: Id,
    val url: String,
    val title: String,
    val notes: String,
    val takenOn: LocalDateTime
)
