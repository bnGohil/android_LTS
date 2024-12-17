package com.sqt.lts.ui.channels.data.request


import com.google.gson.annotations.SerializedName

data class FollowAndUnFollowRequestModel(
    @SerializedName("channelid")
    val channelid: Int? = null
)