package com.sqt.lts.ui.trending.trending_component
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sqt.lts.ui.channels.event.ChannelEvent
import com.sqt.lts.ui.channels.channel_custom_component.CustomChannelViewComponent
import com.example.lts.ui.shimmer.ShimmerEffectBox
import com.example.lts.ui.trending.component.VideoComponent
import com.example.lts.ui.trending.state.TrendingState
import com.example.lts.utils.network.DataState
import com.example.lts.utils.scaleSize
import com.sqt.lts.navigation.route.TrendingDetailRoute
import com.sqt.lts.ui.channels.data.response.ChannelData
import com.sqt.lts.ui.channels.event.FollowingType
import com.sqt.lts.ui.channels.state.ChannelFollowingState
import com.sqt.lts.ui.channels.state.ChannelUiState
import com.sqt.lts.ui.home.enums.HomeDataEnums
import com.sqt.lts.ui.home.event.HomeEvent
import com.sqt.lts.ui.home.homeUiState.HomeResourceAndChannelJoinModel
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.ui.trending.data.request.TrendingRequestModel
import com.sqt.lts.ui.trending.data.response.VideoAudio
import com.sqt.lts.ui.trending.event.TrendingEvent



@Composable
fun TrendingItemComponent(
    onChannelEvent:(ChannelEvent) -> Unit,
    onWatchClick: (VideoAudio?) -> Unit,
    channelDataState:ChannelFollowingState?=null,
    onTrendingEvent:(TrendingEvent) -> Unit,
    homeList: List<HomeResourceAndChannelJoinModel?>?=arrayListOf(),
    trendingState: TrendingState? =null,
    onHomeDataEvent:(HomeEvent) -> Unit,
    channelUiState: ChannelUiState? =null,
    naviController: NavHostController,
) {



    LaunchedEffect(key1 = channelUiState) {

        when(channelUiState?.dataState){
            is DataState.Error -> {
                onHomeDataEvent(HomeEvent.GetChannelList(list = arrayListOf(), channelUiState.channelList?.isEmpty() == true))
            }
            is DataState.Loading -> {

            }
            is DataState.Success -> {
                onHomeDataEvent(HomeEvent.GetChannelList(list = channelUiState.channelList, channelUiState.isFirst))
            }
            null -> {}
        }


    }

    LaunchedEffect(trendingState) {
        when(trendingState?.dataState){
            is DataState.Error -> {
                onHomeDataEvent(HomeEvent.GetVideoList(list = arrayListOf(), first = trendingState.videoAudioList?.isEmpty() == true))
            }
            is DataState.Loading -> {}
            is DataState.Success -> {
                onHomeDataEvent(HomeEvent.GetVideoList(list = trendingState.videoAudioList?: arrayListOf(), first = trendingState.isFirst))
            }
            null -> {}
        }
    }







    val isVideoLoading = trendingState?.isLoading == true && homeList?.isEmpty() == true
    val isChannelLoading = channelUiState?.isLoading == true && homeList?.isEmpty() == true

    val isPagingLoading = (trendingState?.isLoading == true && trendingState.isFirst == false)
    val listForTrendingState = rememberLazyListState()

    LaunchedEffect(channelDataState?.data) {


        println("channelDataState?.data is ${channelDataState?.data}")

        when(channelDataState?.data){



            is DataState.Error -> {}

            is DataState.Loading -> {}

            is DataState.Success -> {

                when(channelDataState.channelFollowingType){

                    FollowingType.FOLLOW -> {
                        onHomeDataEvent(HomeEvent.UpdateHomeFollowUnFollowData(channelId = channelDataState.channelId, followingType = FollowingType.FOLLOW))
                    }

                    FollowingType.UNFOLLOW -> {
                        onHomeDataEvent(HomeEvent.UpdateHomeFollowUnFollowData(channelId = channelDataState.channelId, followingType = FollowingType.UNFOLLOW))
                    }
                    null -> {}
                }
            }
            null -> {}
        }



    }

    LaunchedEffect(listForTrendingState) {



        snapshotFlow { listForTrendingState.layoutInfo.visibleItemsInfo }



            .collect{

                if((it.lastOrNull()?.index?:0) >= 2 && !isPagingLoading && it.lastOrNull()?.index?.plus(1) == listForTrendingState.layoutInfo.totalItemsCount){
                    onTrendingEvent(TrendingEvent.GetTrendingDataForHome(
                        trendingRequestModel = TrendingRequestModel(
                            isFirst = false,
                            currentRecord = listForTrendingState.layoutInfo.totalItemsCount,
                            sortColumn = "trending",
                            sortDirection = "desc",
                            channelId = 0,
                            limit = 3,
                            displayloginuseruploaded = 0,
                            mediaType = "",
                        )
                    ))
                }
            }
    }

    LazyColumn(
        state = listForTrendingState
    ) {

        items(if(isVideoLoading || isChannelLoading) 10  else homeList?.size?:0){

            ShimmerEffectBox(
                    isShow = (isVideoLoading && isChannelLoading),
                    content = {

                        if(!(isVideoLoading || isChannelLoading)){

                            if(homeList?.get(it)?.homeDataEnums == HomeDataEnums.CHANNEL){

                                CustomChannelViewComponent(title = "Other Channel",
                                    channelDataState=channelDataState,
                                    channelUiState = channelUiState,
                                    channelList=homeList[it]?.channelList,
                                    onChannelEvent = onChannelEvent,
                                    onHomeDataEvent = onHomeDataEvent,
                                    trendingState = trendingState)

                            }else{

                                VideoComponent(
                                    onWatchClick = onWatchClick,
                                    naviController = naviController, trendingItem = homeList?.get(it)?.videoItem,
                                    onClick = {
                                    naviController.navigate(TrendingDetailRoute(
                                        id = homeList?.get(it)?.videoItem?.resourceid
                                    ))
                                })




                            }

                        }


                    },
                    modifier = if((isVideoLoading && isChannelLoading)) Modifier.fillMaxWidth().padding(10.dp).height(200.dp.scaleSize()) else Modifier
                )

                if(isPagingLoading && homeList?.size == it.plus(1)){
                ShimmerEffectBox(
                    isShow = true,
                    modifier =  Modifier.fillMaxWidth().padding(10.dp).height(200.dp.scaleSize()),
                    content = {}

                )
            }


                }


    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun TrendingItemComponentPreview() {
    LtsTheme {
        TrendingItemComponent(
            homeList = listOf<HomeResourceAndChannelJoinModel>(


                HomeResourceAndChannelJoinModel(
                    homeDataEnums = HomeDataEnums.RESOURCE,
                    videoItem = VideoAudio(
                        categoryname = "tests,tests,tests",
                        resourcedurationinminute = "10:00:34",
                        timedurationupload = "1 month ago"
                    )
                ),

                HomeResourceAndChannelJoinModel(
                    homeDataEnums = HomeDataEnums.RESOURCE,
                    videoItem = VideoAudio(
                        categoryname = "tests,tests,tests",
                        resourcedurationinminute = "10:00:34",
                        timedurationupload = "1 month ago"
                    )
                ),

                HomeResourceAndChannelJoinModel(
                    homeDataEnums = HomeDataEnums.CHANNEL,
                    channelList = listOf<ChannelData?>(
                        ChannelData(channelname = "My Channel 1", followers = "1 Followers"),
                        ChannelData(channelname = "My Channel 2", followers = "2 Followers"),
                        ChannelData(channelname = "My Channel 3" , followers = "3 Followers"),
                        ChannelData(channelname = "My Channel 4" , followers = "4 Followers"),

                        ),

                    ),

                HomeResourceAndChannelJoinModel(
                    homeDataEnums = HomeDataEnums.RESOURCE,
                    videoItem = VideoAudio(
                        categoryname = "tests,tests,tests",
                        resourcedurationinminute = "10:00:34",
                        timedurationupload = "1 month ago"
                    )

                ),


                ),
            naviController = rememberNavController(),
            onHomeDataEvent = {},
            onChannelEvent = {},
            onTrendingEvent = {},
            onWatchClick = {}
        )
    }
}