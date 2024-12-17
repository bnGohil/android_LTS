package com.example.lts.ui.auth.data.response


import com.example.lts.base.BaseResponse

//data class GetCountryDataModel(
//    @SerializedName("data")
//    override val data: CountryList?
//):BaseResponse<GetCountryDataModel.CountryList>(){
//    data class CountryList(@SerializedName("data") val data: ArrayList<CountryData> = arrayListOf())
//}

data class GetCountryDataModel(override val data: ArrayList<CountryData>?= arrayListOf()):BaseResponse<ArrayList<CountryData>?>()
