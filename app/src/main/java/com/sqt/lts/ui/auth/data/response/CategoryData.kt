package com.example.lts.ui.auth.data.response


import com.google.gson.annotations.SerializedName

data class CategoryData(
    @SerializedName("categoryid")
    val categoryid: Int?,
    @SerializedName("categoryname")
    val categoryname: String?
)