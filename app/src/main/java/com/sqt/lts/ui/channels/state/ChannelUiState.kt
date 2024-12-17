package com.sqt.lts.ui.channels.state

import com.example.lts.utils.network.DataState
import com.sqt.lts.ui.channels.data.response.ChannelData
import com.sqt.lts.ui.channels.data.response.GetChannelDetailDataModel

data class ChannelUiState(
    var channelList: List<ChannelData?>? = arrayListOf(),
    val selectedList: List<Int?>? = arrayListOf(),
    val dataState : DataState<GetChannelDetailDataModel.Data?> ?= null,
    val page : Int = 1,
    val totalCount : Int = 0,
    val isLoading: Boolean?=false,
    val isFirst: Boolean?=false
    )