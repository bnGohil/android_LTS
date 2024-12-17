package com.sqt.lts.ui.channels.presentation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.lts.ui.categories.component.ChannelTabComponent
import com.sqt.lts.ui.channels.event.ChannelEvent
import com.example.lts.utils.scaleSize
import com.sqt.lts.ui.channels.data.request.ChannelRequestModel
import com.sqt.lts.ui.channels.data.response.ChannelData
import com.sqt.lts.ui.channels.state.ChannelFollowingState
import com.sqt.lts.ui.channels.state.ChannelUiState
import com.sqt.lts.ui.channels.state.SelectedChannel
import com.sqt.lts.ui.theme.LtsTheme

@Composable
fun Channels(navController: NavHostController,
             onChannelEvent:(ChannelEvent) -> Unit,
             channelUiState: ChannelUiState? =null,
             channelFollowingState:ChannelFollowingState?=null,
             ) {

    var selectedTab = remember { mutableStateOf<SelectedChannel?>(SelectedChannel.POPULAR_CHANNEL) }
    val isPagingLoading = (channelUiState?.isLoading == true && channelUiState.channelList?.isNotEmpty() == true)
    val lazyGridState = rememberLazyListState()


    LaunchedEffect(lazyGridState) {
        snapshotFlow { lazyGridState.layoutInfo.visibleItemsInfo }
            .collect{
                if((it.lastOrNull()?.index?:0) >= 9 && !isPagingLoading && it.lastOrNull()?.index?.plus(1) == lazyGridState.layoutInfo.totalItemsCount && selectedTab.value != SelectedChannel.POPULAR_CHANNEL){
                    onChannelEvent(ChannelEvent.GetChannelData(
                        channelRequestModel = ChannelRequestModel(
                            isFirst = false,
                            sortDirection = "desc",
                            selected = selectedTab.value,
                            sortColumn = "",
                            categoryIds ="",
                            exceptChannelIds = channelUiState?.selectedList?.map { it }?.joinToString(","),
                            myCreatedChannel = 0,
                            myFollowingChannel = 0
                        )
                    ))
                }
            }
    }



    LaunchedEffect(Unit) {

        onChannelEvent(ChannelEvent.GetChannelData(
            channelRequestModel = ChannelRequestModel(
                isFirst = true,
                sortColumn = "trending",
                sortDirection = "desc",
                categoryIds ="",
                exceptChannelIds = "",
                myCreatedChannel = 0,
                myFollowingChannel = 0
            )
        ))

    }


    Column {
        ChannelTabComponent(
            onChannelEvent = onChannelEvent,
            onChangeTab = {
                selectedTab.value = it
                if(it == SelectedChannel.POPULAR_CHANNEL){
                  onChannelEvent(ChannelEvent.ClearSelectedList)
                }
                onChannelEvent(ChannelEvent.GetChannelData(
                    channelRequestModel = ChannelRequestModel(
                        isFirst = true,
                        sortColumn = if(it == SelectedChannel.POPULAR_CHANNEL) "trending" else "",
                        sortDirection = "desc",
                        categoryIds ="",
                        selected = selectedTab.value,
                        exceptChannelIds = if(it == SelectedChannel.POPULAR_CHANNEL) "" else channelUiState?.selectedList?.map { it }?.joinToString(","),
                        myCreatedChannel = 0,
                        myFollowingChannel = 0
                    )
                ))
            },
            selectedTab = selectedTab.value
        )



        ChannelListPage(
            channelDataState = channelFollowingState,
            modifier = Modifier.padding(horizontal = 10.dp.scaleSize(), vertical = 10.dp),
            navigationHostController = navController,
            channelUiState = channelUiState,
            lazyGridState = lazyGridState,
            onChannelEvent = onChannelEvent
        )
    }
}

@Preview
@Composable
private fun ChannelsPreview() {
    LtsTheme {
        Channels(
            navController = rememberNavController(),
            onChannelEvent = {},
            channelUiState= ChannelUiState(
                channelList = arrayListOf(
                    ChannelData(
                        followers = "1 Followers",
                        channelname = "Bhadresh Gohil",
                    ),
                    ChannelData(
                        followers = "1 Followers",
                        channelname = "Bhadresh Gohil",
                    ),
                    ChannelData(
                        followers = "1 Followers",
                        channelname = "Bhadresh Gohil",
                    ),
                    ChannelData(
                        followers = "1 Followers",
                        channelname = "Bhadresh Gohil",
                    ),ChannelData(
                        followers = "1 Followers",
                        channelname = "Bhadresh Gohil",
                    ),ChannelData(
                        followers = "1 Followers",
                        channelname = "Bhadresh Gohil",
                    ),ChannelData(
                        followers = "1 Followers",
                        channelname = "Bhadresh Gohil",
                    ),ChannelData(
                        followers = "1 Followers",
                        channelname = "Bhadresh Gohil",
                    ),ChannelData(
                        followers = "1 Followers",
                        channelname = "Bhadresh Gohil",
                    ),





                )
            ),
        )
    }
}