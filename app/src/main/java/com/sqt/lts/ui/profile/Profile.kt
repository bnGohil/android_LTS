package com.example.lts.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sqt.lts.R
import com.example.lts.custom_component.CustomVideoComponent
import com.example.lts.navigation.navigation_view_model.NavigationViewModel
import com.example.lts.ui.sharedPreferences.sharedPreferences_view_model.SharedPreferencesViewModel
import com.example.lts.utils.extainstion.kLightTextColorW600FS20
import com.example.lts.utils.extainstion.kWhiteW500FS17
import com.example.lts.utils.scaleSize

@Composable
fun Profile(
    navHostController: NavHostController,

) {
    Column(modifier = Modifier
        .padding(horizontal = 10.dp.scaleSize(), vertical = 10.dp.scaleSize())
        .verticalScroll(rememberScrollState())) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier.size(70.dp.scaleSize()),
                painter = painterResource(id = R.drawable.dummy_categories),
                contentDescription = "dummy_categories"
            )
            Spacer(modifier = Modifier.width(10.dp.scaleSize()))
            Column(modifier = Modifier.weight(1F)) {
                Text(text = "Bhadresh Gohil", style = TextStyle.Default.kWhiteW500FS17())
                Text(
                    text = "Published on Oct 14, 2024",
                    style = TextStyle.Default.kLightTextColorW600FS20()
                )
            }
            Spacer(modifier = Modifier.width(10.dp.scaleSize()))
            Image(painter = painterResource(id = R.drawable.setting), contentDescription = "setting")
        }
        Spacer(modifier = Modifier.height(20.dp.scaleSize()))
        CustomVideoComponent(title = "History", description =  "See All")
        Spacer(modifier = Modifier.height(20.dp.scaleSize()))
        CustomVideoComponent(title = "Watchlist", description =  "See All")
        Spacer(modifier = Modifier.height(30.dp.scaleSize()))
    }
}

data class ProfileTabData(var name: String?=null,var id : Int?=null)