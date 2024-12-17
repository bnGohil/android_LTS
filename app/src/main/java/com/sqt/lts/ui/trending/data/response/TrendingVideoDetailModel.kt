package com.sqt.lts.ui.trending.data.response


import com.example.lts.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class TrendingVideoDetailModel(
    @SerializedName("data")
    override val data: Data?=null
): BaseResponse<TrendingVideoDetailModel.Data>(){
    data class Data(

        @SerializedName("associationid")
        val associationid: Int? = null,
        @SerializedName("associationtype")
        val associationtype: String? = null,
        @SerializedName("bannerurl")
        val bannerurl: String? = null,
        @SerializedName("categoryids")
        val categoryids: List<Int?>? = null,
        @SerializedName("categoryname")
        val categoryname: String? = null,
        @SerializedName("channelid")
        val channelid: Int? = null,
        @SerializedName("channelimgurl")
        val channelimgurl: String? = null,
        @SerializedName("channelname")
        val channelname: String? = null,
        @SerializedName("countmemberwatched")
        val countmemberwatched: Int? = null,
        @SerializedName("createdby")
        val createdby: Int? = null,
        @SerializedName("createdon")
        val createdon: String? = null,
        @SerializedName("durationinsecond")
        val durationinsecond: Int? = null,
        @SerializedName("isagerestriction")
        val isagerestriction: Any? = null,
        @SerializedName("isdislike")
        val isdislike: Int? = null,
        @SerializedName("isfollowchannel")
        var isFollowChannel: Int = 0,
        @SerializedName("islike")
        val islike: Int? = null,
        @SerializedName("languageofvideo")
        val languageofvideo: String? = null,
        @SerializedName("longdetails")
        val longdetails: String? = null,
        @SerializedName("mediatype")
        val mediatype: String? = null,
        @SerializedName("mediatypename")
        val mediatypename: String? = null,
        @SerializedName("memberid")
        val memberid: Int? = null,
        @SerializedName("orientation")
        val orientation: String? = null,
        @SerializedName("publishedon")
        val publishedon: String? = null,
        @SerializedName("qualityofvideo")
        val qualityofvideo: String? = null,
        @SerializedName("resourcedurationinminute")
        val resourcedurationinminute: String? = null,
        @SerializedName("resourceid")
        val resourceid: Int? = null,
        @SerializedName("resourceurl")
        val resourceurl: String? = null,
        @SerializedName("shortdetails")
        val shortdetails: String? = null,
        @SerializedName("sizeinmb")
        val sizeinmb: String? = null,
        @SerializedName("status")
        val status: String? = null,
        @SerializedName("tags")
        val tags: List<String?>? = null,
        @SerializedName("thumbimgurl")
        val thumbimgurl: String? = null,
        @SerializedName("timedurationupload")
        val timedurationupload: String? = null,
        @SerializedName("title")
        val title: String? = null,
        @SerializedName("totalcomments")
        val totalcomments: Int? = null,
        @SerializedName("totaldislike")
        val totaldislike: Int? = null,
        @SerializedName("totallike")
        val totallike: Int? = null,
        @SerializedName("totalshare")
        val totalshare: Int? = null,
        @SerializedName("totalview")
        val totalview: Int? = null,
        @SerializedName("updatedby")
        val updatedby: Int? = null,
        @SerializedName("updatedon")
        val updatedon: String? = null,
        @SerializedName("views")
        val views: String? = null,
        @SerializedName("viewtypeterm")
        val viewtypeterm: String? = null,
        @SerializedName("visibility")
        val visibility: String? = null
    )
}