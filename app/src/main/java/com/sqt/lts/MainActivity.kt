package com.sqt.lts

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.example.lts.navigation.ApplicationNavigation
import com.sqt.lts.ui.theme.LtsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        

        setContent {

            LtsTheme {


                ApplicationNavigation(isLogin = true)
                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("Android")
//                }
            }
        }
    }



}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GreetingPreview() {
    LtsTheme {
        ApplicationNavigation(isLogin = false)
    }
}