package com.example.lts.ui.dummy

import androidx.media3.common.MediaItem

data class Video(
    val description: String?= null,
    val subtitle: String?= null,
    val sources: String?= null,
    val thumb: String?= null,
    val title: String?= null,
    val mediaItem : MediaItem?=null
)