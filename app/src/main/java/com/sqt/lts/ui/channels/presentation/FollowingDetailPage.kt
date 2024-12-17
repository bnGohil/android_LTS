package com.example.lts.ui.channels.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sqt.lts.R
import com.example.lts.custom_component.CustomTopBar
import com.example.lts.navigation.navigation_view_model.NavigationViewModel
import com.sqt.lts.ui.channels.channel_custom_component.isChangeText
import com.sqt.lts.ui.channels.channel_view_model.ChannelViewModel
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.ui.theme.kBackGround
import com.sqt.lts.ui.theme.kPrimaryColor
import com.sqt.lts.ui.theme.kWhite
import com.example.lts.utils.extainstion.kPrimaryColorW400FS15
import com.example.lts.utils.extainstion.kWhiteW400FS13
import com.example.lts.utils.extainstion.kWhiteW500FS17
import com.example.lts.utils.scaleSize
import com.sqt.lts.ui.trending.trending_view_model.TrendingViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FollowingDetailPage(naviController: NavHostController,navigationViewModel: NavigationViewModel,
                        channelViewModel: ChannelViewModel,trendingViewModel:TrendingViewModel
                        ) {

//    val state by trendingViewModel.trendingAllState.collectAsState()

    var following by remember { mutableStateOf<Boolean>(false) }

    Scaffold(containerColor = kBackGround,
        topBar = {
            CustomTopBar(navHostController = naviController, title = "Video / Audio")
        }
        ) {
        paddingValues -> LazyColumn(modifier = Modifier
        .padding(paddingValues)
        .padding(horizontal = 15.dp.scaleSize())) {
            item {
                Spacer(modifier = Modifier.height(10.dp.scaleSize()))
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.dummy_categories),
                        contentDescription = "",
                        modifier = Modifier.size(
                            70.dp.scaleSize()
                        ),
                    )
                    Spacer(modifier = Modifier.width(20.dp.scaleSize()))
                    Column {
                        Text(text = "Bollywood Songs", style = TextStyle.Default.kWhiteW500FS17())
                        Spacer(modifier = Modifier.height(5.dp.scaleSize()))
                        Text(text = "Published On Oct 10, 2024 * 3 Followers * 0 Video * 0 Audio", style = TextStyle.Default.kWhiteW500FS17())
                        Spacer(modifier = Modifier.height(5.dp.scaleSize()))
                        Row {
                            OutlinedButton(
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (following) kPrimaryColor else Color.Transparent
                                ),
                                shape = RoundedCornerShape(10.dp.scaleSize()),
                                border = if (!following) BorderStroke(
                                    color = kPrimaryColor,
                                    width = 1.dp.scaleSize()
                                ) else null,
                                onClick = {
                                    following = !following
                                    //                        following.toggle()
                                }) {
                                Text(
                                    text = isChangeText(following),
                                    style = TextStyle.Default.kPrimaryColorW400FS15().copy(
                                        color = if (!following) kPrimaryColor else kWhite
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            OutlinedButton(
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                shape = RoundedCornerShape(10.dp.scaleSize()),
                                border =  BorderStroke(
                                    color = kPrimaryColor,
                                    width = 1.dp.scaleSize()
                                ),
                                onClick = {

                                    //                        following.toggle()
                                }) {
                                Text(
                                    text = "Ask the senior",
                                    style = TextStyle.Default.kPrimaryColorW400FS15().copy(
                                        color =  kPrimaryColor
                                    )
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp.scaleSize()))
                Text(text = "Video / Audio", style = TextStyle.Default.kWhiteW400FS13())
            }
//            items(state.trendingList){
//                VideoComponent(navigationViewModel = navigationViewModel, naviController = naviController, trendingItem = it)
//            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun FollowingDetailPagePreview() {
    LtsTheme {
        FollowingDetailPage(
            naviController = rememberNavController(),
            navigationViewModel = viewModel(),
            channelViewModel = viewModel(),
            trendingViewModel = viewModel()
        )
    }
}