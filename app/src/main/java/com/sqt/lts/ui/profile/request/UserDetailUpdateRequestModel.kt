package com.sqt.lts.ui.profile.request



import android.net.Uri
import com.google.gson.annotations.SerializedName

data class UserDetailUpdateRequestModel(
    @SerializedName("add1")
    val add1: String? = null,
    @SerializedName("add2")
    val add2: String? = null,
    @SerializedName("city")
    val city: String? = null,
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("facebookprofile")
    val facebookprofile: String? = null,
    @SerializedName("fname")
    val fname: String? = null,
    @SerializedName("instagramprofile:")
    val instagramprofile: String? = null,
    @SerializedName("lname")
    val lname: String? = null,
    @SerializedName("phoneno")
    val phoneno: String? = null,
    @SerializedName("state")
    val state: String? = null,
    @SerializedName("twitterprofile:")
    val twitterprofile: String? = null,
    @SerializedName("zipcode")
    val zipcode: String? = null,
    val bannerUri : Uri?=null,
    val photoUri : Uri?=null
)