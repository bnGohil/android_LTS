package com.example.lts.ui.trending.presentation
import android.annotation.SuppressLint
import android.content.ClipData

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivities
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sqt.lts.ui.channels.event.ChannelEvent
import com.sqt.lts.ui.channels.event.FollowingType
import com.example.lts.ui.dummy.ClickVideo
import com.sqt.lts.R
import com.sqt.lts.ui.channels.channel_custom_component.isChangeText
import com.example.lts.ui.dummy.ExoPlayerPlaylist
import com.example.lts.ui.shimmer.ShimmerEffectBox
import com.example.lts.ui.trending.component.VideoComponent
import com.example.lts.ui.trending.state.TrendingState

import com.sqt.lts.ui.theme.kBackGround
import com.sqt.lts.ui.theme.kCardBackgroundColor
import com.sqt.lts.ui.theme.kPrimaryColor
import com.sqt.lts.ui.theme.kWhite
import com.example.lts.utils.extainstion.kLightTextColorW600FS20
import com.example.lts.utils.extainstion.kPrimaryColorW400FS15
import com.example.lts.utils.extainstion.kWhiteW300FS12
import com.example.lts.utils.extainstion.kWhiteW400FS13
import com.example.lts.utils.extainstion.kWhiteW500FS17
import com.example.lts.utils.network.DataState
import com.example.lts.utils.scaleSize
import com.sqt.lts.custom_component.CustomNetworkImageView
import com.sqt.lts.ui.channels.state.ChannelFollowingState
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.ui.theme.kLightTextColor
import com.sqt.lts.ui.theme.kSecondaryColor
import com.sqt.lts.ui.trending.data.request.TrendingRequestModel
import com.sqt.lts.ui.trending.data.response.PlayListItem
import com.sqt.lts.ui.trending.data.response.TrendingLikeAndDisLikeModel
import com.sqt.lts.ui.trending.data.response.VideoAudio
import com.sqt.lts.ui.trending.event.TrendingEvent
import com.sqt.lts.ui.trending.state.LikeAndDislikeUiState
import com.sqt.lts.ui.trending.state.TrendingVideoResourceUiState



@SuppressLint("QueryPermissionsNeeded")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TrendingVideoDetail(
    id: Int?=null,
    channelDataState:ChannelFollowingState?=null,
    list: List<PlayListItem?> = listOf(),
    trendingPlayListState: TrendingState?=null,
    onTrendingEvent: (TrendingEvent) -> Unit,
    onChannelEvent: (ChannelEvent) -> Unit,
    trendingVideoResourceUiState:TrendingVideoResourceUiState?=null,
    navController: NavHostController,
    likeAndDislikeUiState: LikeAndDislikeUiState?=null
) {

    val isChannelLoading = channelDataState?.isLoading == true

    val url = remember { mutableStateOf<String?>(null) }

    val clickType = remember { mutableStateOf<ClickVideo?>(ClickVideo.NAVIGATION) }


    LaunchedEffect(Unit) {
        onTrendingEvent(TrendingEvent.GetResourceVideoDetail(id))
        onTrendingEvent(TrendingEvent.GetTrendingPlayListData(
            trendingRequestModel = TrendingRequestModel(
                limit = 3,
                channelId = 0,
                mediaType = "",
                displayloginuseruploaded = 0,
                sortColumn = "date",
                sortDirection = "",
                exceptResourceIds = "$id",
                isFirst = true)
        ))
    }

    val clipboardManager: ClipboardManager = LocalClipboardManager.current




    val isLoading = trendingVideoResourceUiState?.isLoading == true
    val isPlayListLoading = trendingPlayListState?.isLoading == true && trendingPlayListState.videoAudioList?.isEmpty() == true
    val isPagingTrendingLoading = trendingPlayListState?.isLoading == true && trendingPlayListState.videoAudioList?.isNotEmpty() == true
    val data = trendingVideoResourceUiState?.data

    val followAndUnFollow = remember { mutableIntStateOf(0) }

    val context = LocalContext.current

    var isVideoLoading = remember { mutableStateOf<Boolean>(false) }

    LaunchedEffect(trendingVideoResourceUiState) {

        snapshotFlow { trendingVideoResourceUiState?.data }.collect{
            if(it?.resourceurl == null) return@collect
            url.value = null
            followAndUnFollow.intValue = trendingVideoResourceUiState?.data?.isFollowChannel?:0
            println("followAndUnFollow.intValue is ${followAndUnFollow.intValue}")
            url.value = it.resourceurl

        }

    }


    LaunchedEffect(channelDataState) {

        when(channelDataState?.data){

            is DataState.Error -> {

                Toast.makeText(context,channelDataState.errorMsg, Toast.LENGTH_LONG)

            }
            is DataState.Loading -> {

            }
            is DataState.Success -> {

                Toast.makeText(context,channelDataState.data.message, Toast.LENGTH_LONG)



                when(channelDataState.channelFollowingType){

                    FollowingType.FOLLOW -> {
                        followAndUnFollow.intValue = 1
                    }

                    FollowingType.UNFOLLOW -> {
                        followAndUnFollow.intValue = 0
                    }



                    null -> {


                    }
                }
            }
            null -> {

            }
        }

    }




    val isCheckForRowBtn = if(isLoading) Modifier
        .size(50.dp)
        .padding(3.dp)
        .clip(RoundedCornerShape(10)) else Modifier

    Scaffold(modifier = Modifier.background(kBackGround)) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .background(kBackGround)
                .fillMaxHeight()) {
            ShimmerEffectBox(
                isShow = isLoading,
                modifier = if(isLoading) Modifier
                    .fillMaxWidth()
                    .height(200.dp) else Modifier,
            ) {
                ExoPlayerPlaylist(
                    url = url.value,
                    onLoading = {
                        isVideoLoading.value = it
                        println("isVideoLoading : ${isVideoLoading.value}")
                    },
                    video = VideoAudio(
                        thumbimgurl = data?.thumbimgurl
                    )
                )
            }
            LazyColumn(modifier = Modifier.padding(horizontal = 10.dp.scaleSize(), vertical = 20.dp.scaleSize())) {

                item {
                    Column {
                        ShimmerEffectBox(
                            modifier = if(isLoading) Modifier
                                .fillMaxWidth()
                                .height(50.dp) else Modifier,
                            isShow = isLoading
                        ) {
                            Text(
                                text = trendingVideoResourceUiState?.data?.title?:"",
                                style = TextStyle.Default.kWhiteW500FS17()
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp.scaleSize()))

                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                            ShimmerEffectBox(
                                isShow = isLoading,
                                modifier = isCheckForRowBtn
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(kCardBackgroundColor)
                                        .padding(horizontal = 10.dp, vertical = 5.dp)
                                ) {
                                    Icon(
                                        painterResource(if(data?.islike == 0) R.drawable.like else R.drawable.likefilled),
                                        modifier = Modifier.clickable {
                                            onTrendingEvent(TrendingEvent.UpdateTrendingLikeAndDisLikeDetail(
                                                trendingLikeAndDisLikeModel = TrendingLikeAndDisLikeModel(
                                                    resourceid = data?.resourceid?:0,
                                                    isdislike = false,
                                                    islike = true
                                                )
                                            ))
                                        },
                                        contentDescription = "like", tint = kWhite
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Spacer(
                                        modifier = Modifier
                                            .height(18.dp.scaleSize())
                                            .width(1.dp.scaleSize())
                                            .background(kLightTextColor)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        if (data?.totallike != null) data.totallike.toString() else "0",
                                        style = TextStyle.Default.kWhiteW500FS17()
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            ShimmerEffectBox(
                                isShow = isLoading,
                                modifier = isCheckForRowBtn
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(kCardBackgroundColor)
                                        .padding(horizontal = 10.dp, vertical = 5.dp)
                                ) {
                                    Icon(
                                        painterResource(if(data?.isdislike == 0) R.drawable.dislike else R.drawable.dislikefilled),
                                        modifier = Modifier.clickable {
                                            onTrendingEvent(TrendingEvent.UpdateTrendingLikeAndDisLikeDetail(
                                                trendingLikeAndDisLikeModel = TrendingLikeAndDisLikeModel(
                                                    resourceid = data?.resourceid?:0,
                                                    isdislike = true,
                                                    islike = false
                                                )
                                            ))
                                        },
                                        contentDescription = "Dislike",
                                        tint = kWhite,
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Spacer(
                                        modifier = Modifier
                                            .height(18.dp.scaleSize())
                                            .width(1.dp.scaleSize())
                                            .background(kLightTextColor)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(if (data?.totaldislike != null) data.totaldislike.toString() else "0", style = TextStyle.Default.kWhiteW500FS17())
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            ShimmerEffectBox(
                                isShow = isLoading,
                                modifier = isCheckForRowBtn
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(kCardBackgroundColor)
                                        .padding(horizontal = 10.dp, vertical = 5.dp)
                                ) {
                                    Icon(
                                        painterResource(R.drawable.share),
                                        modifier = Modifier.clickable {

//                                            val urlToShare = "https://qa.listentoseniors.com/video-audio/${data?.resourceid}"
                                            val urlToShare = "https://qa.listentoseniors.com/video-audio/${data?.resourceid}?utm_source=app&utm_medium=share"

                                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                                type = "text/plain"
                                                putExtra(Intent.EXTRA_TEXT, urlToShare)
                                            }

                                            if (shareIntent.resolveActivity(context.packageManager) != null) {
                                                context.startActivity(Intent.createChooser(shareIntent, "Share URL via"))
                                            }

                                            onTrendingEvent(TrendingEvent.SharedTrendingData(resourceId = data?.resourceid?:0))


                                        },
                                        contentDescription = "Dislike",
                                        tint = kWhite
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Spacer(
                                        modifier = Modifier
                                            .height(18.dp.scaleSize())
                                            .width(1.dp.scaleSize())
                                            .background(kLightTextColor)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        if (data?.totalshare != null) data.totalshare.toString() else "0",
                                        style = TextStyle.Default.kWhiteW500FS17()
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))

                            ShimmerEffectBox(
                                    isShow = isLoading,
                                    modifier = isCheckForRowBtn
                                ) {
                                    IconButton(
                                        colors = IconButtonDefaults.iconButtonColors(containerColor = kCardBackgroundColor),
                                        onClick = {
                                            clipboardManager.setText(AnnotatedString("https://qa.listentoseniors.com/video-audio/${data?.resourceid}"))
                                        }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.copy),
                                            contentDescription = "copy",
                                            tint = kWhite
                                        )
                                    }
                                }



                        }

//                            Row() {
//                                ShimmerEffectBox(
//                                    isShow = isLoading,
//                                    modifier = isCheckForRowBtn
//                                ) {
//                                    IconButton(
//                                        colors = IconButtonDefaults.iconButtonColors(containerColor = kCardBackgroundColor),
//                                        onClick = { /*TODO*/ }) {
//                                        Row() {
//                                            Icon(painter = painterResource(id = R.drawable.like), contentDescription = "like", tint = kWhite)
//                                            Spacer(Modifier.width(10.dp.scaleSize()))
//                                            Spacer(Modifier.background(kLightTextColor).height(15.dp).width(1.dp))
//                                            Spacer(Modifier.width(10.dp.scaleSize()))
//                                            Text("1", style = TextStyle.Default.kWhiteW400FS13())
//                                        }
//                                    }
//                                }
//                                ShimmerEffectBox(
//                                    isShow = isLoading,
//                                    modifier = isCheckForRowBtn
//                                ) {
//                                    IconButton(
//                                        colors = IconButtonDefaults.iconButtonColors(containerColor = kCardBackgroundColor),
//                                        onClick = { /*TODO*/ }) {
//                                        Icon(
//                                            painter = painterResource(id = R.drawable.dislike),
//                                            contentDescription = "dislike",
//                                            tint = kWhite
//                                        )
//                                    }
//                                }
//                                ShimmerEffectBox(
//                                    isShow = isLoading,
//                                    modifier = isCheckForRowBtn
//                                ) {
//                                    IconButton(
//                                        colors = IconButtonDefaults.iconButtonColors(containerColor = kCardBackgroundColor),
//                                        onClick = { /*TODO*/ }) {
//                                        Icon(
//                                            painter = painterResource(id = R.drawable.share),
//                                            contentDescription = "share",
//                                            tint = kWhite
//                                        )
//                                    }
//                                }
//                                ShimmerEffectBox(
//                                    isShow = isLoading,
//                                    modifier = isCheckForRowBtn
//                                ) {
//                                    IconButton(
//                                        colors = IconButtonDefaults.iconButtonColors(containerColor = kCardBackgroundColor),
//                                        onClick = { /*TODO*/ }) {
//                                        Icon(
//                                            painter = painterResource(id = R.drawable.copy),
//                                            contentDescription = "copy",
//                                            tint = kWhite
//                                        )
//                                    }
//                                }
//                            }

                        Spacer(modifier = Modifier.height(10.dp.scaleSize()))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            ShimmerEffectBox(
                                isShow = isLoading,
                                modifier = if(isLoading) Modifier
                                    .size(50.dp)
                                    .clip(CircleShape) else Modifier
                            ) {

                                CustomNetworkImageView(
                                    imagePath = data?.channelimgurl?:"",
                                    modifier = Modifier
                                        .size(70.dp.scaleSize())
                                        .clip(CircleShape),
                                    contentScale = ContentScale.FillBounds
                                )
//
                            }
                            Spacer(modifier = Modifier.width(10.dp.scaleSize()))
                            Column(modifier = Modifier.weight(1F)) {
                                ShimmerEffectBox(
                                    isShow = isLoading,
                                    modifier = if(isLoading) Modifier
                                        .height(30.dp)
                                        .fillMaxWidth()
                                         else Modifier
                                ) {
                                    Text(
                                        text = data?.channelname?:"",
                                        style = TextStyle.Default.kWhiteW500FS17()
                                    )
                                }
                                ShimmerEffectBox(
                                    isShow = isLoading,
                                    modifier = if(isLoading) Modifier
                                        .height(20.dp)
                                        .fillMaxWidth()
                                         else Modifier
                                ) {
                                    Text(
                                        text = data?.publishedon?:"",
                                        style = TextStyle.Default.kLightTextColorW600FS20()
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp.scaleSize()))
                            ShimmerEffectBox(
                                isShow = isLoading,
                                modifier = if(isLoading) Modifier
                                    .size(height = 50.dp, width = 100.dp)
                                    .clip(RoundedCornerShape(30)) else Modifier
                            ) {
                                OutlinedButton(
                                    colors = ButtonDefaults.buttonColors(containerColor = kPrimaryColor),
                                    shape = RoundedCornerShape(10.dp.scaleSize()),
                                    border = if (followAndUnFollow.intValue == 0) BorderStroke(
                                        color = kPrimaryColor,
                                        width = 1.dp.scaleSize()
                                    ) else null,
                                    onClick = {

                                        if(!isChannelLoading){

                                            println("followAndUnFollow.value is ${followAndUnFollow.intValue}")

                                            when(followAndUnFollow.intValue){

                                                0-> {

                                                    onChannelEvent(ChannelEvent.FollowChannelEvent(data?.channelid?:0, followingType = FollowingType.FOLLOW))
                                                }

                                                1->{

                                                    onChannelEvent(ChannelEvent.UnFollowChannelEvent(data?.channelid?:0, followingType = FollowingType.UNFOLLOW))

                                                }
                                            }
                                        }


                                    }) {
                                    Text(
                                        text = isChangeText(followAndUnFollow.intValue == 0),
                                        style = TextStyle.Default.kPrimaryColorW400FS15().copy(color = kWhite)
                                    )
                                }
                            }


                        }
                        Spacer(modifier = Modifier.height(20.dp.scaleSize()))
                        ShimmerEffectBox(
                            isShow = isLoading,
                            modifier = if(isLoading) Modifier.size(height = 10.dp, width=50.dp) else Modifier
                        ) {
                            Text("Cast:", style = TextStyle.Default.kWhiteW500FS17())
                        }

                        Spacer(modifier = Modifier.height(10.dp.scaleSize()))
                        ShimmerEffectBox(
                            isShow = isLoading,
                            modifier = if(isLoading) Modifier.size(height = 10.dp, width=50.dp) else Modifier
                        ) {
                            Text(
                                data?.shortdetails ?: "",
                                style = TextStyle.Default.kWhiteW300FS12()
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp.scaleSize()))
                        ShimmerEffectBox(
                            isShow = isLoading,
                            modifier = if(isLoading) Modifier.size(height = 10.dp, width=50.dp) else Modifier
                        ) {
                            Text("Category:", style = TextStyle.Default.kWhiteW500FS17())
                        }
                        Spacer(modifier = Modifier.height(10.dp.scaleSize()))
                        ShimmerEffectBox(
                            isShow = isLoading,
                            modifier = if(isLoading) Modifier.size(height = 10.dp, width=50.dp) else Modifier
                        ) {
                            Text(
                                data?.categoryname ?: "",
                                style = TextStyle.Default.kWhiteW300FS12()
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp.scaleSize()))
                        ShimmerEffectBox(
                            isShow = isLoading,
                            modifier = if(isLoading) Modifier.size(height = 10.dp, width=50.dp) else Modifier
                        ) {
                            Text("About:", style = TextStyle.Default.kWhiteW500FS17())
                        }
                        Spacer(modifier = Modifier.height(10.dp.scaleSize()))
                        ShimmerEffectBox(
                            isShow = isLoading,
                            modifier = if(isLoading) Modifier.size(height = 10.dp, width=50.dp) else Modifier
                        ) {
                            Text(
                                data?.longdetails ?: "",
                                style = TextStyle.Default.kWhiteW300FS12()
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp.scaleSize()))

                        ShimmerEffectBox(
                            isShow = isLoading,
                            modifier = if(isLoading) Modifier.size(height = 10.dp, width=50.dp) else Modifier
                        ) {
                            Text("Tags:", style = TextStyle.Default.kWhiteW500FS17())
                        }
                        Spacer(modifier = Modifier.height(10.dp.scaleSize()))
                        ShimmerEffectBox(
                            isShow = isLoading,
                            modifier = if(isLoading) Modifier.size(height = 10.dp, width=50.dp) else Modifier
                        ) {
                            FlowRow() {
                                repeat(data?.tags?.size?:0){
                                    Box(modifier = Modifier
                                        .padding(5.dp)
                                        .background(kSecondaryColor)
                                        .padding(5.dp)) {
                                        Text(
                                            data?.tags?.get(it) ?: "",
                                            style = TextStyle.Default.kWhiteW500FS17()
                                        )
                                    }
                                }
                            }

//                            Text(
//                                data?.longdetails ?: "",
//                                style = TextStyle.Default.kWhiteW300FS12()
//                            )
                        }

                    }
                }



                items(if(isLoading || isPlayListLoading) 10 else trendingPlayListState?.videoAudioList?.size?:0){
                    ShimmerEffectBox(
                        isShow = isLoading,
                        modifier = if(isLoading) Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(5.dp) else Modifier,
                        content = {
                            if(!isLoading && !isPlayListLoading){
                                VideoComponent(
                                    onWatchClick = {},
                                    trendingItem = trendingPlayListState?.videoAudioList?.get(it),
                                    naviController = navController, onClick = {
                                        clickType.value = ClickVideo.PLAYLIST
                                        onTrendingEvent(TrendingEvent.GetResourceVideoDetail(trendingPlayListState?.videoAudioList?.get(it)?.resourceid))
                                    })
                            }

                        }
                    )
                }

                if(isPagingTrendingLoading){
                    item {
                        ShimmerEffectBox(
                            isShow = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(5.dp)
                        ) {

                        }
                    }
                }



//                items(state.trendingList){
//                    Column(modifier = Modifier
//                        .clickable {
////                                navigationViewModel.onEvent(
////                                    NavigationEvent.GoToNextPage(
////                                        naviHostController = naviController,
////                                        name = AppRoutesName.kTrendingDetailRoute
////                                    )
////                                )
//                        }
//                        .padding(vertical = 10.dp)
//                        .background(kCardBackgroundColor)) {
//                        Box {
//                            Image(
//                                painter = painterResource(id = R.drawable.youtube_thumbnile),
//                                contentDescription = "",
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .height(200.dp.scaleSize()), contentScale = ContentScale.FillBounds)
//
//                            Box(modifier = Modifier
//                                .padding(10.dp.scaleSize())
//                                .background(Color.Red)
//                                .padding(
//                                    horizontal = 10.dp.scaleSize(),
//                                    vertical = 5.dp.scaleSize()
//                                )
//                                .align(Alignment.BottomEnd)) {
//                                Text(text = "4:50:00", style = TextStyle.Default.kWhiteW600FS17())
//                            }
//                        }
//                        Column(modifier = Modifier.padding(horizontal = 10.dp.scaleSize())) {
//                            Spacer(modifier = Modifier.height(10.dp.scaleSize()))
//                            Text(text = it.name?:"", style = TextStyle.Default.kWhiteW600FS17())
//                            Spacer(modifier = Modifier.height(10.dp.scaleSize()))
//                            if(it.listCategory?.isNotEmpty() == true || it.listCategory != null){
//                                Text(text = it.listCategory.joinToString(separator = "\t•\t") { element -> element }
//                                    ?:"", style = TextStyle.Default.kPrimaryColorW500FS15())
//                            }
//                            Spacer(modifier = Modifier.height(10.dp.scaleSize()))
//                            Row {
//                                Row(modifier = Modifier.weight(1F), verticalAlignment = Alignment.CenterVertically) {
//                                    Text(text = "1.8M Views", style = TextStyle.Default.kLightTextColorW500FS15())
//                                    Spacer(modifier = Modifier.width(width = 5.dp.scaleSize()))
//                                    Image(painter = painterResource(id = R.drawable.datetime_calender), contentDescription = "",modifier = Modifier.size(20.dp.scaleSize()))
//                                    Text(text = "11 Months ago", style = TextStyle.Default.kLightTextColorW500FS15())
//                                }
//
//                                Row {
//                                    Image(painter = painterResource(id = R.drawable.start_video), contentDescription = "", modifier = Modifier.size(24.dp.scaleSize()))
//                                    Spacer(modifier = Modifier.width(width = 5.dp.scaleSize()))
//                                    Image(painter = painterResource(id = R.drawable.watchlist), contentDescription = "",modifier = Modifier.size(24.dp.scaleSize()))
//                                    Spacer(modifier = Modifier.width(width = 10.dp.scaleSize()))
//                                }
//                            }
//                            Spacer(modifier = Modifier.height(10.dp.scaleSize()))
//                        }
//                    }
//                }
            }
        }
    }
}



@Preview(backgroundColor = 1L, showBackground = true)
@Composable
private fun TrendingVideoDetailPreview() {
    LtsTheme {
        TrendingVideoDetail(onTrendingEvent = {}, navController = rememberNavController(), onChannelEvent = {})
    }
}

