package com.example.lts.ui.dummy.repository

import android.content.Context
import com.example.lts.ui.dummy.Video
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.io.InputStreamReader
import javax.inject.Inject

class DummyRepository(val context: Context) {


   suspend fun loadJsonFromAssets() :List<Video>?{
        return try {
            val inputStream = context.assets.open("video_data.json")
            val reader = InputStreamReader(inputStream)
            val type = object : TypeToken<List<Video>>() {}.type
            Gson().fromJson<List<Video>>(reader, type)
        }catch (e:Exception){
            e.printStackTrace()
            null
        }


    }

}