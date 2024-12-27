package com.sqt.lts.ui.home.event

import com.sqt.lts.ui.channels.event.FollowingType
import com.sqt.lts.ui.channels.data.response.ChannelData
import com.sqt.lts.ui.trending.data.response.VideoAudio

sealed class HomeEvent {
    data class GetVideoList(val list: List<VideoAudio?>?= arrayListOf<VideoAudio?>(),val first: Boolean?=false) : HomeEvent()
    data class GetChannelList(val list: List<ChannelData?>?= arrayListOf<ChannelData?>(), val isFirst: Boolean?=false) : HomeEvent()
    data class UpdateHomeFollowUnFollowData(val channelId: Int?,val followingType: FollowingType?): HomeEvent()
    data class UpdateResourceData(val resourceId : Int?=null) : HomeEvent()
    data object ClearData : HomeEvent()
    data object GetHomeData: HomeEvent()
}