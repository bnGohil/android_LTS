package com.example.lts.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.lts.ui.categories.component.HomeCategoriesComponent
import com.sqt.lts.ui.categories.event.CategoriesEvent
import com.sqt.lts.ui.categories.state.CategoriesState
import com.sqt.lts.ui.channels.event.ChannelEvent
import com.example.lts.ui.trending.state.TrendingState
import com.sqt.lts.ui.theme.kBackGround
import com.sqt.lts.ui.trending.trending_component.TrendingItemComponent

import com.example.lts.utils.scaleSize
import com.sqt.lts.ui.channels.data.response.ChannelData
import com.sqt.lts.ui.channels.state.ChannelFollowingState
import com.sqt.lts.ui.channels.state.ChannelUiState
import com.sqt.lts.ui.home.enums.HomeDataEnums
import com.sqt.lts.ui.home.event.HomeEvent
import com.sqt.lts.ui.home.homeUiState.HomeResourceAndChannelJoinModel
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.ui.trending.data.response.VideoAudio
import com.sqt.lts.ui.trending.event.TrendingEvent

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Home(
    onChannelEvent:(ChannelEvent) -> Unit,
    homeList: List<HomeResourceAndChannelJoinModel?>?=arrayListOf(),
    trendingState: TrendingState? =null,
    channelUiState: ChannelUiState? =null,
    categoriesState: CategoriesState? =null,
    onTrendingEvent:(TrendingEvent) -> Unit,
    navController: NavHostController,
    onCategoryEvent:(CategoriesEvent) -> Unit,
    channelDataState:ChannelFollowingState?=null,
    onHomeDataEvent:(HomeEvent) -> Unit,
) {
    Scaffold(containerColor = kBackGround) {
        paddingValues -> Column(modifier = Modifier
        .padding(paddingValues)
        .padding(vertical = 10.dp.scaleSize(), horizontal = 20.dp.scaleSize())
        ) {
        HomeCategoriesComponent(categoriesState = categoriesState,onCategoryEvent=onCategoryEvent)
        Spacer(modifier = Modifier.height(10.dp.scaleSize()))
        TrendingItemComponent(
            channelDataState=channelDataState,
            onChannelEvent = onChannelEvent,
            channelUiState = channelUiState,
            naviController = navController,
            onHomeDataEvent = onHomeDataEvent,
            trendingState = trendingState,
            homeList = homeList,
            onTrendingEvent = onTrendingEvent
            )
    }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun HomePagePreview() {
    LtsTheme {
        Home(
            navController = rememberNavController(),
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
            onHomeDataEvent = {},
            onCategoryEvent = {},
            onChannelEvent = {},
            onTrendingEvent = {}
            )
    }
}