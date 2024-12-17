package com.sqt.lts.ui.channels.data.request


import com.google.gson.annotations.SerializedName
import com.sqt.lts.ui.channels.state.SelectedChannel

data class ChannelRequestModel(
    @SerializedName("categoryids")
    val categoryIds: String? = null,
    @SerializedName("exceptchannelids")
    val exceptChannelIds: String? = null,
    @SerializedName("limit")
    val limit: Int? = null,
    @SerializedName("mycreatedchannel")
    val myCreatedChannel: Int? = null,
    @SerializedName("myfollowingchannel")
    val myFollowingChannel: Int? = null,
    @SerializedName("page")
    var page: Int? = null,
    @SerializedName("sortcolumn")
    val sortColumn: String? = null,
    @SerializedName("sortdirection")
    val sortDirection: String? = null,
    val isFirst: Boolean?=null,
    val currentRecord: Int? = null,
    val selected:SelectedChannel?=null
)