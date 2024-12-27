package com.example.lts.ui.post_video

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.collection.arrayMapOf
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
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
import com.example.lts.custom_component.CustomCountryDropdown
import com.example.lts.custom_component.CustomTextFromFiled
import com.example.lts.custom_component.CustomTopBar
import com.example.lts.ui.auth.data.response.CountryData
import com.example.lts.ui.categories.data.request.GetCategoryRequestModel
import com.example.lts.utils.extainstion.createUniqueImageFileUri
import com.example.lts.utils.extainstion.kBlackW700FS18
import com.example.lts.utils.extainstion.kSecondaryTextColorW500FS15
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.ui.theme.kBackGround
import com.example.lts.utils.extainstion.kWhiteW500FS17
import com.example.lts.utils.scaleSize
import com.sqt.lts.R
import com.sqt.lts.custom_component.CustomNetworkImageView
import com.sqt.lts.ui.categories.event.CategoriesEvent
import com.sqt.lts.ui.categories.state.CategoriesState
import com.sqt.lts.ui.categories.state.CategoryType
import com.sqt.lts.ui.channels.data.request.ChannelRequestModel
import com.sqt.lts.ui.channels.event.ChannelEvent
import com.sqt.lts.ui.channels.state.ChannelUiState
import com.sqt.lts.ui.theme.kBlackColor
import com.sqt.lts.ui.theme.kPrimaryColor
import com.sqt.lts.ui.theme.kWhite
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SimpleDateFormat", "SuspiciousIndentation")
@Composable
fun PostVideoPage(
    navHostController: NavHostController,
    kChannelFunction: (ChannelEvent) -> Unit,
    channelUiState: ChannelUiState? = null,
    kCategoriesFunction: (CategoriesEvent) -> Unit,
    categoriesState: CategoriesState?=null,

    ) {

    val lazyListState = rememberLazyListState()
    val isPagingLoading = (categoriesState?.isLoading == true && categoriesState.categories.isNotEmpty())
    LaunchedEffect(Unit) {
        kChannelFunction(ChannelEvent.GetSavePostForChannelData(
            channelRequestModel = ChannelRequestModel(
                sortColumn = "trending",
                sortDirection = "desc",
                exceptChannelIds = "",
                myCreatedChannel = 1,
                myFollowingChannel = 0
            )
        ))

    }



    LaunchedEffect(Unit) {
        kCategoriesFunction(CategoriesEvent.GetAllCategoryDataForPostVideo(getCategoryRequestModel = GetCategoryRequestModel(displayLoginUserCategory = 0, sortColumn = "trending", sortDirection = "desc", limit = 100), isFirst = true))
    }


    val isLoading = channelUiState?.isLoading == true
    var videoUrl by remember { mutableStateOf<Uri?>(null) }
    var isBottomSheet by remember { mutableStateOf<Boolean>(false) }
    var isShowVideo by remember { mutableStateOf<Boolean>(false) }

    val context = LocalContext.current

    var hasPermission by remember { mutableStateOf<Boolean?>(null) }


    var mediaTitle by remember { mutableStateOf<String?>(null) }
    var about by remember { mutableStateOf<String?>(null) }
    var cast by remember { mutableStateOf<String?>(null) }
    var videoLanguage by remember { mutableStateOf<String?>(null) }

    var channel by remember { mutableStateOf<CountryData?>(null) }
    var orientation by remember { mutableStateOf<String?>("straight") }
    var mediaType by remember { mutableStateOf<String?>(null) }


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

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasPermission = isGranted
    }


    var mainProfileUrl by remember { mutableStateOf<Uri?>(null) }
    var profileCameraUri by remember { mutableStateOf<Uri?>(null) }
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture(),
        onResult = {
            if (it) {
                mainProfileUrl = profileCameraUri
                isBottomSheet = false
            }
        }
    )

    val requestCameraPermissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(), onResult = {
        if(it){
            val createUniqueImageFileUri = createUniqueImageFileUri(context)
            profileCameraUri = createUniqueImageFileUri
            cameraLauncher.launch(createUniqueImageFileUri)
        }
    })

    val pickMedia = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia(),
        onResult = {
            mainProfileUrl = it
            isBottomSheet = false
        }
    )



    val sheetState = rememberModalBottomSheetState()





    Scaffold(
        containerColor = kBackGround,
        topBar = {
            CustomTopBar(navHostController = navHostController, title = "Post Video")
        },
    ) { paddingValues ->


        if(isBottomSheet){
            ModalBottomSheet(
                onDismissRequest = {isBottomSheet = false},
                sheetState = sheetState
            ) {

                Column(modifier = Modifier.padding(horizontal = 10.dp.scaleSize())) {
                    Text(text = "Upload Photo", style = TextStyle.Default.kBlackW700FS18())

                    Spacer(modifier = Modifier.height(20.dp.scaleSize()))

                    Row {
                        OutlinedButton(
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(width = 1.dp, color = kBlackColor),
                            onClick = {



                                if(ContextCompat.checkSelfPermission(context,
                                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                                    val createUniqueImageFileUri = createUniqueImageFileUri(context)



                                    if(isBottomSheet){
                                        profileCameraUri = createUniqueImageFileUri
                                        cameraLauncher.launch(createUniqueImageFileUri)
                                    }


                                }else{

                                    if(isBottomSheet){

                                        requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                                    }



                                }
                            }) {
                            Image(modifier = Modifier.size(30.dp.scaleSize()), painter = painterResource(id = R.drawable.camera), contentDescription = "")
                        }
                        Spacer(modifier = Modifier.width(10.dp.scaleSize()))
                        OutlinedButton(
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(width = 1.dp, color = kBlackColor),
                            onClick = {

                                if(isBottomSheet){
                                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                }
                            }) {
                            Image(
                                modifier = Modifier.size(30.dp.scaleSize()),
                                painter = painterResource(id = R.drawable.gallery),
                                contentDescription = ""
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp.scaleSize()))
                }

            }
        }


        if(videoUrl != null && isShowVideo){

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues = paddingValues)
                    .padding(horizontal = 20.dp.scaleSize()),
                state = lazyListState
            ) {

                item {

                    Column {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(300.dp)) {
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
                        }
                        Spacer(modifier = Modifier.height(10.dp.scaleSize()))

                        CustomTextFromFiled(titleText = "Media Title", keyboardType = KeyboardType.Text,onValueChange = { mediaTitle = it }, value = mediaTitle?:"")
                        Spacer(modifier = Modifier.height(height = 20.dp.scaleSize()))

                        CustomTextFromFiled(titleText = "About", keyboardType = KeyboardType.Text,onValueChange = { about = it }, value = about?:"")
                        Spacer(modifier = Modifier.height(height = 20.dp.scaleSize()))

                        CustomTextFromFiled(titleText = "Cast", keyboardType = KeyboardType.Text,onValueChange = { cast = it }, value = cast?:"")
                        Spacer(modifier = Modifier.height(height = 20.dp.scaleSize()))

                        CustomTextFromFiled(titleText = "Language In Video", keyboardType = KeyboardType.Text,onValueChange = { videoLanguage = it }, value = videoLanguage?:"")
                        Spacer(modifier = Modifier.height(height = 20.dp.scaleSize()))

                        CustomTextFromFiled(
                            enabled = false,
                            titleText = "Orientation",
                            keyboardType = KeyboardType.Text,
                            onValueChange = { orientation = it },
                            value = orientation?:"")
                        Spacer(modifier = Modifier.height(height = 20.dp.scaleSize()))

                        CustomTextFromFiled(titleText = "Media Type", keyboardType = KeyboardType.Text,onValueChange = { mediaType = it }, value = mediaType?:"")
                        Spacer(modifier = Modifier.height(height = 20.dp.scaleSize()))

                        CustomCountryDropdown(
                            hintText = "Select Channel", title = "Channel",
                            onItemSelected = {
                                channel = it
                            },
                            isLoading =isLoading,
                            countryList = channelUiState?.countryList
                        )

                        Spacer(modifier = Modifier.height(height = 20.dp.scaleSize()))

                        Text(text = "Video Category",style = TextStyle.Default.kSecondaryTextColorW500FS15())
                        Spacer(modifier = Modifier.height(height = 20.dp.scaleSize()))

                    }



                }

                items(if((categoriesState?.categories?.size?:0) % 2 != 0) ((categoriesState?.categories?.size?:0)/2).plus(1) else ((categoriesState?.categories?.size ?: 0)/2)){
                    Row() {

                        Row(modifier = Modifier.weight(1F), horizontalArrangement = Arrangement.Start,Alignment.CenterVertically) {
                            Checkbox(
                                checked = categoriesState?.categories?.get(it + it)?.selectedCategory ?: false,
                                onCheckedChange = {  isSelected ->
                                    kCategoriesFunction(CategoriesEvent.UpdatePostVideoCategoriesValue(categoriesState?.categories?.get(it + it)?.categoryid?:0,isSelected))
                                }
                            )
                            Spacer(Modifier.width(10.dp))
                            Text(categoriesState?.categories?.get(it + it)?.categoryname?:"", style = TextStyle.Default.kSecondaryTextColorW500FS15())
                        }


                        if(categoriesState?.categories?.size != it + it + 1){

                            Row(modifier = Modifier.weight(1F),Arrangement.Start,Alignment.CenterVertically) {
                                Checkbox(
                                    checked = categoriesState?.categories?.get(it + it + 1)?.selectedCategory ?: false,
                                    onCheckedChange = { isSelected ->
                                        kCategoriesFunction(CategoriesEvent.UpdatePostVideoCategoriesValue(categoriesState?.categories?.get(it + it + 1)?.categoryid?:0,isSelected))
                                    }
                                )
                                Spacer(Modifier.width(10.dp))
                                Text(categoriesState?.categories?.get(it + it + 1)?.categoryname?:"", style = TextStyle.Default.kSecondaryTextColorW500FS15())
                            }
                        }


//                   Text("${categoriesState?.categories?.get(it + it)?.categoryname}")
//                   Text("${categoriesState?.categories?.get(it + it + 1)?.categoryname}")
//                   Text("${it + it + 2 - 1}")

//                   Text(if(it == 0) it.toString() else (it + it - 1).toString() )
//                   Text(if(it == 0) (it+1).toString() else (it + it - 2).toString() )
//                      Text()
//                     Text(categoriesState?.categories?.get(it+it+1)?.categoryname ?: "",style = TextStyle.Default.kSecondaryTextColorW500FS15(), modifier = Modifier.weight(1F))
//                     Text(categoriesState?.categories?.get(it+it+2)?.categoryname ?: "",style = TextStyle.Default.kSecondaryTextColorW500FS15(), modifier = Modifier.weight(1F))
                    }
                }

                item {
                    CustomButton(
                        btnText = "PostVideo",
                        onClick = {}
                    )
                }
            }


            }
        else
            {
            Column(modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 10.dp.scaleSize())
                .fillMaxSize()) {
                Text(
                    modifier = Modifier
                        .weight(1F)
                        .align(Alignment.CenterHorizontally),
                    text = "Upload your video",
                    style = TextStyle.Default.kWhiteW500FS17(),
                    textAlign = TextAlign.Center
                )
                CustomButton(btnText = "Record Video", onClick = {
////
                    val isPermissionGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED

                    if(hasPermission == null || hasPermission == false){
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                    else
                    {
                        val uniqueFileName = "new_video_${System.currentTimeMillis()}.mp4"
                        val contentValues = ContentValues().apply {
                            put(MediaStore.MediaColumns.DISPLAY_NAME, uniqueFileName)
                            put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
                            put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/CapturedVideos")
                        }

                        videoUrl = context.contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues)

                        if(videoUrl != null){

                            captureVideoLauncher.launch(videoUrl!!)

                        }
                    }

                    println("isPermissionGranted : $isPermissionGranted")

                })
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
        PostVideoPage(
            navHostController = rememberNavController(),
            kChannelFunction = {},
            kCategoriesFunction = {},

        )
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


