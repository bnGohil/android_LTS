package com.sqt.lts.base


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("fontcolor")
    val fontcolor: Any?,
    @SerializedName("forcolor")
    val forcolor: Any?,
    @SerializedName("termcategory")
    val termcategory: String?,
    @SerializedName("termcode")
    val termcode: Any?,
    @SerializedName("termid")
    val termid: Int,
    @SerializedName("termname")
    val termname: String?
)