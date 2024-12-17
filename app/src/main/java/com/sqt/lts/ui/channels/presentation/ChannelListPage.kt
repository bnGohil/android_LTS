package com.sqt.lts.ui.channels.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.lts.ui.channels.channel_custom_component.FollowingElementComponent
import com.sqt.lts.ui.channels.event.ChannelEvent
import com.example.lts.ui.shimmer.ShimmerEffectBox
import com.sqt.lts.ui.channels.data.response.ChannelData
import com.sqt.lts.ui.channels.state.ChannelFollowingState
import com.sqt.lts.ui.channels.state.ChannelUiState
import com.sqt.lts.ui.theme.LtsTheme

@SuppressLint("RememberReturnType")
@Composable
fun ChannelListPage(
    modifier: Modifier,
    channelUiState: ChannelUiState? =null,
    onChannelEvent:(ChannelEvent) -> Unit,
    channelDataState:ChannelFollowingState?=null,
    navigationHostController: NavHostController,
    lazyGridState: LazyListState) {




    LazyColumn(modifier, state = lazyGridState) {


        items(if(channelUiState?.isLoading == true && channelUiState.channelList?.isEmpty() == true) 10 else channelUiState?.channelList?.size?:0){



            ShimmerEffectBox(
                isShow = channelUiState?.isLoading == true,
                modifier = if(channelUiState?.isLoading == false) Modifier else Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(vertical = 5.dp)
            ){
                println("channelUiState?.channelList?.get(it) is ${channelUiState?.channelList?.get(it)}")

                FollowingElementComponent(
                    channel = channelUiState?.channelList?.get(it),
                    navigationHostController= navigationHostController,
                    onChannelEvent = onChannelEvent,
                    channelDataState = channelDataState
                )
            }





        }
    }
}

@Preview
@Composable
private fun ChannelListPagePreview() {
    LtsTheme {
        ChannelListPage(
            navigationHostController = rememberNavController(),
            channelUiState = ChannelUiState(
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
            modifier = Modifier,
            lazyGridState = rememberLazyListState(),
            onChannelEvent = {}
        )
    }
}