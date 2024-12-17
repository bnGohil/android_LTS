package com.example.lts.ui.dummy

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView


@Composable
fun VideoPlayer(videoUri: String) {

    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val currentItem = MediaItem.Builder().setUri(videoUri).build()
            setMediaItem(currentItem)
            prepare()
        }
    }

    val isPlaying = remember {
        exoPlayer.isPlaying
    }

    AndroidView(factory = { PlayerView(it).apply { player = exoPlayer } })
}

