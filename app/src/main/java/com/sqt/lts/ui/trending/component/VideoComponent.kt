package com.example.lts.ui.trending.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.sqt.lts.R
import com.example.lts.navigation.navigation_view_model.NavigationViewModel
import com.example.lts.ui.dummy.ClickVideo
//import com.example.lts.navigation.route.AppRoutesName
import com.sqt.lts.navigation.route.TrendingDetailRoute
import com.sqt.lts.ui.theme.kCardBackgroundColor
import com.example.lts.ui.trending.data.TrendingItemResponseData
import com.example.lts.utils.extainstion.kLightTextColorW500FS15
import com.example.lts.utils.extainstion.kPrimaryColorW500FS15
import com.example.lts.utils.extainstion.kWhiteW600FS17
import com.example.lts.utils.scaleSize
import com.sqt.lts.custom_component.CustomNetworkImageView
import com.sqt.lts.ui.home.homeUiState.HomeResourceAndChannelUiState
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.ui.theme.kWhite
import com.sqt.lts.ui.trending.data.response.VideoAudio
import com.sqt.lts.utils.enums.GlobalSearchORHomeData
import java.nio.file.WatchEvent

@Composable
fun VideoComponent(
    naviController:NavHostController,
    onClick: () -> Unit,
    onWatchClick: (VideoAudio?) -> Unit,
    homeResourceAndChannelUiState: HomeResourceAndChannelUiState?=null,
    trendingItem: VideoAudio?=null) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            onClick()
        }
        .padding(vertical = 10.dp)
        .background(kCardBackgroundColor)) {
        Box {
            CustomNetworkImageView(
                imagePath = trendingItem?.thumbimgurl,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp.scaleSize()),
                contentScale = ContentScale.FillBounds)

            Box(modifier = Modifier
                .padding(10.dp.scaleSize())
                .background(Color.Red)
                .padding(horizontal = 10.dp.scaleSize(), vertical = 5.dp.scaleSize())
                .align(Alignment.BottomEnd)) {
                Text(text = trendingItem?.resourcedurationinminute?:"", style = TextStyle.Default.kWhiteW600FS17())
            }
        }
        Column(modifier = Modifier.padding(horizontal = 10.dp.scaleSize())) {
            Spacer(modifier = Modifier.height(10.dp.scaleSize()))
            Text(text = trendingItem?.categoryname?:"", style = TextStyle.Default.kWhiteW600FS17(), minLines = 1, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(10.dp.scaleSize()))
            if(homeResourceAndChannelUiState?.typeForSelection != GlobalSearchORHomeData.GLOBAL_SEARCH){
                Row() {
                    Row(modifier = Modifier.weight(1F), verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "${trendingItem?.views ?: "0"} Views", style = TextStyle.Default.kLightTextColorW500FS15())
                        Spacer(modifier = Modifier.width(width = 5.dp.scaleSize()))
                        Image(painter = painterResource(id = R.drawable.datetime_calender), contentDescription = "",modifier = Modifier.size(20.dp.scaleSize()))
                        Text(text = trendingItem?.timedurationupload?:"", style = TextStyle.Default.kLightTextColorW500FS15())
                    }

                    Row {
                        Image(painter = painterResource(id = R.drawable.start_video), contentDescription = "", modifier = Modifier.size(24.dp.scaleSize()))
                        Spacer(modifier = Modifier.width(width = 5.dp.scaleSize()))
                        Image(
                            painter = painterResource(id = if(trendingItem?.isaddedinwatchlist == 1) R.drawable.bookmark else R.drawable.favorite),
                            colorFilter = ColorFilter.tint(kWhite, blendMode = BlendMode.SrcIn),
                            contentDescription = "",modifier = Modifier.size(24.dp.scaleSize()).clickable{
                                onWatchClick(trendingItem)
                            }
                        )
                        Spacer(modifier = Modifier.width(width = 10.dp.scaleSize()))
                    }
                }
                Spacer(modifier = Modifier.height(10.dp.scaleSize()))
            }

        }
    }
}

@Preview
@Composable
private fun VideoComponentPreview() {
    LtsTheme {
        VideoComponent(
            naviController = rememberNavController(),
            onClick = {},
            onWatchClick = {},
            trendingItem = VideoAudio(
                categoryname = "tests,tests,tests",
                resourcedurationinminute = "10:00:34",
                timedurationupload = "1 month ago"
            )
        )
    }
}