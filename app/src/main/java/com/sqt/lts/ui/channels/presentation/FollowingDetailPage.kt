package com.example.lts.ui.channels.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.lts.base.BaseCommonResponseModel
import com.sqt.lts.R
import com.example.lts.custom_component.CustomTopBar
import com.example.lts.ui.shimmer.ShimmerEffectBox
import com.example.lts.ui.trending.component.VideoComponent
import com.example.lts.ui.trending.state.TrendingState
import com.sqt.lts.ui.channels.channel_custom_component.isChangeText
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.ui.theme.kBackGround
import com.sqt.lts.ui.theme.kPrimaryColor
import com.sqt.lts.ui.theme.kWhite
import com.example.lts.utils.extainstion.kPrimaryColorW400FS15
import com.example.lts.utils.extainstion.kWhiteW400FS13
import com.example.lts.utils.extainstion.kWhiteW500FS17
import com.example.lts.utils.network.DataState
import com.example.lts.utils.scaleSize
import com.sqt.lts.custom_component.CustomNetworkImageView
import com.sqt.lts.navigation.route.TrendingDetailRoute
import com.sqt.lts.ui.channels.event.ChannelEvent
import com.sqt.lts.ui.channels.event.FollowingType
import com.sqt.lts.ui.channels.state.ChannelDetailUiState
import com.sqt.lts.ui.channels.state.ChannelFollowingState
import com.sqt.lts.ui.history.event.HistoryAndWatchListEvent
import com.sqt.lts.ui.trending.data.request.TrendingRequestModel
import com.sqt.lts.ui.trending.event.TrendingEvent


@Composable
fun FollowingDetailPage(
    naviController: NavHostController,
    channelId: Int?=null,
    onChannelEvent: (ChannelEvent) -> Unit,
    onTrendingEvent: (TrendingEvent) -> Unit,
    channelDetailUiState: ChannelDetailUiState?=null,
    channelFollowingState: ChannelFollowingState?=null,
    trendingState: TrendingState?=null,
    onHistoryAndWatchListEvent:(HistoryAndWatchListEvent) -> Unit,
    addAndRemoveWatchListAppResponse: DataState<BaseCommonResponseModel.Data?>? = null,
    ) {


    val isLoading = channelDetailUiState?.isLoading == true
    val channelData = channelDetailUiState?.channelData
    val isChannelLoading = channelFollowingState?.isLoading == true

    val isTrendingLoading = trendingState?.isLoading == true && trendingState.videoAudioList?.isEmpty() == true

    var resourceId = remember { mutableIntStateOf(0) }


    LaunchedEffect(addAndRemoveWatchListAppResponse) {

        when(addAndRemoveWatchListAppResponse){

            is DataState.Error -> {}

            DataState.Loading -> {}

            is DataState.Success -> {
                onTrendingEvent(TrendingEvent.UpdateResourceForWatchData(resourceId = resourceId.intValue))
            }

            null -> {}
        }
    }




    LaunchedEffect(Unit) {
        onChannelEvent(ChannelEvent.GetChannelDetailEvent(channelId = channelId?:0))
    }


    LaunchedEffect(channelDetailUiState) {

        when(channelDetailUiState?.channelDataState){

            is DataState.Error -> {

            }

            is DataState.Loading -> {

            }

            is DataState.Success -> {
                onTrendingEvent(TrendingEvent.GetChannelTrendingData(
                    TrendingRequestModel(
                        mediaType = "",
                        isFirst = true,
                        categoryIds = "",
                        limit = 100,
                        sortColumn = "date",
                        sortDirection = "desc",
                        exceptResourceIds = "",
                        displayloginuseruploaded = 0,
                        channelId = channelDetailUiState.channelData?.channelid
                    )
                ))
            }

            null -> {}
        }
    }



    Scaffold(containerColor = kBackGround,
        topBar = {
            CustomTopBar(navHostController = naviController, title = "Video / Audio")
        }
        ) {
        paddingValues ->

        if(isLoading)
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        else LazyColumn(modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 15.dp.scaleSize())) {
            item {
                Spacer(modifier = Modifier.height(10.dp.scaleSize()))
                Row {
                    CustomNetworkImageView(
                        modifier = Modifier.size(70.dp.scaleSize()).clip(CircleShape),
                        imagePath = channelData?.channelimgurl,
                        contentScale = ContentScale.FillBounds
                    )
                    Spacer(modifier = Modifier.width(20.dp.scaleSize()))
                    Column {
                        Text(text = channelData?.channelname?:"", style = TextStyle.Default.kWhiteW500FS17())
                        Spacer(modifier = Modifier.height(5.dp.scaleSize()))
                        Text(text = "Published ${channelData?.publishedon}, * ${channelData?.followers} * ${channelData?.totalvideo} Video * ${channelData?.totalaudio} Audio", style = TextStyle.Default.kWhiteW500FS17())
                        Spacer(modifier = Modifier.height(5.dp.scaleSize()))
                        Row {
                            OutlinedButton(
                                colors = ButtonDefaults.buttonColors(containerColor = if (channelData?.isfollowchannel == 0) kPrimaryColor else Color.Transparent),
                                shape = RoundedCornerShape(10.dp.scaleSize()),
                                border = if (channelData?.isfollowchannel != 0) BorderStroke(
                                    color = kPrimaryColor,
                                    width = 1.dp.scaleSize()
                                ) else null,
                                onClick = {

                                    if(!isChannelLoading){


                                        when(channelData?.isfollowchannel){

                                            0-> {

                                                onChannelEvent(ChannelEvent.FollowChannelEvent(
                                                    channelData.channelid ?:0, followingType = FollowingType.FOLLOW))
                                            }

                                            1->{

                                                onChannelEvent(ChannelEvent.UnFollowChannelEvent(
                                                    channelData.channelid ?:0, followingType = FollowingType.UNFOLLOW))

                                            }
                                        }
                                    }


                                }) {
                                Text(
                                    text = isChangeText(channelData?.isfollowchannel == 0),
                                    style = TextStyle.Default.kPrimaryColorW400FS15().copy(color = kWhite)
                                )
                            }
//                            OutlinedButton(
//                                colors = ButtonDefaults.buttonColors(
//                                    containerColor = if (following) kPrimaryColor else Color.Transparent
//                                ),
//                                shape = RoundedCornerShape(10.dp.scaleSize()),
//                                border = if (!following) BorderStroke(
//                                    color = kPrimaryColor,
//                                    width = 1.dp.scaleSize()
//                                ) else null,
//                                onClick = {
//                                    following = !following
//                                }) {
//                                Text(
//                                    text = isChangeText(following),
//                                    style = TextStyle.Default.kPrimaryColorW400FS15().copy(
//                                        color = if (!following) kPrimaryColor else kWhite
//                                    )
//                                )
//                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            OutlinedButton(
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                shape = RoundedCornerShape(10.dp.scaleSize()),
                                border =  BorderStroke(
                                    color = kPrimaryColor,
                                    width = 1.dp.scaleSize()
                                ),
                                onClick = {

                                    //                        following.toggle()
                                }) {
                                Text(
                                    text = "Ask the senior",
                                    style = TextStyle.Default.kPrimaryColorW400FS15().copy(
                                        color =  kPrimaryColor
                                    )
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp.scaleSize()))
                Text(text = "Video / Audio", style = TextStyle.Default.kWhiteW400FS13())
            }
            items(if(isTrendingLoading) 10 else trendingState?.videoAudioList?.size?:0){
                ShimmerEffectBox(
                    isShow = isTrendingLoading,
                    modifier = if(isTrendingLoading) Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(5.dp) else Modifier,
                    content = {
                        VideoComponent(trendingItem = trendingState?.videoAudioList?.get(it),
                            onWatchClick = {
                                if(it?.resourceid == null) return@VideoComponent

                                resourceId.intValue = it.resourceid

                                when(it.isaddedinwatchlist){

                                    0->{
                                        onHistoryAndWatchListEvent(HistoryAndWatchListEvent.AddWatchList(it.resourceid, type = null))
                                    }

                                    1->{
                                        onHistoryAndWatchListEvent(HistoryAndWatchListEvent.RemoveWatchList(it.resourceid, type = null))
                                    }

                                }
                            },
                            naviController = naviController,
                            onClick = {
                                naviController.navigate(TrendingDetailRoute(id = trendingState?.videoAudioList?.get(it)?.resourceid))
                            }
                        )
                    }
                )
            }
//            items(state.trendingList){
//                VideoComponent(navigationViewModel = navigationViewModel, naviController = naviController, trendingItem = it)
//            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun FollowingDetailPagePreview() {
    LtsTheme {
        FollowingDetailPage(
            naviController = rememberNavController(),
            onChannelEvent={},
            onTrendingEvent = {},
            onHistoryAndWatchListEvent = {}
        )
    }
}