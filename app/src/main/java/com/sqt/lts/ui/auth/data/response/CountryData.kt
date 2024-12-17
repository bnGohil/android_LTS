package com.example.lts.ui.auth.data.response


import com.google.gson.annotations.SerializedName

data class CountryData(
    @SerializedName("countrycode")
    val countrycode: String?,
    @SerializedName("countryid")
    val countryid: Int?,
    @SerializedName("countryname")
    val countryname: String?
)