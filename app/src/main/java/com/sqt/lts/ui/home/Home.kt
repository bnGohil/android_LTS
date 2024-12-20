package com.example.lts.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.lts.enums.PagingLoadingType
import com.example.lts.ui.categories.component.HomeCategoriesComponent
import com.example.lts.ui.categories.data.request.GetCategoryRequestModel
import com.example.lts.ui.categories.data.response.Category
import com.example.lts.ui.categories.data.response.SelectedType
import com.example.lts.ui.categories.data.ui_state.CategoryUiState
import com.example.lts.ui.sharedPreferences.data.SaveLoginState
import com.sqt.lts.ui.categories.event.CategoriesEvent
import com.sqt.lts.ui.categories.state.CategoriesState
import com.sqt.lts.ui.channels.event.ChannelEvent
import com.example.lts.ui.trending.state.TrendingState
import com.sqt.lts.ui.theme.kBackGround
import com.sqt.lts.ui.trending.trending_component.TrendingItemComponent

import com.example.lts.utils.scaleSize
import com.sqt.lts.ui.channels.data.request.ChannelRequestModel
import com.sqt.lts.ui.channels.data.response.ChannelData
import com.sqt.lts.ui.channels.state.ChannelFollowingState
import com.sqt.lts.ui.channels.state.ChannelUiState
import com.sqt.lts.ui.home.enums.HomeDataEnums
import com.sqt.lts.ui.home.event.HomeEvent
import com.sqt.lts.ui.home.homeUiState.HomeResourceAndChannelJoinModel
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.ui.trending.data.request.TrendingRequestModel
import com.sqt.lts.ui.trending.data.response.VideoAudio
import com.sqt.lts.ui.trending.event.TrendingEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Home(
    category: Category?=null,
    onChannelEvent:(ChannelEvent) -> Unit,
    homeList: List<HomeResourceAndChannelJoinModel?>?=arrayListOf(),
    trendingState: TrendingState? =null,
    saveLoginState: SaveLoginState? =null,
    channelUiState: ChannelUiState? =null,
    categoriesState: CategoriesState? =null,
    onTrendingEvent:(TrendingEvent) -> Unit,
    navController: NavHostController,
    onCategoryEvent:(CategoriesEvent) -> Unit,
    channelDataState:ChannelFollowingState?=null,
    onHomeDataEvent:(HomeEvent) -> Unit,
) {

    val listState = rememberLazyListState()
    val isPagingLoading = (categoriesState?.isLoading == true && categoriesState.categories.isNotEmpty())
    val listForTrendingState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect{
                if((it.lastOrNull()?.index?:0) >= 9 && !isPagingLoading && it.lastOrNull()?.index?.plus(1) == listState.layoutInfo.totalItemsCount){

                    onCategoryEvent(
                        CategoriesEvent.GetCategoryData(
                            categoryUiState = CategoryUiState(
                                pagingLoadingType = PagingLoadingType.IS_LOADING,
                                getCategoryRequestModel = GetCategoryRequestModel(
                                    sortColumn = "",
                                    displayLoginUserCategory = if(saveLoginState?.isLogin == false) 1 else 0,
                                    page = 1,
                                    limit = 100,
                                    sortDirection = "desc"
                                )
                            )
                        )
                    )
                }
            }
    }

    LaunchedEffect(category) {

        when(category?.type){

            SelectedType.ALL -> {



                onHomeDataEvent(HomeEvent.ClearData)

                onTrendingEvent(TrendingEvent.GetTrendingDataForHome(
                    trendingRequestModel = TrendingRequestModel(
                        isFirst = true,
                        page = 1,
                        displayloginuseruploaded = if(saveLoginState?.isLogin == true) 1 else 0,
                        limit = 3,
                    )
                ))

                onChannelEvent(ChannelEvent.GetHomeChannelData(
                    channelRequestModel = ChannelRequestModel(
                        isFirst = true,
                        limit = 10,
                        page = 1,
                        sortColumn = "trending",
                        sortDirection = "desc",
                        categoryIds ="",
                        exceptChannelIds = "",
                        myCreatedChannel = 0,
                        myFollowingChannel = 0
                    )
                ))

            }

            SelectedType.ANY -> {

                println("API CALL TIME ${System.currentTimeMillis()}")

                val categoryIds = categoriesState?.categories?.filter { it.selectedCategory == true }?.map { it.categoryid }?.joinToString(",")

                onHomeDataEvent(HomeEvent.ClearData)

                onTrendingEvent(TrendingEvent.GetTrendingDataForHome(
                    trendingRequestModel = TrendingRequestModel(
                        isFirst = true,
                        page = 1,
                        displayloginuseruploaded = if(saveLoginState?.isLogin == true) 1 else 0,
                        limit = 3,
                        categoryIds = categoryIds,
                    )
                ))

                onChannelEvent(ChannelEvent.GetHomeChannelData(
                    channelRequestModel = ChannelRequestModel(
                        isFirst = true,
                        limit = 10,
                        page = 1,
                        sortColumn = "trending",
                        sortDirection = "desc",
                        categoryIds = categoryIds,
                        exceptChannelIds = "",
                        myCreatedChannel = 0,
                        myFollowingChannel = 0
                    )
                ))
            }
            null -> {

            }
        }
    }

    Scaffold(containerColor = kBackGround) {
        paddingValues -> Column(
        modifier = Modifier
        .padding(paddingValues)
        .padding(vertical = 5.dp.scaleSize(), horizontal = 20.dp.scaleSize())) {
        HomeCategoriesComponent(
            listState = listState,
            categoriesState = categoriesState,
            onCategoryEvent=onCategoryEvent,
            onCategoriesClick = { category ->
                if(category?.type == SelectedType.ALL){
                    onCategoryEvent(CategoriesEvent.SelectAllCategories)
                }else{
                    onCategoryEvent(CategoriesEvent.CategorySelected(category))
                }
            }
            )
        Spacer(modifier = Modifier.height(5.dp.scaleSize()))
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