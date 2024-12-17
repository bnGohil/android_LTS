package com.sqt.lts.ui.trending.data.response


import com.google.gson.annotations.SerializedName

data class PlayListItem(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("thumb")
    val thumb: String? = null
)