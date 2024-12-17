package com.example.lts.ui.auth.data.request


import com.google.gson.annotations.SerializedName

data class CreateUserRequestModel(
    @SerializedName("country")
    val country: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("fname")
    val fname: String?,
    @SerializedName("lname")
    val lname: String?
)