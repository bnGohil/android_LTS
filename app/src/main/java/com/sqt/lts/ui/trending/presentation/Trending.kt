package com.sqt.lts.ui.trending.presentation
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.lts.base.BaseCommonResponseModel
import com.example.lts.ui.categories.component.HomeCategoriesComponent
import com.example.lts.ui.categories.data.request.GetCategoryRequestModel
import com.example.lts.ui.categories.data.response.Category
import com.example.lts.ui.categories.data.response.SelectedType
import com.example.lts.ui.shimmer.ShimmerEffectBox
import com.example.lts.ui.trending.component.VideoComponent
import com.example.lts.ui.trending.state.TrendingState
import com.example.lts.utils.extainstion.kWhiteW500FS17
import com.example.lts.utils.network.DataState
import com.sqt.lts.ui.theme.kBackGround
import com.example.lts.utils.scaleSize
import com.sqt.lts.navigation.route.TrendingDetailRoute
import com.sqt.lts.ui.categories.event.CategoriesEvent
import com.sqt.lts.ui.categories.state.CategoriesState
import com.sqt.lts.ui.history.event.HistoryAndWatchListEvent
import com.sqt.lts.ui.home.event.HomeEvent
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.ui.trending.data.request.TrendingRequestModel
import com.sqt.lts.ui.trending.event.TrendingEvent

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Trending(
    navController: NavHostController,
    onCategoryEvent: (CategoriesEvent) -> Unit,
    onTrendingEvent: (TrendingEvent) -> Unit,
    onHistoryAndWatchListEvent:(HistoryAndWatchListEvent) -> Unit,
    trendingState: TrendingState? = null,
    addAndRemoveWatchListAppResponse: DataState<BaseCommonResponseModel.Data?>? = null,
    selectedCategoriesForTrending: Category? = null,
    categoryForTrendingState: CategoriesState? = null,

) {
    val listForTrendingState = rememberLazyListState()
    val isPagingTrendingLoading = (trendingState?.isLoading == true && trendingState.videoAudioList?.isNotEmpty() == true)
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



    LaunchedEffect(listForTrendingState) {
        snapshotFlow { listForTrendingState.layoutInfo.visibleItemsInfo }
            .collect{
                if((it.lastOrNull()?.index?:0) >= 2 && !isPagingTrendingLoading && it.lastOrNull()?.index?.plus(1) == listForTrendingState.layoutInfo.totalItemsCount){
                    onTrendingEvent(TrendingEvent.GetTrendingData(
                        trendingRequestModel = TrendingRequestModel(
                            isFirst = false,
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

    LaunchedEffect(Unit) {

        onTrendingEvent(TrendingEvent.GetTrendingData(
            trendingRequestModel = TrendingRequestModel(
                isFirst = true,
                sortColumn = "trending",
                sortDirection = "desc",
                channelId = 0,
                limit = 3,
                displayloginuseruploaded = 0,
                mediaType = "",
            )
        ))
    }

    LaunchedEffect(Unit) {
        onCategoryEvent(CategoriesEvent.GetAllCategoryDataForTrending(
            isFirst = true,
            getCategoryRequestModel = GetCategoryRequestModel(
                sortColumn = "",
                sortDirection = "desc",
                limit = 10,
                displayLoginUserCategory = 0,
            )
        ))
    }

    LaunchedEffect(selectedCategoriesForTrending) {

        when(selectedCategoriesForTrending?.type){

            SelectedType.ALL -> {


                onTrendingEvent(TrendingEvent.GetTrendingData(
                    trendingRequestModel = TrendingRequestModel(
                        isFirst = true,
                        page = 1,
                        sortColumn = "trending",
                        sortDirection = "desc",
                        mediaType = "",
                        channelId = 0,
                        displayloginuseruploaded = 0,
                        limit = 3,
                    )
                ))



            }

            SelectedType.ANY -> {



                val categoryIds = categoryForTrendingState?.categories?.filter { it.selectedCategory == true }?.map { it.categoryid }?.joinToString(",")



                println("categoryIds:$categoryIds")

                onTrendingEvent(TrendingEvent.GetTrendingData(
                    trendingRequestModel = TrendingRequestModel(
                        isFirst = true,
                        page = 1,
                        sortColumn = "trending",
                        sortDirection = "desc",
                        mediaType = "",
                        channelId = 0,
                        categoryIds=categoryIds,

                        displayloginuseruploaded =  0,
                        limit = 3,
                    )
                ))


            }

            null -> {

            }
        }

    }


    val isLoading = trendingState?.isLoading == true && trendingState.videoAudioList?.isEmpty() == true



    Scaffold(containerColor = kBackGround) {
            paddingValues -> Column(modifier = Modifier
        .padding(paddingValues)
        .padding(vertical = 10.dp.scaleSize(), horizontal = 20.dp.scaleSize())
    ) {
        HomeCategoriesComponent(
            onCategoryEvent = onCategoryEvent,
            categoriesState = categoryForTrendingState,
            onCategoriesClick = { category -> if(category?.type == SelectedType.ALL){
                    onCategoryEvent(CategoriesEvent.SelectAllForTrendingCategories)
                }else{
                    onCategoryEvent(CategoriesEvent.CategorySelectedForTrending(category))
                }
            },
            onPaginationClickEvent = {
                onCategoryEvent(CategoriesEvent.GetAllCategoryDataForTrending(isFirst = false, getCategoryRequestModel = GetCategoryRequestModel(sortColumn = "", sortDirection = "desc", limit = 10, displayLoginUserCategory = 0,)))
            },

        )
        Spacer(modifier = Modifier.height(20.dp.scaleSize()))

        if(!isLoading && trendingState?.videoAudioList?.isEmpty() == true){
            Box(modifier = Modifier
                .weight(1F)
                .fillMaxWidth()) {
                Text("Data Not Found", modifier = Modifier.align(Alignment.Center), textAlign = TextAlign.Center, style = TextStyle.Default.kWhiteW500FS17())
            }
        }

        LazyColumn(state = listForTrendingState) {

            items(if(isLoading) 10 else trendingState?.videoAudioList?.size?:0){
                ShimmerEffectBox(
                    isShow = isLoading,
                    modifier = if(isLoading) Modifier
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
                            naviController = navController,
                            onClick = {
                            navController.navigate(TrendingDetailRoute(id = trendingState?.videoAudioList?.get(it)?.resourceid))
                           }
                        )
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

        }
    }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(backgroundColor = 1L, showBackground = true)
@Composable
private fun TrendingPreview() {
    LtsTheme {
        Trending(
            navController = rememberNavController(),
            onCategoryEvent = {},
            onTrendingEvent = {},
            onHistoryAndWatchListEvent = {}
        )
    }
}