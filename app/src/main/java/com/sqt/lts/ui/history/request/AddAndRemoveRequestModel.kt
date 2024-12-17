package com.sqt.lts.ui.history.request


import com.google.gson.annotations.SerializedName

data class AddAndRemoveRequestModel(
    @SerializedName("resourceid")
    val resourceId: Int? = null
)