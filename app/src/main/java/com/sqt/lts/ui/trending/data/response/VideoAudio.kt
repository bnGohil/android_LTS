package com.sqt.lts.ui.trending.data.response


import com.google.gson.annotations.SerializedName

data class VideoAudio(
    @SerializedName("associationid")
    val associationid: Int? = null,
    @SerializedName("associationtype")
    val associationtype: String? = null,
    @SerializedName("categoryids")
    val categoryids: String? = null,
    @SerializedName("categoryname")
    val categoryname: String? = null,
    @SerializedName("countmemberwatched")
    val countmemberwatched: Int? = null,
    @SerializedName("durationinsecond")
    val durationinsecond: Int? = null,
    @SerializedName("isaddedinwatchlist")
    val isaddedinwatchlist: Int? = null,
    @SerializedName("longdetails")
    val longdetails: String? = null,
    @SerializedName("mediatype")
    val mediatype: String? = null,
    @SerializedName("mediatypename")
    val mediatypename: String? = null,
    @SerializedName("memberid")
    val memberid: Int? = null,
    @SerializedName("publishedon")
    val publishedon: String? = null,
    @SerializedName("resourcedurationinminute")
    val resourcedurationinminute: String? = null,
    @SerializedName("resourceid")
    val resourceid: Int? = null,
    @SerializedName("rownumber")
    val rownumber: Int? = null,
    @SerializedName("shortdetails")
    val shortdetails: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("tags")
    val tags: List<String?>? = null,
    @SerializedName("thumbimg")
    val thumbimg: String? = null,
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
    @SerializedName("totalrecords")
    val totalrecords: Int? = null,
    @SerializedName("totalshare")
    val totalshare: Int? = null,
    @SerializedName("totalview")
    val totalview: Int? = null,
    @SerializedName("views")
    val views: String? = null,
    @SerializedName("viewtypeterm")
    val viewtypeterm: String? = null,
    @SerializedName("visibility")
    val visibility: String? = null
)