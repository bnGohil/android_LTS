package com.example.lts.ui.splash


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sqt.lts.ui.theme.kBackGround
import com.sqt.lts.ui.theme.kPrimaryColor

@Composable
fun SplashPage() {
    Scaffold(
        containerColor = kBackGround,
    ) {
        paddingValues -> Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = kPrimaryColor
        )
    }
    }
}