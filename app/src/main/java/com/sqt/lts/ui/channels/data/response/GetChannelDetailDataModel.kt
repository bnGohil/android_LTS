package com.sqt.lts.ui.channels.data.response


import com.example.lts.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetChannelDetailDataModel(
    @SerializedName("data")
    override val `data`: Data? = null,
): BaseResponse<GetChannelDetailDataModel.Data>(){
    data class Data(
        @SerializedName("channelList")
        val channelList: ArrayList<ChannelData?>? = null,
        @SerializedName("currentPage")
        val currentPage: Int? = null,
        @SerializedName("limit")
        val limit: String? = null,
        @SerializedName("totalPages")
        val totalPages: Int? = null,
        @SerializedName("totalRecords")
        val totalRecords: Int? = null
    )
}