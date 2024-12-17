package com.example.lts.ui.sharedPreferences.data

import com.example.lts.utils.ResponseData

data class SaveLoginState(val isLogin:Boolean?=null,
                          val token: String?=null,
                          val associationId : Int?=null,
                          val associationType:String?=null
                          ,val response : ResponseData?=null)

