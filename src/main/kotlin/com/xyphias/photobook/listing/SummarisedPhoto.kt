package com.xyphias.photobook.listing

import com.xyphias.photobook.Id
import java.time.LocalDateTime

data class SummarisedPhoto(
    val id: Id,
    val title: String,
    val takenOn: LocalDateTime
)
