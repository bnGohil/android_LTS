package com.example.lts.ui.dummy

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.lts.ui.dummy.view_model.DummyViewModel
import com.sqt.lts.custom_component.CustomNetworkImageView
import com.sqt.lts.ui.dummy.state.DummyVideoState
import com.sqt.lts.ui.theme.kPrimaryColor
import com.sqt.lts.ui.trending.data.response.VideoAudio

@Composable
fun DummyAndDemoForComponent(dummyViewModel: DummyViewModel) {

    val state by dummyViewModel.videoState.collectAsState();


    val context = LocalContext.current

    val exoPlayer = remember { ExoPlayer.Builder(context).build()}

//    DummyVideoState.videoList?.forEach {
//        exoPlayer.addMediaItem(MediaItem.fromUri(it.sources?:""))
//    }

    exoPlayer.prepare()

    ExoPlayerPlaylist("",{})

//    val isPlaying = remember {
//        exoPlayer.isPlaying
//    }

//    AndroidView(factory = { PlayerView(it).apply { player = exoPlayer } })

//    VideoPlayer(videoUri = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
}

@OptIn(UnstableApi::class)
@Composable
fun ExoPlayerPlaylist(
    url: String? = null,
    onLoading: (Boolean) -> Unit,
    video: VideoAudio? = null
) {





    val isVideoLoading = remember { mutableStateOf<Boolean>(true) }

    val context = LocalContext.current

    if(url == null) return

    val exoPlayer = remember { ExoPlayer.Builder(context).build() }


    DisposableEffect(key1 = exoPlayer) {
        onDispose {
            exoPlayer.stop()
            exoPlayer.release()
        }
    }


    LaunchedEffect(url) {
        exoPlayer.stop()
        exoPlayer.clearMediaItems()
        val mediaItem = MediaItem.fromUri(url)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }

    LaunchedEffect(exoPlayer) {
        snapshotFlow {
            exoPlayer
        }.collect{

            it.addListener(object : Player.Listener{

                override fun onIsLoadingChanged(isLoading: Boolean) {
                    onLoading(isLoading)
                    isVideoLoading.value = isLoading
                    super.onIsLoadingChanged(isLoading)
                }

                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)
                }
                override fun onEvents(player: Player, events: Player.Events) {
//                    println("player.duration is ${player.duration}")
                    super.onEvents(player, events)
                }
            })
        }
    }


    var currentMediaIndex by remember { mutableIntStateOf(exoPlayer.currentMediaItemIndex) }

    // Listen to changes in the media item
    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                currentMediaIndex = exoPlayer.currentMediaItemIndex
            }
        }
        exoPlayer.addListener(listener)

        onDispose {
            exoPlayer.removeListener(listener)
        }
    }

    Column {


        if(isVideoLoading.value){

            Box() {

                CustomNetworkImageView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    imagePath = video?.thumbimgurl,
                    contentScale = ContentScale.FillBounds
                )

                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center),trackColor = kPrimaryColor)

            }

        }else{

            AndroidView(factory = {
                PlayerView(it).apply {
                    player = exoPlayer
                }
            }, update = {
                it.player = exoPlayer
            }
            )
        }
    }
}
enum class ClickVideo{NAVIGATION,PLAYLIST}