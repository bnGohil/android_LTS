package com.example.lts.ui.auth.data.response


import com.example.lts.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class LoginUserResponseModel(
    @SerializedName("data")
    override val `data`: LoginResData?=null,
    val isLoading : Boolean?= null,
    val exception: Exception? = null,
    ):BaseResponse<LoginUserResponseModel.LoginResData>(){
    data class LoginResData(
        @SerializedName("associationid")
        val associationId: Int?,
        @SerializedName("associationtype")
        val associationtype: String?,
        @SerializedName("categorydata")
        val categorydata: List<CategoryData?>?,
        @SerializedName("createdby")
        val createdby: Any?,
        @SerializedName("createdon")
        val createdon: String?,
        @SerializedName("currentpackageid")
        val currentpackageid: Int?,
        @SerializedName("displayname")
        val displayname: String?,
        @SerializedName("isactive")
        val isactive: Int?,
        @SerializedName("isblock")
        val isblock: Int?,
        @SerializedName("isdefault")
        val isdefault: Int?,
        @SerializedName("memberid")
        val memberid: Int?,
        @SerializedName("memberno")
        val memberno: String?,
        @SerializedName("no_of_subscription")
        val noOfSubscription: Int?,
        @SerializedName("no_of_uploaded")
        val noOfUploaded: Int?,
        @SerializedName("no_of_view")
        val noOfView: Int?,
        @SerializedName("package_frequency")
        val packageFrequency: String?,
        @SerializedName("packageid")
        val packageid: Int?,
        @SerializedName("packagename")
        val packagename: String?,
        @SerializedName("packagetype")
        val packagetype: String?,
        @SerializedName("photo")
        val photo: Any?,
        @SerializedName("roleid")
        val roleid: Any?,
        @SerializedName("token")
        val token: String?,
        @SerializedName("updatedby")
        val updatedby: Any?,
        @SerializedName("updatedon")
        val updatedon: String?,
        @SerializedName("upload_frequency")
        val uploadFrequency: String?,
        @SerializedName("userid")
        val userid: Int?,
        @SerializedName("username")
        val username: String?,
        @SerializedName("view_frequency")
        val viewFrequency: String?
    )
}