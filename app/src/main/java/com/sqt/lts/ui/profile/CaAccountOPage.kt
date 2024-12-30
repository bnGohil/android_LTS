package com.example.lts.ui.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.lts.custom_component.CustomTopBar
import com.example.lts.ui.categories.data.request.GetCategoryRequestModel
import com.sqt.lts.ui.channels.event.ChannelEvent
import com.example.lts.ui.shimmer.ShimmerEffectBox
import com.example.lts.ui.trending.component.VideoComponent
import com.sqt.lts.ui.theme.kBackGround
import com.example.lts.ui.trending.state.TrendingState
import com.example.lts.utils.extainstion.kWhiteW500FS17
import com.example.lts.utils.scaleSize
import com.sqt.lts.navigation.route.CreateChannelRoute
import com.sqt.lts.navigation.route.TrendingDetailRoute
import com.sqt.lts.ui.categories.component.CategoriesItemComponent
import com.sqt.lts.ui.categories.event.CategoriesEvent
import com.sqt.lts.ui.categories.state.CategoriesState
import com.sqt.lts.ui.channels.channel_custom_component.ChannelElementComponent
import com.sqt.lts.ui.channels.channel_custom_component.IsUpdateStatus
import com.sqt.lts.ui.channels.data.request.ChannelRequestModel
import com.sqt.lts.ui.channels.state.ChannelFollowingState
import com.sqt.lts.ui.channels.state.ChannelUiState
import com.sqt.lts.ui.home.event.HomeEvent
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.ui.trending.data.request.TrendingRequestModel
import com.sqt.lts.ui.trending.data.response.VideoAudio
import com.sqt.lts.ui.trending.event.TrendingEvent
import com.sqt.lts.utils.enums.ChannelUpdateNotUpdateType


@Composable
fun CaAccountPage(
    navHostController: NavHostController,
    onCategoryEvent:(CategoriesEvent) -> Unit,
    onChannelEvent:(ChannelEvent) -> Unit,
    onTrendingEvent:(TrendingEvent) -> Unit,
    onHomeEvent:(HomeEvent) -> Unit,
    categoriesState: CategoriesState? =null,
    channelUiState: ChannelUiState? =null,
    channelDataState:ChannelFollowingState?=null,
    caAccountResourceData: TrendingState? =null,
) {

    val categoryListState = rememberLazyListState()
    val videoListState = rememberLazyListState()
    val channelListState = rememberLazyListState()


    val isVideoLoading = (caAccountResourceData?.isLoading == true && caAccountResourceData.videoAudioList?.isEmpty() == true)
    val isCategoryLoading = (categoriesState?.isLoading == true && categoriesState.categories.isEmpty())
    val isChannelLoading = (channelUiState?.isLoading == true && channelUiState.channelList?.isEmpty() == true)
    val isPagingVideoLoading = (caAccountResourceData?.isLoading == true && caAccountResourceData.videoAudioList?.isNotEmpty() == true)
    val isPagingCategoryLoading = (categoriesState?.isLoading == true && categoriesState.categories.isNotEmpty())
    val isPagingChannelLoading = (channelUiState?.isLoading == true && channelUiState.channelList?.isNotEmpty() == true)

    val width = LocalConfiguration.current.screenWidthDp.dp

    LaunchedEffect(Unit) {
        onTrendingEvent(TrendingEvent.GetCaAccountResource(
            trendingRequestModel = TrendingRequestModel(
                mediaType = "",
                channelId = 0,
                sortColumn = "date",
                sortDirection = "desc",
                limit = 3,
                categoryIds = "",
                isFirst = true,
                displayloginuseruploaded = 1
            )
        ))
    }


    LaunchedEffect(Unit) {
        onCategoryEvent(CategoriesEvent.GetCaAccountAllCategories(
            isFirst = true, getCategoryRequestModel = GetCategoryRequestModel(sortColumn = "", sortDirection = "desc", limit = 10, displayLoginUserCategory = 1)
        )
        )
    }

    LaunchedEffect(Unit) {
        onChannelEvent(ChannelEvent.GetCaAccountChannelData(
            channelRequestModel = ChannelRequestModel(
                isFirst = true,
                limit = 10,
                myFollowingChannel = 0,
                myCreatedChannel = 1,
                sortColumn = "",
                sortDirection = "",
                exceptChannelIds = "",
                )
        ))
    }


    LaunchedEffect(videoListState) {
        snapshotFlow { videoListState.layoutInfo.visibleItemsInfo }
            .collect{
                if((it.lastOrNull()?.index?:0) >= 2 && !isPagingVideoLoading && it.lastOrNull()?.index?.plus(1) == videoListState.layoutInfo.totalItemsCount){
                    onTrendingEvent(TrendingEvent.GetCaAccountResource(
                        trendingRequestModel = TrendingRequestModel(
                            mediaType = "",
                            channelId = 0,
                            sortColumn = "date",
                            sortDirection = "desc",
                            limit = 3,
                            categoryIds = "",
                            isFirst = false,
                            displayloginuseruploaded = 1
                        )
                    ))
                }
            }
    }

    LaunchedEffect(categoryListState) {
        snapshotFlow { categoryListState.layoutInfo.visibleItemsInfo }
            .collect{
                if((it.lastOrNull()?.index?:0) >= 9 && !isPagingCategoryLoading && it.lastOrNull()?.index?.plus(1) == categoryListState.layoutInfo.totalItemsCount){
                    onCategoryEvent(CategoriesEvent.GetCaAccountAllCategories(
                        isFirst = false,
                        getCategoryRequestModel = GetCategoryRequestModel(
                            sortColumn = "",
                            sortDirection = "desc", limit = 10,
                            displayLoginUserCategory = 1)
                    )
                    )
                }
            }

    }

    LaunchedEffect(channelListState) {
        snapshotFlow { channelListState.layoutInfo.visibleItemsInfo }
            .collect{
                if((it.lastOrNull()?.index?:0) >= 9 && !isPagingChannelLoading && it.lastOrNull()?.index?.plus(1) == channelListState.layoutInfo.totalItemsCount){
                    onChannelEvent(ChannelEvent.GetCaAccountChannelData(
                        channelRequestModel = ChannelRequestModel(
                            isFirst = false,
                            limit = 10,
                            myFollowingChannel = 0,
                            myCreatedChannel = 1,
                            sortColumn = "",
                            sortDirection = "",
                            exceptChannelIds = "",
                        )
                    ))
                }
            }

    }












    Scaffold(
        topBar = { CustomTopBar(title = "CA Account",navHostController) },
        containerColor = kBackGround) {
        paddingValues -> Column(modifier = Modifier
        .padding(paddingValues)) {

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp.scaleSize())
                .verticalScroll(rememberScrollState())) {

                Text("My Videos / Audios",style= TextStyle.Default.kWhiteW500FS17(), modifier = Modifier.padding(horizontal = 15.dp))
                LazyRow(modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp), state = videoListState) {

                    items( if(isVideoLoading) 5 else caAccountResourceData?.videoAudioList?.size?:0){

                        ShimmerEffectBox(
                            isShow = isVideoLoading,
                            modifier = if(isVideoLoading) Modifier
                                .padding(horizontal = 10.dp)
                                .size(width = 500.dp, height = 200.dp) else Modifier
                                .width(width)
                                .padding(horizontal = 10.dp),
                            content = {
                                VideoComponent(trendingItem = caAccountResourceData?.videoAudioList?.get(it),
                                    naviController = navHostController,
                                    onWatchClick = {

                                    },
                                    onClick = {
                                    navHostController.navigate(TrendingDetailRoute(
                                        id = caAccountResourceData?.videoAudioList?.get(it)?.resourceid
                                    ))
                                })
                            }
                        )

                    }

                    if(isPagingVideoLoading){
                        item {
                            ShimmerEffectBox(
                                isShow = true,
                                modifier =  Modifier
                                    .padding(horizontal = 10.dp)
                                    .size(width = 500.dp, height = 200.dp)
                            ) {

                            }
                        }
                    }

                }
                Spacer(modifier = Modifier.height(5.dp))
                Text("My Categories",style= TextStyle.Default.kWhiteW500FS17(), modifier = Modifier.padding(horizontal = 15.dp))
                Spacer(modifier = Modifier.height(15.dp))
                LazyRow(modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp), state = categoryListState) {
                    items(if(isCategoryLoading) 10 else (categoriesState?.categories?.size?:0)){
                        ShimmerEffectBox(
                            isShow = isCategoryLoading,
                            modifier = if(isCategoryLoading) Modifier
                                .padding(horizontal = 10.dp)
                                .width(width / 2)
                                .height(200.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .padding(5.dp) else Modifier.width(width/2)
                        ) {
                            CategoriesItemComponent(categoriesState?.categories?.get(it))
                        }
                    }

                    if(isPagingCategoryLoading){
                        item{
                            ShimmerEffectBox(
                                isShow = true,
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                                    .width(width / 2)
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .padding(5.dp)
                            ) {}
                        }
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text("My Channel",style= TextStyle.Default.kWhiteW500FS17(), modifier = Modifier.padding(horizontal = 15.dp))
                Spacer(modifier = Modifier.height(5.dp))
                LazyRow(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp), state = channelListState) {
                    items(if(isChannelLoading) 10 else (channelUiState?.channelList?.size?:0)){
                        ShimmerEffectBox(
                            isShow = isChannelLoading,
                            modifier = if(isChannelLoading) Modifier
                                .padding(horizontal = 10.dp)
                                .width(width / 2)
                                .height(200.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .padding(5.dp) else Modifier
                        ) {

                            Box(modifier = Modifier.padding(vertical = 10.dp)) {

                                ChannelElementComponent(
                                    channelUiState?.channelList?.get(it),
                                    onChannelEvent = onChannelEvent,
                                    onHomeDataEvent = onHomeEvent,
                                    channelDataState=channelDataState,
                                    isUpdateStatus = IsUpdateStatus.UPDATE,
                                    onUpdateClick = {
                                        navHostController.navigate(CreateChannelRoute(
                                            channelUpdateNotUpdateType = ChannelUpdateNotUpdateType.CHANNEL_UPDATE,
                                            channelId = channelUiState?.channelList?.get(it)?.channelid
                                            ))
                                    }
                                )
                            }
                        }
                    }

                    if(isPagingChannelLoading){
                        item{
                            ShimmerEffectBox(
                                isShow = true,
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                                    .width(width / 2)
                                    .height(300.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .padding(5.dp)
                            ) {}
                        }
                    }
                }

            }



//            LazyColumn {
//
////            item {
////                Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
////                    Text(text = "My Videos / Audios", style = TextStyle.Default.kWhiteW500FS17())
////                    Text(text = "See all", style = TextStyle.Default.kWhiteW500FS17())
////                }
////            }
//            /*items(if(state.trendingList.size.compareTo(2) > 2)  state.trendingList.size else 2){
//                VideoComponent(naviController = navHostController, onClick = {
//                    navHostController.navigate(TrendingDetailRoute(
////                        id = state.trendingList.get(it).resourceid
//                    ))
//                })
//            }*/
//
////            item {
////                Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
////                    Text(text = "My Categories", style = TextStyle.Default.kWhiteW500FS17())
////                    Text(text = "See all", style = TextStyle.Default.kWhiteW500FS17())
////                }
////                }
//
////                item {
////                    LazyRow {
////                        items(categoriesViewModel.categoryState.value.categories){
////                            CategoriesItemComponent(it)
////                        }
////                    }
////                }
//
//                item {
//                    CustomChannelViewComponent(title = "My Channels", onChannelEvent = {}, onHomeDataEvent = {})
//                }
//
//
//
////                item{SQT@2024
////                    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
////                        items(categoriesViewModel.categoryState.value.categories.size){
////                            CategoriesItemComponent(category = categoriesViewModel.categoryState.value.categories[it])
////                        }
////                    }
////                }
//
//
//            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(backgroundColor = 1L, showBackground = true)
@Composable
private fun CaAccountOPagePreview() {
    LtsTheme {
        CaAccountPage(
            navHostController = rememberNavController(),
            onChannelEvent = {},
            onTrendingEvent = {},
            onCategoryEvent = {},
            onHomeEvent = {},
            caAccountResourceData = TrendingState(
                videoAudioList = arrayListOf(
                    VideoAudio(),
                    VideoAudio(),
                    VideoAudio(),
                    VideoAudio(),
                    VideoAudio()
                )
            )
        )
    }

}