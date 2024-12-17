package com.example.lts.ui.auth.data.request


import com.google.gson.annotations.SerializedName

data class LoginUserRequestModel(
    @SerializedName("password")
    val password: String,
    @SerializedName("username")
    val username: String
)