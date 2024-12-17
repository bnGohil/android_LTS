package com.example.lts.ui.profile

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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
import coil.compose.rememberAsyncImagePainter
import com.example.lts.base.BaseCommonResponseModel
import com.sqt.lts.R
import com.example.lts.custom_component.CustomButton
import com.example.lts.custom_component.CustomCountryDropdown
import com.example.lts.custom_component.CustomTextFromFiled
import com.example.lts.custom_component.CustomTopBar
import com.example.lts.ui.auth.data.response.CountryData
import com.example.lts.ui.auth.state.CountryDataState
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.ui.theme.kBackGround
import com.sqt.lts.ui.theme.kBlackColor
import com.sqt.lts.ui.theme.kCardBackgroundColor
import com.sqt.lts.ui.theme.kPrimaryColor
import com.sqt.lts.ui.theme.kWhite
import com.example.lts.utils.extainstion.createUniqueImageFileUri
import com.example.lts.utils.extainstion.kBlackW700FS18
import com.example.lts.utils.extainstion.kWhiteW400FS13
import com.example.lts.utils.extainstion.kWhiteW500FS17
import com.example.lts.utils.network.DataState
import com.example.lts.utils.scaleSize
import com.sqt.lts.custom_component.CustomNetworkImageView
import com.sqt.lts.ui.profile.event.ProfileEvent
import com.sqt.lts.ui.profile.request.UserDetailUpdateRequestModel
import com.sqt.lts.ui.profile.state.UserDetailGetState
import com.sqt.lts.utils.enums.ProfileSettingEnums


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSettingPage(
    navHostController: NavHostController,
    updateDetail:DataState<BaseCommonResponseModel.Data?>?=null,
    onSettingEvent:(ProfileEvent) -> Unit,
    userDetailGetState:UserDetailGetState?=null,
    countryUiState: CountryDataState?=null
    ) {

    var isUpdateUserDetailLoading =  remember { mutableStateOf<Boolean?>(null) }


    LaunchedEffect(updateDetail) {

        when(updateDetail){
            is DataState.Error -> {
                isUpdateUserDetailLoading.value = false
            }
            DataState.Loading -> {
                isUpdateUserDetailLoading.value = true
            }
            is DataState.Success -> {

                isUpdateUserDetailLoading.value = false
                navHostController.popBackStack()
            }
            null -> {}
        }

    }




    var isBottomSheet by remember { mutableStateOf<Boolean>(false) }
    var isBannerBottomSheet by remember { mutableStateOf<Boolean>(false) }
    val context = LocalContext.current
    var mainProfileUrl by remember { mutableStateOf<Uri?>(null) }
    var profileCameraUri by remember { mutableStateOf<Uri?>(null) }
    var mainBannerUrl by remember { mutableStateOf<Uri?>(null) }
    var cameraBannerUrl by remember { mutableStateOf<Uri?>(null) }
    val sheetState = rememberModalBottomSheetState()

    var countryData by remember { mutableStateOf<CountryData?>(null) }

    LaunchedEffect(userDetailGetState?.data?.country,countryUiState?.getCountryDataModel?.data) {

        snapshotFlow { userDetailGetState?.data?.country }.collect{





            if(it != null){

                println("userDetailGetState?.data?.country : ${it.toInt()}")
                println("countryUiState?.getCountryDataModel?.data ${countryUiState?.getCountryDataModel?.data}")


                countryData  = countryUiState?.getCountryDataModel?.data?.find { data -> data.countryid == it.toInt() }

                println("data is ${countryData}")
            }
        }

    }

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




    Scaffold(
        containerColor = kBackGround,
        topBar = { CustomTopBar(title = "Profile & Setting", navHostController) },
    ) { paddingValues ->

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

                                if(ContextCompat.checkSelfPermission(context,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
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

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .fillMaxWidth()
                .padding(horizontal = 20.dp.scaleSize(), vertical = 20.dp.scaleSize())
        ) {

            if(mainProfileUrl != null){
                Image(painter = rememberAsyncImagePainter(model = mainProfileUrl),
                    contentDescription = "",
                    Modifier
                        .clickable { isBottomSheet = true }
                        .clip(CircleShape)
                        .size(130.dp),
                    contentScale = ContentScale.FillBounds)

            } else {



                CustomNetworkImageView(
                    imagePath = userDetailGetState?.data?.photourl?:"",
                    modifier = Modifier
                        .clickable { isBottomSheet = true }
                        .clip(CircleShape)
                        .size(130.dp),
                    contentScale = ContentScale.FillBounds
                )
            }

            Spacer(modifier = Modifier.height(15.dp.scaleSize()))

            Text(text = "Banner", modifier = Modifier.fillMaxWidth(), style = TextStyle.Default.kWhiteW500FS17(), textAlign = TextAlign.Left)
            Spacer(modifier = Modifier.height(10.dp.scaleSize()))

            if(mainBannerUrl != null){

                Image(
                    modifier = Modifier
                        .clickable {
                            isBannerBottomSheet = true
                        }
                        .fillMaxWidth()
                        .height(120.dp.scaleSize())
                        .clip(RoundedCornerShape(10)),
                    painter = rememberAsyncImagePainter(model = mainBannerUrl),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )

            }else{
                Box(
                    modifier = Modifier
                        .clickable {
                            isBannerBottomSheet = true
                        }
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10))
                        .background(kCardBackgroundColor)
                ) {
                    CustomNetworkImageView(
                        imagePath = userDetailGetState?.data?.bannerurl?:"",
                        modifier = Modifier
                            .clickable { isBottomSheet = true }
                            .fillMaxWidth().height(130.dp),
                        contentScale = ContentScale.FillBounds
                    )

                }
            }



            Spacer(modifier = Modifier.height(15.dp.scaleSize()))

            CustomTextFromFiled(titleText = "First Name", keyboardType = KeyboardType.Text, onValueChange = {
                onSettingEvent(ProfileEvent.UpdateTextDataEvent(text = it, type = ProfileSettingEnums.FIRST_NAME))
            }, value = userDetailGetState?.data?.fname)

            Spacer(modifier = Modifier.height(15.dp.scaleSize()))

            CustomTextFromFiled(titleText = "Last Name", keyboardType = KeyboardType.Text,onValueChange = {
                onSettingEvent(ProfileEvent.UpdateTextDataEvent(text = it, type = ProfileSettingEnums.LAST_NAME))
            }, value = userDetailGetState?.data?.lname)

            Spacer(modifier = Modifier.height(15.dp.scaleSize()))

            CustomTextFromFiled(

                titleText = "Email Address", keyboardType = KeyboardType.Email,onValueChange = {
                onSettingEvent(ProfileEvent.UpdateTextDataEvent(text = it, type = ProfileSettingEnums.EMAIL))
            }, value = userDetailGetState?.data?.email)

            Spacer(modifier = Modifier.height(15.dp.scaleSize()))

            CustomTextFromFiled(titleText = "Address1", keyboardType = KeyboardType.Text,onValueChange = {
                onSettingEvent(ProfileEvent.UpdateTextDataEvent(text = it, type = ProfileSettingEnums.ADD_1))
            },value = userDetailGetState?.data?.add1)

            Spacer(modifier = Modifier.height(15.dp.scaleSize()))

            CustomTextFromFiled(
                titleText =  "Address2",
                keyboardType = KeyboardType.Text,
                onValueChange = {
                    onSettingEvent(ProfileEvent.UpdateTextDataEvent(text = it, type = ProfileSettingEnums.ADD_2))
                                },
                value = userDetailGetState?.data?.add2)

            Spacer(modifier = Modifier.height(15.dp.scaleSize()))

            CustomTextFromFiled(titleText  = "Zip Code", keyboardType = KeyboardType.Text,onValueChange = {
                onSettingEvent(ProfileEvent.UpdateTextDataEvent(text = it, type = ProfileSettingEnums.ZIP_CODE))
            },value = userDetailGetState?.data?.zipcode)

            Spacer(modifier = Modifier.height(15.dp.scaleSize()))

            CustomTextFromFiled(titleText =  "City", keyboardType = KeyboardType.Text,onValueChange = {
                onSettingEvent(ProfileEvent.UpdateTextDataEvent(text = it, type = ProfileSettingEnums.CITY))
            },value = userDetailGetState?.data?.city)

            Spacer(modifier = Modifier.height(15.dp.scaleSize()))

            CustomTextFromFiled(titleText = "State", keyboardType = KeyboardType.Text,onValueChange = {
                onSettingEvent(ProfileEvent.UpdateTextDataEvent(text = it, type = ProfileSettingEnums.STATE))
            },value = userDetailGetState?.data?.state)

            Spacer(modifier = Modifier.height(15.dp.scaleSize()))

            CustomTextFromFiled(titleText = "Facebook Profile", keyboardType = KeyboardType.Text,onValueChange = {
                onSettingEvent(ProfileEvent.UpdateTextDataEvent(text = it, type = ProfileSettingEnums.FACEBOOK))
            },value = userDetailGetState?.data?.facebookprofile)

            Spacer(modifier = Modifier.height(15.dp.scaleSize()))

            CustomCountryDropdown(hintText = "Select country", value = countryData, title = "Country", onItemSelected = { countryData = it }, countryList = countryUiState?.getCountryDataModel?.data)
            Spacer(modifier = Modifier.height(height = 20.dp.scaleSize()))

            CustomTextFromFiled(titleText = "Twitter Profile", keyboardType = KeyboardType.Text,onValueChange = {
                onSettingEvent(ProfileEvent.UpdateTextDataEvent(text = it, type = ProfileSettingEnums.TWITTER))
            },value = userDetailGetState?.data?.twitterprofile)

            Spacer(modifier = Modifier.height(15.dp.scaleSize()))

            CustomTextFromFiled(titleText = "Instagram Profile", keyboardType = KeyboardType.Text,onValueChange = {
                onSettingEvent(ProfileEvent.UpdateTextDataEvent(text = it, type = ProfileSettingEnums.INSTAGRAM))
            },value = userDetailGetState?.data?.instagramprofile)

            Spacer(modifier = Modifier.height(15.dp.scaleSize()))


            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    modifier = Modifier.weight(1F),
                    shape = RoundedCornerShape(10),
                    border = BorderStroke(width = 1.dp, color = kPrimaryColor,),
                    onClick = { navHostController.popBackStack() }) {
                    Text(text = "Cancel", style = TextStyle.Default.kWhiteW400FS13())
                }
                Spacer(modifier = Modifier.width(10.dp.scaleSize()))
                Box(modifier = Modifier.weight(1F)){
                    CustomButton(
                        isLoading = isUpdateUserDetailLoading.value,
                        btnText = "Save Changes", onClick = {
                            onSettingEvent(ProfileEvent.UpdateUserDetailEvent(
                                data = UserDetailUpdateRequestModel(
                                    instagramprofile = userDetailGetState?.data?.instagramprofile,
                                    facebookprofile = userDetailGetState?.data?.facebookprofile,
                                    twitterprofile = userDetailGetState?.data?.twitterprofile,
                                    fname = userDetailGetState?.data?.fname,
                                    city = userDetailGetState?.data?.city,
                                    state = userDetailGetState?.data?.state,
                                    lname = userDetailGetState?.data?.lname,
                                    add2 = userDetailGetState?.data?.add2,
                                    add1 = userDetailGetState?.data?.add1,
                                    zipcode = userDetailGetState?.data?.zipcode,
                                    photoUri = mainProfileUrl,
                                    bannerUri = mainBannerUrl,
                                    country = countryData?.countryid.toString(),
                                )
                            ))
//
                    })
                }
            }






        }
    }
}

@Preview
@Composable
private fun ProfileSettingPagePreview() {
    LtsTheme {
        ProfileSettingPage(navHostController = rememberNavController(), onSettingEvent = {})
    }
}