package com.sqt.lts.ui.home.homeUiState

import com.sqt.lts.ui.channels.data.response.ChannelData
import com.sqt.lts.ui.home.enums.HomeDataEnums
import com.sqt.lts.ui.trending.data.response.VideoAudio
import com.sqt.lts.utils.enums.ApiResponseType
import com.sqt.lts.utils.enums.GlobalSearchORHomeData

data class HomeResourceAndChannelJoinModel(
    var channelList: List<ChannelData?>?= arrayListOf<ChannelData?>(),
    val videoItem: VideoAudio?=null,
    val homeDataEnums:HomeDataEnums?=null
)

data class HomeResourceAndChannelUiState(
    var homeDataList : List<HomeResourceAndChannelJoinModel?> = arrayListOf<HomeResourceAndChannelJoinModel?>(),
    val isLoading: Boolean?=null,
    val apiResponseType: ApiResponseType ?=null,
    val typeForSelection : GlobalSearchORHomeData?=null
)