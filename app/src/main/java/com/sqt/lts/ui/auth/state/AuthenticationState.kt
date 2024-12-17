package com.example.lts.ui.auth.state

import android.content.Context

data class AuthenticationState(val token:String?=null, var isLogin:Boolean, var userID: Int?= null, val context: Context?=null)
