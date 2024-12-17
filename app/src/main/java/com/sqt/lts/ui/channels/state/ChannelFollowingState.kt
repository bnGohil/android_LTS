package com.sqt.lts.ui.channels.state

import com.example.lts.base.BaseCommonResponseModel
import com.sqt.lts.ui.channels.event.FollowingType
import com.example.lts.utils.network.DataState

data class ChannelFollowingState(val channelFollowingType: FollowingType?=null,
                                 val errorMsg: String?=null,
                                 val isLoading: Boolean?=null,
                                 val channelId: Int?=null,
                                 val data: DataState<BaseCommonResponseModel.Data?>?=null)