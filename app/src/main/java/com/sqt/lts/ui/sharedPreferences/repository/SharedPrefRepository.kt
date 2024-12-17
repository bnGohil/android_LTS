package com.example.lts.ui.sharedPreferences.repository

import com.example.lts.ui.sharedPreferences.SharedPreferencesHelper
import com.example.lts.ui.sharedPreferences.data.SaveLoginState
import javax.inject.Inject

class SharedPrefRepository @Inject constructor(private val sharedPrefHelper: SharedPreferencesHelper){

//    suspend fun saveLoginState(key: String, value: Boolean){
//        sharedPrefHelper.saveLoginState(key = key, value = value)
//    }

    suspend fun saveLoginState(saveLoginState:SaveLoginState?=null){
        sharedPrefHelper.saveAllUserData(saveLoginState)
    }

    suspend fun getLoginState(): SaveLoginState {
       return sharedPrefHelper.getAllUserData()
    }

    suspend fun clearAllData(){
        sharedPrefHelper.clearAllData()
    }

//    suspend fun getLoginState(key: String):Boolean{
//        return sharedPrefHelper.getLoginState(key)
//    }

}