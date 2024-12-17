package com.sqt.lts.ui.history.request


import com.google.gson.annotations.SerializedName

data class HistoryAndWatchListRequestModel(
    @SerializedName("days")
    val days: Int? = null,
    @SerializedName("limit")
    val limit: Int? = null,
    @SerializedName("page")
    var page: Int? = null,
    @SerializedName("sortcolumn")
    val sortcolumn: String? = null,
    @SerializedName("sortdirection")
    val sortdirection: String? = null,
    val isFirst: Boolean?=null
)