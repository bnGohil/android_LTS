package com.sqt.lts.base


import com.example.lts.base.BaseResponse
import com.example.lts.ui.auth.data.response.CountryData
import com.google.gson.annotations.SerializedName

data class GlobalSearchResponseModel(
    @SerializedName("data")
    override val data: List<GlobalSearchData>?= arrayListOf()
):BaseResponse<List<GlobalSearchData?>>()


data class GlobalSearchData(
    @SerializedName("categoryname")
    val categoryname: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("resourcedurationinminute")
    val resourcedurationinminute: String,
    @SerializedName("thumbimg")
    val thumbimg: String,
    @SerializedName("thumbimgurl")
    val thumbimgurl: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String
)