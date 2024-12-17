package com.example.lts.ui.auth.data.request

import com.google.gson.annotations.SerializedName

data class ForgotPasswordRequestModel(
    @SerializedName("email")
    val email:String?= null
)