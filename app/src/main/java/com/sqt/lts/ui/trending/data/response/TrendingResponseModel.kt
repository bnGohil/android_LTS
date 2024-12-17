package com.sqt.lts.ui.trending.data.response
import com.example.lts.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class TrendingResponseModel(
    @SerializedName("data")
    override val `data`: TrendingData? = null,
    ): BaseResponse<TrendingResponseModel.TrendingData>(){
    data class TrendingData(
        @SerializedName("currentPage")
        val currentPage: Int? = null,
        @SerializedName("limit")
        val limit: String? = null,
        @SerializedName("totalPages")
        val totalPages: Int? = null,
        @SerializedName("totalRecords")
        val totalRecords: Int? = null,
        @SerializedName("videoAudioList")
        val videoAudioList: ArrayList<VideoAudio?>? = arrayListOf()
    )
}