package com.sqt.lts.ui.channels.state

import android.os.Message
import com.sqt.lts.ui.channels.data.response.ChannelData
import com.sqt.lts.ui.channels.data.response.ChannelDataResponseModel

data class ChannelDetailUiState(
    val isLoading : Boolean?= null,
    val channelData: ChannelDataResponseModel.ChannelData?=null,
    val exception: Exception?=null,
    val message: String?= null
)
