package com.sqt.lts.ui.channels.event

import android.net.Uri
import com.sqt.lts.ui.channels.data.request.ChannelRequestModel
import com.sqt.lts.ui.channels.data.response.ChannelData
import com.sqt.lts.ui.channels.state.SelectedChannelState

sealed class ChannelEvent {

    data class GetHomeChannelData(val channelRequestModel : ChannelRequestModel?) : ChannelEvent()
    data class GetCaAccountChannelData(val channelRequestModel : ChannelRequestModel?) : ChannelEvent()
    data class GetFollowingChannelData(val channelRequestModel : ChannelRequestModel?) : ChannelEvent()
    data class GetChannelData(val channelRequestModel : ChannelRequestModel?) : ChannelEvent()
    data class GetSavePostForChannelData(val channelRequestModel : ChannelRequestModel?) : ChannelEvent()
    data class ChangeStatusForFollowAndUnFollow(val channelData: ChannelData) : ChannelEvent()
    data class SelectedChannelTabData(val selectedChannelState: SelectedChannelState) : ChannelEvent()
    data object ClearSelectedList : ChannelEvent()
    data class FollowUnFollowUpdateChannel(val followingType: FollowingType,val channelId: Int) : ChannelEvent()
    data class FollowChannelEvent(val channelId: Int,val followingType: FollowingType?=null) : ChannelEvent()
    data class GetChannelDetailEvent(val channelId: Int) : ChannelEvent()
    data class UnFollowChannelEvent(val channelId: Int,val followingType: FollowingType?=null) : ChannelEvent()
    data class CreateChannelEvent(val channelName: String?,val photo:Uri?,val banner: Uri?) : ChannelEvent()
    data class UpdateChannelEvent(val channelName: String?,val photo:Uri?,val banner: Uri?,val channelId: Int?) : ChannelEvent()
}

enum class FollowingType{FOLLOW,UNFOLLOW}