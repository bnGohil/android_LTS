package com.sqt.lts.ui.channels.data.response


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("banner")
    val banner: String,
    @SerializedName("bannerurl")
    val bannerurl: String,
    @SerializedName("channelcode")
    val channelcode: String,
    @SerializedName("channelid")
    val channelid: Int,
    @SerializedName("channelimg")
    val channelimg: String,
    @SerializedName("channelimgurl")
    val channelimgurl: String,
    @SerializedName("channelname")
    val channelname: String,
    @SerializedName("createdby")
    val createdby: Int,
    @SerializedName("createdon")
    val createdon: String,
    @SerializedName("facebookprofile")
    val facebookprofile: String,
    @SerializedName("followers")
    val followers: String,
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("instagramprofile")
    val instagramprofile: String,
    @SerializedName("isactive")
    val isactive: Int,
    @SerializedName("isfollowchannel")
    val isfollowchannel: Int,
    @SerializedName("memberid")
    val memberid: Int,
    @SerializedName("publishedon")
    val publishedon: String,
    @SerializedName("qblogin")
    val qblogin: String,
    @SerializedName("qbpassword")
    val qbpassword: String,
    @SerializedName("qbuserid")
    val qbuserid: Int,
    @SerializedName("totalaudio")
    val totalaudio: Int,
    @SerializedName("totaldislike")
    val totaldislike: Int,
    @SerializedName("totalfollowers")
    val totalfollowers: Int,
    @SerializedName("totallike")
    val totallike: Int,
    @SerializedName("totalotherresource")
    val totalotherresource: Int,
    @SerializedName("totalvideo")
    val totalvideo: Int,
    @SerializedName("twitterprofile")
    val twitterprofile: String,
    @SerializedName("updatedby")
    val updatedby: Any?,
    @SerializedName("updatedon")
    val updatedon: String
)