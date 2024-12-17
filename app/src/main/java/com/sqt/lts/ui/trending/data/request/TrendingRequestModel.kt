package com.sqt.lts.ui.trending.data.request


import com.google.gson.annotations.SerializedName

data class TrendingRequestModel(
    @SerializedName("categoryids")
    val categoryIds: String? = null,
    @SerializedName("channelid")
    val channelId: Int? = null,
    @SerializedName("displayloginuseruploaded")
    val displayloginuseruploaded: Int? = null,
    @SerializedName("exceptresourceids")
    val exceptResourceIds: String? = null,
    @SerializedName("limit")
    val limit: Int? = null,
    @SerializedName("mediatype")
    val mediaType: String? = null,
    @SerializedName("page")
    var page: Int? = null,
    @SerializedName("sortcolumn")
    val sortColumn: String? = null,
    @SerializedName("sortdirection")
    val sortDirection: String? = null,
    val currentRecord: Int? = 0,
    val isFirst: Boolean? = null
)