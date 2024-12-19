package com.sqt.lts.ui.post_video

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.lts.ui.post_video.VideoPlayer

@Composable
fun SaveVideoPostPage(
    navHostController: NavHostController,
    url:String?=null
) {

    println("RESPONSE ============== $url")

    Scaffold(

    ) {
        paddingValues -> Column(
            modifier = Modifier.padding(paddingValues)
        ) {

            if(url != null){ VideoPlayer(uri = Uri.parse(url)) }


    }
    }
}