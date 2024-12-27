package com.sqt.lts.ui.channels.state

import com.example.lts.utils.network.DataState
import com.sqt.lts.ui.channels.data.response.ChannelDataResponseModel

data class ChannelDetailUiState(
    val isLoading: Boolean?= null,
    val channelData: ChannelDataResponseModel.ChannelData?=null,
    val channelDataState: DataState<ChannelDataResponseModel.ChannelData?> ?=null,
    val exception: Exception?=null,
    val message: String?= null
)
