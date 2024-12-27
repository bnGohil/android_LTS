package com.sqt.lts.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.lts.base.BaseCommonResponseModel
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
import com.example.lts.utils.extainstion.kSecondaryTextColorW500FS15
import com.example.lts.utils.extainstion.kWhiteW500FS17
import com.example.lts.utils.network.DataState
import com.sqt.lts.ui.theme.kBackGround
import com.sqt.lts.ui.trending.trending_component.TrendingItemComponent

import com.example.lts.utils.scaleSize
import com.sqt.lts.ui.channels.data.request.ChannelRequestModel
import com.sqt.lts.ui.channels.data.response.ChannelData
import com.sqt.lts.ui.channels.state.ChannelFollowingState
import com.sqt.lts.ui.channels.state.ChannelUiState
import com.sqt.lts.ui.history.event.HistoryAndWatchListEvent
import com.sqt.lts.ui.home.enums.HomeDataEnums
import com.sqt.lts.ui.home.event.HomeEvent
import com.sqt.lts.ui.home.homeUiState.HomeResourceAndChannelJoinModel
import com.sqt.lts.ui.tab.state.SelectedTabAndSearch
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.ui.theme.kWhite
import com.sqt.lts.ui.trending.data.request.TrendingRequestModel
import com.sqt.lts.ui.trending.data.response.VideoAudio
import com.sqt.lts.ui.trending.event.TrendingEvent

@Composable
fun Home(
    selectedCategory: Category?=null,
    addAndRemoveWatchListAppResponse: DataState<BaseCommonResponseModel.Data?>? =null,
    onChannelEvent:(ChannelEvent) -> Unit,
    onHistoryAndWatchListEvent:(HistoryAndWatchListEvent) -> Unit,
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
    selectedTabAndSearch: SelectedTabAndSearch? = null,
) {




    val resourceId = remember { mutableIntStateOf(0) }





   val isSearch =  selectedTabAndSearch?.isSearch == true



    LaunchedEffect(selectedCategory) {

        when(selectedCategory?.type){

            SelectedType.ALL -> {

                onHomeDataEvent(HomeEvent.ClearData)

                onTrendingEvent(TrendingEvent.GetTrendingDataForHome(
                    trendingRequestModel = TrendingRequestModel(
                        isFirst = true,
                        page = 1,
                        sortColumn = "trending",
                        sortDirection = "desc",
                        mediaType = "",
                        channelId = 0,
                        exceptResourceIds = "",
                        displayloginuseruploaded = 0,
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



                val categoryIds = categoriesState?.categories?.filter { it.selectedCategory == true }?.map { it.categoryid }?.joinToString(",")


                onHomeDataEvent(HomeEvent.ClearData)



                onTrendingEvent(TrendingEvent.GetTrendingDataForHome(
                    trendingRequestModel = TrendingRequestModel(
                        isFirst = true,
                        page = 1,
                        sortColumn = "trending",
                        sortDirection = "desc",
                        mediaType = "",
                        channelId = 0,
                        categoryIds=categoryIds,
                        exceptResourceIds = "",
                        displayloginuseruploaded =  0,
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

    LaunchedEffect(addAndRemoveWatchListAppResponse) {

        when(addAndRemoveWatchListAppResponse){

            is DataState.Error -> {}

            DataState.Loading -> {}

            is DataState.Success -> {
                onHomeDataEvent(HomeEvent.UpdateResourceData(resourceId = resourceId.intValue))
            }

            null -> {}
        }
    }

    val searchText = remember { mutableStateOf<String>("") }




    Scaffold(containerColor = kBackGround) {
        paddingValues -> Column(
        modifier = Modifier
        .padding(paddingValues)
        .padding(vertical = 5.dp.scaleSize(), horizontal = 20.dp.scaleSize())) {

        if(isSearch){
            Box(modifier = Modifier
                .fillMaxWidth()
                .border(
                    shape = CircleShape,
                    border = BorderStroke(
                        color = kWhite,
                        width = 1.dp,
                    )
                )
                .padding(vertical = 10.dp))
            {


                if(searchText.value.isEmpty()){
                    Text("Search", style = TextStyle.Default.kWhiteW500FS17(), modifier = Modifier.padding(horizontal = 14.dp.scaleSize()))
                }

                BasicTextField(
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text
                    ),
                    value = searchText.value,
                    onValueChange = { searchText.value = it },
                    textStyle = TextStyle.Default.kSecondaryTextColorW500FS15(),
                    cursorBrush = SolidColor(kWhite)
                )


            }
        } else{
            HomeCategoriesComponent(
                categoriesState = categoriesState,
                onCategoryEvent=onCategoryEvent,
                onPaginationClickEvent = {
                    onCategoryEvent(
                        CategoriesEvent.GetCategoryData(
                            categoryUiState = CategoryUiState(
                                pagingLoadingType = PagingLoadingType.IS_LOADING,
                                getCategoryRequestModel = GetCategoryRequestModel(
                                    sortColumn = "",
                                    displayLoginUserCategory = if(saveLoginState?.isLogin == false) 1 else 0,
                                    page = 1,
                                    limit = 10, sortDirection = "")
                            )
                        )
                    )
                },
                onCategoriesClick = { category ->
                    if(category?.type == SelectedType.ALL){
                        onCategoryEvent(CategoriesEvent.SelectAllCategories)
                    }else{
                        println("category:$category")
                        onCategoryEvent(CategoriesEvent.CategorySelected(category))
                    }
                }
            )
        }


        Spacer(modifier = Modifier.height(5.dp.scaleSize()))
        TrendingItemComponent(
            channelDataState=channelDataState,
            onChannelEvent = onChannelEvent,
            channelUiState = channelUiState,
            naviController = navController,
            onHomeDataEvent = onHomeDataEvent,
            trendingState = trendingState,
            homeList = homeList,
            onTrendingEvent = onTrendingEvent,
            onWatchClick = {
                if(it?.resourceid == null) return@TrendingItemComponent

                resourceId.intValue = it.resourceid

                when(it.isaddedinwatchlist){

                    0->{
                        onHistoryAndWatchListEvent(HistoryAndWatchListEvent.AddWatchList(it.resourceid, type = null))
                    }

                    1->{
                        onHistoryAndWatchListEvent(HistoryAndWatchListEvent.RemoveWatchList(it.resourceid, type = null))
                    }

                }
            }
            )
    }
    }
}


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
            onTrendingEvent = {},
            onHistoryAndWatchListEvent = {}
            )
    }
}