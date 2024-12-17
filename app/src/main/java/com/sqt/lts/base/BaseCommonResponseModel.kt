package com.example.lts.base


import com.example.lts.ui.auth.data.response.CountryData
import com.google.gson.annotations.SerializedName

data class BaseCommonResponseModel(
    @SerializedName("data")
    override val `data`: Data?,
):BaseResponse<BaseCommonResponseModel.Data>(){
   data class Data(val isLoading: Boolean?=null)
}

