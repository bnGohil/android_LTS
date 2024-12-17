package com.sqt.lts.ui.channels.data.response


import com.example.lts.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class ChannelDataResponseModel(
    @SerializedName("data")
    override val `data`: ChannelData? = null,
    ): BaseResponse<ChannelDataResponseModel.ChannelData>(){
    data class ChannelData(
        @SerializedName("banner")
        val banner: String? = null,
        @SerializedName("bannerurl")
        val bannerurl: String? = null,
        @SerializedName("channelcode")
        val channelcode: String? = null,
        @SerializedName("channelid")
        val channelid: Int? = null,
        @SerializedName("channelimg")
        val channelimg: String? = null,
        @SerializedName("channelimgurl")
        val channelimgurl: String? = null,
        @SerializedName("channelname")
        val channelname: String? = null,
        @SerializedName("countchannelmember")
        var countchannelmember: Int? = null,
        @SerializedName("createdby")
        val createdby: Int? = null,
        @SerializedName("followers")
        val followers: String? = null,
        @SerializedName("isfollowchannel")
        var isfollowchannel: Int? = null,
        @SerializedName("memberid")
        val memberid: Int? = null,
        @SerializedName("publishedon")
        val publishedon: String? = null,
        @SerializedName("rownumber")
        val rownumber: Int? = null,
        @SerializedName("totalaudio")
        val totalaudio: Int? = null,
        @SerializedName("totaldislike")
        val totaldislike: Int? = null,
        @SerializedName("totalfollowers")
        val totalfollowers: Int? = null,
        @SerializedName("totallike")
        val totallike: Int? = null,
        @SerializedName("totalotherresource")
        val totalotherresource: Int? = null,
        @SerializedName("totalrecords")
        val totalrecords: Int? = null,
        @SerializedName("totalvideo")
        val totalvideo: Int? = null,
        @SerializedName("updatedby")
        val updatedby: Int? = null,
        var following : Boolean?= false
    )
}