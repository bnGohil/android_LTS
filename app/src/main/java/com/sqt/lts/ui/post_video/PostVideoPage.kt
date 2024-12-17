package com.example.lts.ui.post_video

import android.annotation.SuppressLint
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.lts.custom_component.CustomButton
import com.example.lts.custom_component.CustomTopBar
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.ui.theme.kBackGround
import com.example.lts.utils.extainstion.kWhiteW400FS13
import com.example.lts.utils.extainstion.kWhiteW500FS17
import com.example.lts.utils.scaleSize
import com.sqt.lts.ui.theme.kPrimaryColor
import com.sqt.lts.ui.theme.kWhite
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date

@SuppressLint("SimpleDateFormat")
@Composable
fun PostVideoPage(navHostController: NavHostController) {

    var videoUrl by remember { mutableStateOf<Uri?>(null) }

    var isShowVideo by remember { mutableStateOf<Boolean>(false) }

    val context = LocalContext.current

    val getAudioLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) {
        println("RESPONSE +++++++++++++$it")
    }


    val getContentLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent(),
        onResult = {
            videoUrl = it
            isShowVideo = true
        }
    )

    val captureVideoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.CaptureVideo(),
        onResult = {
                if(it){
                    isShowVideo = true
                }else{
                    Toast.makeText(context, "Can't open camera because permission is denied. Please provide access in settings.", Toast.LENGTH_SHORT).show()
                }
        }
    )







    Scaffold(
        containerColor = kBackGround,
        topBar = {
            CustomTopBar(navHostController = navHostController, title = "Post Video")
        },
    ) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues = it)
            .padding(horizontal = 20.dp.scaleSize())) {




                Box(modifier = Modifier
                    .weight(1F)
                    .fillMaxWidth()) {

                    if(videoUrl != null && isShowVideo){
                        VideoPlayer(uri = videoUrl!!)
                        OutlinedButton(
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = kBackGround,
                            ),
                            modifier = Modifier.align(Alignment.TopEnd),
                            onClick = {
                                videoUrl = null
                                isShowVideo = false
                            }) {
                            Icon(imageVector = Icons.Filled.Clear, contentDescription = "clear")
                        }
                    }else{
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "Upload your video", style = TextStyle.Default.kWhiteW500FS17(), textAlign = TextAlign.Center)
                    }





                }



            Column() {

                if(videoUrl != null && isShowVideo){

                    CustomButton(btnText = "Save Video", onClick = {
                        videoUrl = null
                        navHostController.popBackStack()
                    })

                    Spacer(modifier = Modifier.height(10.dp.scaleSize()))

                }else{

                    Spacer(modifier = Modifier.height(10.dp.scaleSize()))
                    CustomButton(btnText = "Record Video", onClick = {

                        val uniqueFileName = "new_video_${System.currentTimeMillis()}.mp4"
//                        val permissionCheckResult = ContextCompat.checkSelfPermission(context,Me)
                        try {

                            val contentValues = ContentValues().apply {
                                put(MediaStore.MediaColumns.DISPLAY_NAME, uniqueFileName)
                                put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
                                put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/CapturedVideos")
                            }

                            videoUrl = context.contentResolver.insert(
                                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                                contentValues,

                            )

                            println("videoUrl:::::::::::$videoUrl")

                            if(videoUrl != null){
                                captureVideoLauncher.launch(videoUrl!!)
                            }

                        }catch (e: Exception){

                            println("ERROR IS WORKING $e")

                        }


                    })
                    }
                    Spacer(modifier = Modifier.height(10.dp.scaleSize()))
                    CustomButton(btnText = "Upload Video", onClick = {
                        videoUrl = null
                        getContentLauncher.launch("video/*")
                    })
                    Spacer(modifier = Modifier.height(10.dp.scaleSize()))
                }


            }
        }
    }



@Preview
@Composable
private fun PostVideoPagePreview() {
    LtsTheme {
        PostVideoPage(navHostController = rememberNavController())
    }
}
@Composable
fun FloatingButton(){
    FloatingActionButton(onClick = {},
        shape = CircleShape,
        containerColor = kPrimaryColor,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 8.dp
        )
    ) {
        Icon(Icons.Default.Add,
            contentDescription = null,
            tint = Color.White
        )
    }
}

enum class MultiFabState {
    COLLAPSED, EXPANDED
}
class FabItem(
    val icon: Painter,
    val label: String,
    val onFabItemClicked: () -> Unit
)
@Composable
fun MultiFloatingActionButton(
    fabIcon: Painter,
    items: List<FabItem>,
    showLabels: Boolean = true,
    onStateChanged: ((state: MultiFabState) -> Unit)? = null
){

    var currentState by remember { mutableStateOf(MultiFabState.COLLAPSED) }


}


@Composable
fun VideoPlayer(uri: Uri) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
        }
    }

    DisposableEffect(
        AndroidView(factory = {
            PlayerView(context).apply {
                player = exoPlayer
            }
        })
    ) {
        onDispose {
            exoPlayer.release()
        }
    }
}


