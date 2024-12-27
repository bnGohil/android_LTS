package com.sqt.lts.ui.channels.channel_custom_component
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sqt.lts.ui.channels.event.ChannelEvent
import com.example.lts.ui.shimmer.ShimmerEffectBox
import com.example.lts.ui.trending.state.TrendingState
import com.example.lts.utils.extainstion.kWhiteW500FS17
import com.example.lts.utils.scaleSize
import com.sqt.lts.ui.channels.data.request.ChannelRequestModel
import com.sqt.lts.ui.channels.data.response.ChannelData
import com.sqt.lts.ui.channels.state.ChannelFollowingState
import com.sqt.lts.ui.channels.state.ChannelUiState
import com.sqt.lts.ui.home.event.HomeEvent
import com.sqt.lts.ui.theme.LtsTheme

@Composable
fun CustomChannelViewComponent(
    title: String? = null,
    trendingState: TrendingState? =null,
    channelList: List<ChannelData?>?= arrayListOf(),
    channelUiState: ChannelUiState? =null,
    onChannelEvent:(ChannelEvent) -> Unit,
    channelDataState:ChannelFollowingState?=null,
    onHomeDataEvent:(HomeEvent) -> Unit,
) {

    val isPagingLoading = (channelUiState?.isLoading == true && channelUiState.isFirst == false)

    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect{
                if((it.lastOrNull()?.index?:0) >= 9 && !isPagingLoading && it.lastOrNull()?.index?.plus(1) == listState.layoutInfo.totalItemsCount){
                    onChannelEvent(ChannelEvent.GetHomeChannelData(
                        channelRequestModel = ChannelRequestModel(
                            isFirst = false,
                            currentRecord = channelList?.size,
                            sortColumn = "trending",
                            sortDirection = "desc",
                            categoryIds ="",
                            exceptChannelIds = "",
                            myCreatedChannel = 0,
                            myFollowingChannel = 0
                        )
                    ))
                }
            }
    }

    Column {
        Spacer(modifier = Modifier.height(15.dp.scaleSize()))
        LazyRow(state = listState) {
            items(channelList?.size?:0){
//                if(channelList?.size == it.plus(1) && !isPagingLoading){
//
//                }
                ChannelElementComponent(channelList?.get(it),
                    onChannelEvent = onChannelEvent,
                    onHomeDataEvent = onHomeDataEvent,
                    onUpdateClick = {},
                    channelDataState=channelDataState)
                if(isPagingLoading && (it.plus(1) == channelList?.size)){
                    ShimmerEffectBox(
                        isShow = true,
                        modifier = Modifier.size(
                            width = 100.dp.scaleSize(),
                            height = 270.dp.scaleSize()
                        ),
                    ) {
                        ChannelElementComponent(
                            onChannelEvent = {},
                            onHomeDataEvent = {},
                            onUpdateClick = {}
                        )
                    }
                }
            }
        }


    }
}

@Preview(showBackground = true, backgroundColor = 1L)
@Composable
private fun CustomChannelViewComponentPreview() {
    LtsTheme {
        CustomChannelViewComponent(title = "Popular Channels",
            channelList = listOf<ChannelData?>(
                ChannelData(channelname = "My Channel 1", followers = "1 Followers"),
                ChannelData(channelname = "My Channel 2", followers = "2 Followers"),
                ChannelData(channelname = "My Channel 3" , followers = "3 Followers"),
                ChannelData(channelname = "My Channel 4" , followers = "4 Followers"),

            ),
            onChannelEvent = {},
            onHomeDataEvent = {}
        )
    }
}