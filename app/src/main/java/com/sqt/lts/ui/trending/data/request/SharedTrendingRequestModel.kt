package com.sqt.lts.ui.trending.data.request


import com.google.gson.annotations.SerializedName

data class SharedTrendingRequestModel(
    @SerializedName("resourceid")
    val resourceid: Int
)