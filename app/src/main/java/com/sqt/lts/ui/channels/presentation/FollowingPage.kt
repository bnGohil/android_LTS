package com.example.lts.ui.channels.presentation
import androidx.compose.foundation.layout.padding
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
import com.example.lts.custom_component.CustomTopBar
import com.sqt.lts.ui.channels.event.ChannelEvent
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.ui.theme.kBackGround
import com.example.lts.utils.scaleSize
import com.sqt.lts.ui.channels.data.request.ChannelRequestModel
import com.sqt.lts.ui.channels.presentation.ChannelListPage
import com.sqt.lts.ui.channels.state.ChannelUiState

@Composable
fun FollowingPage(
    navigationHostController: NavHostController,
    onChannelEvent:(ChannelEvent) -> Unit,
    channelUiState:ChannelUiState?=null,
    ) {


    val isPagingLoading = (channelUiState?.isLoading == true && channelUiState.channelList?.isNotEmpty() == true)
    val lazyGridState = rememberLazyListState()


    LaunchedEffect(lazyGridState) {
        snapshotFlow { lazyGridState.layoutInfo.visibleItemsInfo }
            .collect{
                if((it.lastOrNull()?.index?:0) >= 14 && !isPagingLoading && it.lastOrNull()?.index?.plus(1) == lazyGridState.layoutInfo.totalItemsCount){
                    onChannelEvent(ChannelEvent.GetChannelData(
                        channelRequestModel = ChannelRequestModel(
                            limit = 10,
                            isFirst = false,
                            sortDirection = "desc",
                            sortColumn = "",
                            exceptChannelIds = "",
                            myCreatedChannel = 0,
                            myFollowingChannel = 0,
                        )
                    ))
                }
            }
    }

    Scaffold(containerColor = kBackGround, topBar = {
        CustomTopBar(navHostController = navigationHostController, title = "Following") }) {
        paddingValues ->
        ChannelListPage(
            modifier = Modifier.padding(paddingValues).padding(horizontal = 10.dp.scaleSize(), vertical = 10.dp),
            navigationHostController = navigationHostController,
            channelUiState = channelUiState,
            lazyGridState = lazyGridState,
            onChannelEvent = onChannelEvent)
    }
}

@Preview
@Composable
private fun FollowingPagePreview() {
    LtsTheme {
        FollowingPage(
            navigationHostController = rememberNavController(),
            onChannelEvent = {}
            )
    }
}