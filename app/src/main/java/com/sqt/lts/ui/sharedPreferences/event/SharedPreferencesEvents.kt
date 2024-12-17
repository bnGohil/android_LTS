package com.example.lts.ui.sharedPreferences.event

import com.example.lts.ui.sharedPreferences.data.SaveLoginState

sealed class SharedPreferencesEvents {

    data class SetLoginState(val saveLoginState: SaveLoginState?=null) : SharedPreferencesEvents()
    data object GetLoginState : SharedPreferencesEvents()
    data class CheckAuthentication(var isLoading:Boolean?=null) : SharedPreferencesEvents()
    data object Logout : SharedPreferencesEvents()

}