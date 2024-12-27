package com.sqt.lts.ui.trending.data.response


import com.google.gson.annotations.SerializedName

data class TrendingLikeAndDisLikeModel(
    @SerializedName("isdislike")
    val isdislike: Boolean,
    @SerializedName("islike")
    val islike: Boolean,
    @SerializedName("resourceid")
    val resourceid: Int
)