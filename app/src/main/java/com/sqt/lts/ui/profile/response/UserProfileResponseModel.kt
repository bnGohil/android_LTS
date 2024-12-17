package com.sqt.lts.ui.profile.response


import com.example.lts.base.BaseResponse
import com.example.lts.ui.categories.data.response.Category
import com.google.gson.annotations.SerializedName

data class UserProfileResponseModel(
    @SerializedName("data")
    override val data: Data?=null

): BaseResponse<UserProfileResponseModel.Data>(){
    data class Data(
        @SerializedName("add1")
        val add1: String? = null,
        @SerializedName("add2")
        val add2: String? = null,
        @SerializedName("associationid")
        val associationid: Int? = null,
        @SerializedName("associationtype")
        val associationtype: String? = null,
        @SerializedName("banner")
        val banner: String? = null,
        @SerializedName("bannerurl")
        val bannerurl: String? = null,
        @SerializedName("categorydata")
        val categorydata: List<Category?>? = null,
        @SerializedName("city")
        val city: String? = null,
        @SerializedName("country")
        val country: String? = null,
        @SerializedName("createdby")
        val createdby: Any? = null,
        @SerializedName("createdon")
        val createdon: String? = null,
        @SerializedName("currentpackageid")
        val currentpackageid: Int? = null,
        @SerializedName("displayname")
        val displayname: String? = null,
        @SerializedName("email")
        val email: String? = null,
        @SerializedName("facebookprofile")
        val facebookprofile: String? = null,
        @SerializedName("fname")
        var fname: String? = null,
        @SerializedName("instagramprofile")
        val instagramprofile: String? = null,
        @SerializedName("isactive")
        val isactive: Int? = null,
        @SerializedName("isblock")
        val isblock: Int? = null,
        @SerializedName("isdefault")
        val isdefault: Int? = null,
        @SerializedName("lname")
        val lname: String? = null,
        @SerializedName("memberid")
        val memberid: Int? = null,
        @SerializedName("mname")
        val mname: String? = null,
        @SerializedName("no_of_subscription")
        val noOfSubscription: Int? = null,
        @SerializedName("no_of_uploaded")
        val noOfUploaded: Int? = null,
        @SerializedName("no_of_view")
        val noOfView: Int? = null,
        @SerializedName("package_frequency")
        val packageFrequency: String? = null,
        @SerializedName("packageid")
        val packageid: Int? = null,
        @SerializedName("packagename")
        val packagename: String? = null,
        @SerializedName("packagetype")
        val packagetype: String? = null,
        @SerializedName("photo")
        val photo: String? = null,
        @SerializedName("photourl")
        val photourl: String? = null,
        @SerializedName("primaryno")
        val primaryno: String? = null,
        @SerializedName("qblogin")
        val qblogin: String? = null,
        @SerializedName("qbpassword")
        val qbpassword: String? = null,
        @SerializedName("qbuserid")
        val qbuserid: Int? = null,
        @SerializedName("roleid")
        val roleid: Any? = null,
        @SerializedName("state")
        val state: String? = null,
        @SerializedName("twitterprofile")
        val twitterprofile: String? = null,
        @SerializedName("updatedby")
        val updatedby: Any? = null,
        @SerializedName("updatedon")
        val updatedon: String? = null,
        @SerializedName("upload_frequency")
        val uploadFrequency: String? = null,
        @SerializedName("username")
        val username: String? = null,
        @SerializedName("view_frequency")
        val viewFrequency: String? = null,
        @SerializedName("zipcode")
        val zipcode: String? = null
    )
}