package com.sqt.lts.base


import com.example.lts.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class BaseTermDataModel(
    @SerializedName("data")
    override val `data`: ArrayList<Data> = arrayListOf(),
    ):BaseResponse<ArrayList<Data>>()