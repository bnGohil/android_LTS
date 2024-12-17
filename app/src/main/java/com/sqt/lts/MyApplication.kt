package com.example.lts

import android.app.Application
import android.content.Context
import com.example.lts.ui.sharedPreferences.SharedPreferencesHelper
import com.example.lts.ui.sharedPreferences.repository.SharedPrefRepository
import com.example.lts.ui.sharedPreferences.sharedPreferences_view_model.SharedPreferencesViewModel
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {



    companion object {


        lateinit var instance: MyApplication
        lateinit var appContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
          instance = this

        appContext = applicationContext

        val sharedPreferencesHelper = SharedPreferencesHelper(appContext)
        setAuthToken(sharedPreferencesHelper)
    }

    var authToken: String? = ""

    private fun setAuthToken(sharedPreferencesHelper:SharedPreferencesHelper) {
        CoroutineScope(Dispatchers.IO).launch {
            val  data = sharedPreferencesHelper.getAllUserData()
            authToken = data.token
        }

    }


}

