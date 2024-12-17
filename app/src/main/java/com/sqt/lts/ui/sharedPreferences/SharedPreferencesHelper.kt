package com.example.lts.ui.sharedPreferences


import android.content.Context
import android.content.SharedPreferences
import com.example.lts.ui.sharedPreferences.data.SaveLoginState

class SharedPreferencesHelper(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)




    companion object {
        const val IS_LOGIN = "IS_LOGIN"
        const val TOKEN = "TOKEN"
        const val ASSOCIATION_ID = "ASSOCIATION_ID"
        const val ASSOCIATION_TYPE = "ASSOCIATION_TYPE"
        const val PREF_NAME = "app_preferences"
    }

   suspend fun saveLoginState(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    suspend fun saveAllUserData(saveLoginState: SaveLoginState?=null) {
        sharedPreferences.edit().putBoolean(IS_LOGIN,saveLoginState?.isLogin?:false).apply()
        sharedPreferences.edit().putString(TOKEN,saveLoginState?.token).apply()
        sharedPreferences.edit().putInt(ASSOCIATION_ID,saveLoginState?.associationId?:0).apply()
        sharedPreferences.edit().putString(ASSOCIATION_TYPE,saveLoginState?.associationType).apply()
    }

    suspend fun getAllUserData(): SaveLoginState {
        val isLogin = sharedPreferences.getBoolean(IS_LOGIN,false)
        val token = sharedPreferences.getString(TOKEN,"")
        val associationId = sharedPreferences.getInt(ASSOCIATION_ID,0)
        val associationType = sharedPreferences.getString(ASSOCIATION_TYPE,"")
        return SaveLoginState(token = token, isLogin = isLogin, associationId = associationId, associationType = associationType)
    }



   suspend fun getLoginState(key: String, defaultValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue) ?: defaultValue
    }

    suspend fun clearAllData(){
        sharedPreferences.edit().clear().apply()
    }

}
