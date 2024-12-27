package com.sqt.lts.ui.channels.data.response


import com.example.lts.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class ChannelDetailResponseModel(
    @SerializedName("data")
    override val `data`: ChannelData?=null
):BaseResponse<ChannelData>()