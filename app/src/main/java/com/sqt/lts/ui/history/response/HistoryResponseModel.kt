package com.sqt.lts.ui.history.response


import com.example.lts.base.BaseResponse
import com.google.gson.annotations.SerializedName
import com.sqt.lts.ui.trending.data.response.VideoAudio

data class HistoryResponseModel(
    @SerializedName("data")
    override val `data`: Data? = null,
    ): BaseResponse<HistoryResponseModel.Data>(){
    data class Data(
        @SerializedName("currentPage")
        val currentPage: Int? = null,
        @SerializedName("historyList")
        val historyList: List<VideoAudio?>? = arrayListOf(),
        @SerializedName("limit")
        val limit: Int? = null,
        @SerializedName("totalPages")
        val totalPages: Int? = null,
        @SerializedName("totalRecords")
        val totalRecords: Int? = null
    )
}