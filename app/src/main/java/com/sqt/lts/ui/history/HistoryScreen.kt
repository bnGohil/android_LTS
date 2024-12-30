package com.example.lts.ui.history

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.lts.custom_component.CustomTopBar
import com.example.lts.navigation.navigation_view_model.NavigationViewModel
import com.example.lts.navigation.repository.NavigationRepository
import com.example.lts.ui.shimmer.ShimmerEffectBox
import com.sqt.lts.navigation.route.HistoryAndWatchlistRoute
import com.example.lts.ui.tab.data.NavigationDrawer
import com.example.lts.ui.trending.component.VideoComponent
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.ui.theme.kBackGround
import com.sqt.lts.ui.theme.kCardBackgroundColor
import com.sqt.lts.ui.theme.kWhite
import com.sqt.lts.ui.trending.trending_component.TrendingItemComponent
import com.example.lts.utils.extainstion.kBlackW400FS15
import com.example.lts.utils.extainstion.kWhiteW400FS13
import com.example.lts.utils.scaleSize
import com.sqt.lts.navigation.route.TrendingDetailRoute
import com.sqt.lts.ui.history.event.HistoryAndWatchListEvent
import com.sqt.lts.ui.history.request.HistoryAndWatchListRequestModel
import com.sqt.lts.ui.history.state.HistoryAndWatchListUiState
import com.sqt.lts.ui.trending.data.request.TrendingRequestModel
import com.sqt.lts.ui.trending.event.TrendingEvent
import com.sqt.lts.ui.trending.trending_view_model.TrendingViewModel


@Composable
fun HistoryScreen(
    navHostController: NavHostController,
    onHistoryAndWatchListEvent: (HistoryAndWatchListEvent) -> Unit,
    historyAndWatchListUiState:HistoryAndWatchListUiState?=null,
    data: HistoryAndWatchlistRoute? = null
) {
    var selectedItem by remember { mutableIntStateOf(30) }


    val listForTrendingState = rememberLazyListState()

    val isLoading = historyAndWatchListUiState?.isLoading == true && historyAndWatchListUiState.videoAudioList.isEmpty()
    val isPagingTrendingLoading = historyAndWatchListUiState?.isLoading == true && historyAndWatchListUiState.videoAudioList.isNotEmpty()



    val list = arrayListOf(30,60,365)
    var isDropDown by remember { mutableStateOf<Boolean>(false) }
//    val state by trendingViewModel.trendingAllState.collectAsState()


    LaunchedEffect(listForTrendingState) {




        snapshotFlow { listForTrendingState.layoutInfo.visibleItemsInfo }
            .collect{

                if((it.lastOrNull()?.index?:0) >= 3 && !isPagingTrendingLoading && it.lastOrNull()?.index?.plus(1) == listForTrendingState.layoutInfo.totalItemsCount){

                    println("data?.navigationDrawer : ${data?.navigationDrawer}")

                    if(data?.navigationDrawer == NavigationDrawer.HISTORY){
                        onHistoryAndWatchListEvent(HistoryAndWatchListEvent.HistoryEvent(
                            historyAndWatchListRequestModel = HistoryAndWatchListRequestModel(
                                limit = 3,
                                sortcolumn = "date",
                                days = selectedItem,
                                isFirst = false,
                                sortdirection = "desc"
                            )
                        ))
                    }else if(data?.navigationDrawer == NavigationDrawer.WATCHLIST){
                        onHistoryAndWatchListEvent(HistoryAndWatchListEvent.WatchEvent(
                            historyAndWatchListRequestModel = HistoryAndWatchListRequestModel(
                                limit = 3,
                                sortcolumn = "date",
                                isFirst = false,
                                sortdirection = "desc"
                            )
                        ))
                    }
                }
            }
    }

    Scaffold(containerColor = kBackGround, topBar = {
        CustomTopBar(navHostController = navHostController, title = data?.title?:"",
            content = {
                if(data?.navigationDrawer == NavigationDrawer.HISTORY){
                    Box(modifier = Modifier
                        .padding(5.dp.scaleSize())
                        .background(kCardBackgroundColor)
                        .padding(7.dp.scaleSize())
                    ){
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                            isDropDown = true
                        }) {
                            Text(
                                text = "$selectedItem Days",
                                style = TextStyle.Default.kWhiteW400FS13()
                            )
                            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "", tint = kWhite)
                        }
                        DropdownMenu(
                            expanded = isDropDown,
                            onDismissRequest = { isDropDown = false }
                        ) {
                            list.map {
                                DropdownMenuItem(text = { Text(text =  "$it Days", style = TextStyle.Default.kBlackW400FS15())}, onClick = {
                                    selectedItem = it
                                    isDropDown = false
                                    onHistoryAndWatchListEvent(HistoryAndWatchListEvent.HistoryEvent(
                                        historyAndWatchListRequestModel = HistoryAndWatchListRequestModel(
                                            limit = 3,
                                            sortcolumn = "date",
                                            days = it,
                                            isFirst = true,
                                            sortdirection = "desc"
                                        )
                                    ))
                                })
                            }
                        }
                    }
                } else {
                    Box(modifier = Modifier.size(10.dp.scaleSize()))
                }
            })
    }) {
        paddingValues -> LazyColumn(
        modifier = Modifier.padding(paddingValues).padding(horizontal = 15.dp),
        state = listForTrendingState) {

        items(if(isLoading) 10 else historyAndWatchListUiState?.videoAudioList?.size?:0){
            ShimmerEffectBox(
                isShow = isLoading,
                modifier = if(isLoading) Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(5.dp) else Modifier,
                content = {
                    VideoComponent(trendingItem = historyAndWatchListUiState?.videoAudioList?.get(it), naviController = navHostController,
                        onWatchClick = {

                            if(it?.resourceid == null) return@VideoComponent

                            when(it.isaddedinwatchlist){

                                0->{
                                    onHistoryAndWatchListEvent(HistoryAndWatchListEvent.AddWatchList(it.resourceid, type = data?.navigationDrawer))
                                }

                                1->{
                                    onHistoryAndWatchListEvent(HistoryAndWatchListEvent.RemoveWatchList(it.resourceid, type = data?.navigationDrawer))
                                }

                            }
                        },
                        onClick = {
                        navHostController.navigate(TrendingDetailRoute(
                            id = historyAndWatchListUiState?.videoAudioList?.get(it)?.resourceid
                        ))
                    })
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun HistoryScreenPreview() {
    LtsTheme {
        HistoryScreen(
            navHostController = rememberNavController(),
            onHistoryAndWatchListEvent = {},
            data = HistoryAndWatchlistRoute(
                navigationDrawer = NavigationDrawer.HISTORY,
                title = "history"
            )
        )
    }
}