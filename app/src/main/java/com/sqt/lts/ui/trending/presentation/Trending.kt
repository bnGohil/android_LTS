package com.sqt.lts.ui.trending.presentation
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.lts.ui.categories.component.HomeCategoriesComponent
import com.example.lts.ui.categories.data.request.GetCategoryRequestModel
import com.example.lts.ui.shimmer.ShimmerEffectBox
import com.example.lts.ui.trending.component.VideoComponent
import com.example.lts.ui.trending.state.TrendingState
import com.sqt.lts.ui.theme.kBackGround
import com.example.lts.utils.scaleSize
import com.sqt.lts.navigation.route.TrendingDetailRoute
import com.sqt.lts.ui.categories.event.CategoriesEvent
import com.sqt.lts.ui.categories.state.CategoriesState
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.ui.trending.data.request.TrendingRequestModel
import com.sqt.lts.ui.trending.event.TrendingEvent

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Trending(
    navController: NavHostController,
    onCategoryEvent:(CategoriesEvent) -> Unit,
    onTrendingEvent:(TrendingEvent) -> Unit,
    trendingState: TrendingState? =null,
    categoryForTrendingState: CategoriesState? =null,
) {

    val listState = rememberLazyListState()
    val listForTrendingState = rememberLazyListState()
    val isPagingLoading = (categoryForTrendingState?.isLoading == true && categoryForTrendingState.categories.isNotEmpty() == true)
    val isPagingTrendingLoading = (trendingState?.isLoading == true && trendingState.videoAudioList?.isNotEmpty() == true)

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect{
                if((it.lastOrNull()?.index?:0) >= 9 && !isPagingLoading && it.lastOrNull()?.index?.plus(1) == listState.layoutInfo.totalItemsCount){
                    onCategoryEvent(CategoriesEvent.GetAllCategoryDataForTrending(
                        isFirst = false,
                        getCategoryRequestModel = GetCategoryRequestModel(
                            sortColumn = "",
                            sortDirection = "desc",
                            limit = 10,
                            displayLoginUserCategory = 0,
                        )
                    ))
                }
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


    val isLoading = trendingState?.isLoading == true && trendingState.videoAudioList?.isEmpty() == true



    Scaffold(containerColor = kBackGround) {
            paddingValues -> Column(modifier = Modifier
        .padding(paddingValues)
        .padding(vertical = 10.dp.scaleSize(), horizontal = 20.dp.scaleSize())
    ) {
        HomeCategoriesComponent(
            listState = listState,
            onCategoryEvent = {},
            categoriesState = categoryForTrendingState,
            onCategoriesClick = {}
        )
        Spacer(modifier = Modifier.height(20.dp.scaleSize()))
        LazyColumn(state = listForTrendingState) {

            items(if(isLoading) 10 else trendingState?.videoAudioList?.size?:0){
                ShimmerEffectBox(
                    isShow = isLoading,
                    modifier = if(isLoading) Modifier.fillMaxWidth().height(200.dp).padding(5.dp) else Modifier,
                    content = {
                        VideoComponent(trendingItem = trendingState?.videoAudioList?.get(it),
                            onWatchClick = {},
                            naviController = navController, onClick = {
                            navController.navigate(TrendingDetailRoute(
                               id = trendingState?.videoAudioList?.get(it)?.resourceid
                            ))
                        })
                    }
                )
            }

            if(isPagingTrendingLoading){
                item {
                    ShimmerEffectBox(
                        isShow = true,
                        modifier = Modifier.fillMaxWidth().height(200.dp).padding(5.dp)
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
            onTrendingEvent = {}
        )
    }
}