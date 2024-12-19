package com.sqt.lts.ui.channels.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.lts.base.BaseCommonResponseModel
import com.example.lts.custom_component.CustomButton
import com.example.lts.custom_component.CustomTextFromFiled
import com.example.lts.custom_component.CustomTopBar
import com.example.lts.utils.extainstion.createUniqueImageFileUri
import com.example.lts.utils.extainstion.kBlackW700FS18
import com.example.lts.utils.extainstion.kWhiteW500FS17
import com.example.lts.utils.network.DataState
import com.example.lts.utils.scaleSize
import com.sqt.lts.R
import com.sqt.lts.custom_component.CustomNetworkImageView
import com.sqt.lts.datasource.remote.RestApiService
import com.sqt.lts.navigation.route.CreateChannelRoute
import com.sqt.lts.ui.channels.event.ChannelEvent
import com.sqt.lts.ui.channels.state.ChannelDetailUiState
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.ui.theme.kBackGround
import com.sqt.lts.ui.theme.kBlackColor
import com.sqt.lts.ui.theme.kWhite
import com.sqt.lts.utils.enums.ChannelUpdateNotUpdateType
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@SuppressLint("Recycle")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateChannelsPage(
    createChannelRoute: CreateChannelRoute?=null,
    channelDetailUiState: ChannelDetailUiState?=null,
    navHostController: NavHostController,
    onChannel: (ChannelEvent)-> Unit,
    baseCommonResponseModel: DataState<BaseCommonResponseModel.Data?>?=null,
    updateChannelData: DataState<BaseCommonResponseModel.Data?>?=null

) {

    val context = LocalContext.current
    var isLoading by remember { mutableStateOf<Boolean?>(null) }





    LaunchedEffect(baseCommonResponseModel) {

        when(baseCommonResponseModel){
            is DataState.Error -> {
                isLoading = false
                Toast.makeText(context,baseCommonResponseModel.exception.message, Toast.LENGTH_LONG).show()
            }
            is DataState.Loading -> {
                isLoading = true
            }
            is DataState.Success -> {
                isLoading = false
                Toast.makeText(context,baseCommonResponseModel.message, Toast.LENGTH_LONG).show()
            }
            null -> {
                isLoading = false
            }
        }

    }

    LaunchedEffect(createChannelRoute) {

        when(createChannelRoute?.channelUpdateNotUpdateType){

            ChannelUpdateNotUpdateType.CHANNEL_UPDATE -> {
                onChannel(ChannelEvent.GetChannelDetailEvent(channelId = createChannelRoute.channelId?:0))
            }

            ChannelUpdateNotUpdateType.CHANNEL_NOT_UPDATE -> {

            }
            null -> {}
        }
    }

    LaunchedEffect(updateChannelData) {

        when(updateChannelData){
            is DataState.Error -> {
                isLoading = false
                Toast.makeText(context,updateChannelData.exception.message, Toast.LENGTH_LONG).show()
            }
            is DataState.Loading -> {
                isLoading = true
            }
            is DataState.Success -> {
                isLoading = false
                Toast.makeText(context,updateChannelData.message, Toast.LENGTH_LONG).show()
            }
            null -> {}
        }
    }




    val scope = rememberCoroutineScope()

    var isBottomSheet by remember { mutableStateOf<Boolean>(false) }
    val channelName = remember { mutableStateOf<String>("") }
    var isBannerBottomSheet by remember { mutableStateOf<Boolean>(false) }
    var mainProfileUrl by remember { mutableStateOf<Uri?>(null) }
    var profileCameraUri by remember { mutableStateOf<Uri?>(null) }
    var mainBannerUrl by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var cameraBannerUrl by remember { mutableStateOf<Uri?>(null) }
    val sheetState = rememberModalBottomSheetState()

    val pickMedia = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia(),
        onResult = {
            mainProfileUrl = it
            isBottomSheet = false
        }
    )

    val pickBannerMedia = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia(),
        onResult = {
            mainBannerUrl = it

            isBottomSheet = false
        }
    )





    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture(),
        onResult = {
            if (it) {
                mainProfileUrl = profileCameraUri
                isBottomSheet = false
            }
        }
    )

    val cameraBannerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture(),
        onResult = {
            if (it) {
                mainBannerUrl = cameraBannerUrl
                isBannerBottomSheet = false
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
    val requestBannerCameraPermissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(), onResult = {
        if(it){
            val createUniqueImageFileUri = createUniqueImageFileUri(context)
            cameraBannerUrl = createUniqueImageFileUri
            cameraBannerLauncher.launch(createUniqueImageFileUri)
        }
    })


    LaunchedEffect(channelDetailUiState?.channelData) {

        if(channelDetailUiState?.channelData != null){
            channelName.value = channelDetailUiState.channelData.channelname?:""
            mainProfileUrl = Uri.parse(channelDetailUiState.channelData.channelimgurl?:"")
            mainBannerUrl = Uri.parse(channelDetailUiState.channelData.bannerurl?:"")
        }

    }

    Scaffold(
        containerColor = kBackGround,
        topBar = {
            CustomTopBar(navHostController = navHostController, title = "Create Channel")
        }
    ) {
        paddingValues -> Column(modifier = Modifier.padding(paddingValues)) {
        if(isBottomSheet || isBannerBottomSheet){
            ModalBottomSheet(
                onDismissRequest = {isBottomSheet = false
                    isBannerBottomSheet = false},
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

                                    if(isBannerBottomSheet){
                                        cameraBannerUrl = createUniqueImageFileUri
                                        cameraBannerLauncher.launch(createUniqueImageFileUri)
                                    }

                                    if(isBottomSheet){
                                        profileCameraUri = createUniqueImageFileUri
                                        cameraLauncher.launch(createUniqueImageFileUri)
                                    }


                                }else{

                                    if(isBottomSheet){

                                        requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                                    }


                                    if(isBannerBottomSheet){

                                        requestBannerCameraPermissionLauncher.launch(Manifest.permission.CAMERA)

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
                                if(isBannerBottomSheet){
                                    pickBannerMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                }
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

        if(channelDetailUiState?.isLoading == true){
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }else{
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp.scaleSize(), vertical = 20.dp.scaleSize())
            ) {

//                if(bitmap != null){
//                    Image(
//                        bitmap = bitmap!!.asImageBitmap(),
//                        contentDescription = "Selected Image",
//                        modifier = Modifier
//                            .size(200.dp)
//                            .padding(8.dp),
//                        contentScale = ContentScale.Crop
//                    )
//                }

                if(mainProfileUrl != null){
                    CustomNetworkImageView(
                        imagePath = mainProfileUrl?.toString(),
                        modifier= Modifier
                            .clickable { isBottomSheet = true }
                            .clip(CircleShape)
                            .size(130.dp), contentScale = ContentScale.FillBounds)
                }else{
                    Image(
                        modifier = Modifier
                            .size(150.dp.scaleSize())
                            .clip(RoundedCornerShape(100))
                            .clickable { isBottomSheet = true },
                        painter = painterResource(id = R.drawable.image_not_found),
                        colorFilter = ColorFilter.tint(kWhite, BlendMode.SrcIn),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = "placeholder"
                    )
                }

                Spacer(modifier = Modifier.height(15.dp.scaleSize()))

                Text(text = "Banner", modifier = Modifier.fillMaxWidth(), style = TextStyle.Default.kWhiteW500FS17(), textAlign = TextAlign.Left)
                Spacer(modifier = Modifier.height(10.dp.scaleSize()))

                if(mainBannerUrl != null){

                    CustomNetworkImageView(
                        imagePath = mainBannerUrl?.toString(),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clickable {
                                isBannerBottomSheet = true
                            }
                            .fillMaxWidth()
                            .height(120.dp.scaleSize())
                            .clip(RoundedCornerShape(10)),
                    )

                }else{


                    Image(
                        modifier = Modifier
                            .clickable {
                                isBannerBottomSheet = true
                            }
                            .fillMaxWidth()
                            .height(120.dp.scaleSize())
                            .clip(RoundedCornerShape(10)),
                        painter = painterResource(id = R.drawable.placeholder_error),
                        colorFilter = ColorFilter.tint(kWhite, BlendMode.SrcIn),
                        contentDescription = "placeholder",
                        contentScale = ContentScale.Crop
                    )
                }


                Spacer(modifier = Modifier.height(15.dp.scaleSize()))

                CustomTextFromFiled(
                    value = channelName.value,
                    singleLine = true,
                    titleText = "Channel Name",
                    keyboardType = KeyboardType.Text, onValueChange = {
                        channelName.value = it
                    })

                Spacer(modifier = Modifier.height(15.dp.scaleSize()))

                CustomButton(btnText =  if(createChannelRoute?.channelUpdateNotUpdateType == ChannelUpdateNotUpdateType.CHANNEL_NOT_UPDATE) "Add" else "Update",
                    isLoading = isLoading,
                    onClick = {

                        when(createChannelRoute?.channelUpdateNotUpdateType){

                            ChannelUpdateNotUpdateType.CHANNEL_UPDATE -> {

                                onChannel(ChannelEvent.UpdateChannelEvent(
                                    channelName = channelName.value,
                                    photo = if(mainProfileUrl == Uri.parse(channelDetailUiState?.channelData?.channelimgurl)) null else mainProfileUrl ,
                                    banner = if(mainBannerUrl == Uri.parse(channelDetailUiState?.channelData?.bannerurl)) null else mainBannerUrl,
                                    channelId = createChannelRoute.channelId
                                ))

                            }

                            ChannelUpdateNotUpdateType.CHANNEL_NOT_UPDATE -> {
                                onChannel(ChannelEvent.CreateChannelEvent(
                                    channelName = channelName.value,
                                    photo = mainProfileUrl,
                                    banner = mainBannerUrl
                                ))
                            }
                            null -> {}
                        }

                    })
            }
        }


    }
    }


}

fun uriToBitmap(contentResolver: ContentResolver, uri: Uri): Bitmap? {
    return try {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            // For API levels below 28
            MediaStore.Images.Media.getBitmap(contentResolver, uri)
        } else {
            // For API 28 and above
            val source = ImageDecoder.createSource(contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@Preview(backgroundColor = 1L, showBackground = true)
@Composable
private fun CreateChannelsPagePreview() {
    LtsTheme {
        CreateChannelsPage(navHostController = rememberNavController(), onChannel = {})
    }
}