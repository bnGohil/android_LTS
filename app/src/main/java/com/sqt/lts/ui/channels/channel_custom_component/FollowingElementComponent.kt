package com.example.lts.ui.channels.channel_custom_component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sqt.lts.ui.channels.event.ChannelEvent
import com.sqt.lts.ui.channels.event.FollowingType
//import com.example.lts.navigation.route.AppRoutesName
import com.sqt.lts.navigation.route.FollowingDetailRoute
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.ui.theme.kLightTextColor
import com.sqt.lts.ui.theme.kPrimaryColor
import com.sqt.lts.ui.theme.kWhite
import com.example.lts.utils.extainstion.kPrimaryColorW400FS15
import com.example.lts.utils.extainstion.kWhiteW400FS13
import com.example.lts.utils.scaleSize
import com.sqt.lts.custom_component.CustomNetworkImageView
import com.sqt.lts.ui.channels.channel_custom_component.isChangeText
import com.sqt.lts.ui.channels.data.response.ChannelData
import com.sqt.lts.ui.channels.state.ChannelFollowingState

@Composable
fun FollowingElementComponent(
    channel: ChannelData? = null,
    onChannelEvent:(ChannelEvent) -> Unit,
    navigationHostController: NavHostController,
    channelDataState:ChannelFollowingState?=null,
) {
//    var following by remember { mutableStateOf<Boolean>(channel?.isfollowchannel == 1) }
    val isChannelLoading = channelDataState?.isLoading == true


    Column(modifier = Modifier
        .padding(5.dp)
        .clickable {
            navigationHostController.navigate(FollowingDetailRoute)
        }) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomNetworkImageView(
                imagePath = channel?.channelimgurl,
                modifier = Modifier.size(50.dp.scaleSize()).clip(CircleShape),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.width(20.dp.scaleSize()))
            Column(modifier = Modifier.weight(1F)) {
                Text(text = channel?.channelname ?: "", style = TextStyle.Default.kWhiteW400FS13())
                     Text(
                        text = channel?.followers?:"",
                        style = TextStyle.Default.kWhiteW400FS13()
                    )

            }
            Spacer(modifier = Modifier.width(20.dp.scaleSize()))

            OutlinedButton(
                colors = ButtonDefaults.buttonColors(containerColor = if (channel?.isfollowchannel == 0) kPrimaryColor else Color.Transparent),
                shape = RoundedCornerShape(10.dp.scaleSize()),
                border = if (channel?.isfollowchannel != 0) BorderStroke(
                    color = kPrimaryColor,
                    width = 1.dp.scaleSize()
                ) else null,
                onClick = {

                    if(!isChannelLoading){


                        when(channel?.isfollowchannel){

                            0-> {

                                onChannelEvent(ChannelEvent.FollowChannelEvent(channel.channelid?:0, followingType = FollowingType.FOLLOW))
                            }

                            1->{

                                onChannelEvent(ChannelEvent.UnFollowChannelEvent(channel.channelid?:0, followingType = FollowingType.UNFOLLOW))

                            }
                        }
                    }


                }) {
                Text(
                    text = isChangeText(channel?.isfollowchannel == 0),
                    style = TextStyle.Default.kPrimaryColorW400FS15().copy(color = kWhite)
                )
            }

        }
        Spacer(modifier = Modifier.height(10.dp.scaleSize()))
        Divider(
            Modifier
                .height(1.dp)
                .alpha(0.4F), color = kLightTextColor,)
    }
}

@Preview
@Composable
private fun FollowingElementComponentPreview() {
    LtsTheme {
        FollowingElementComponent(
            channel = ChannelData(
                following = false,
                channelname = "Bollywood Songs",
                followers = "10 Followers",
//                channelimgurl = "https://fastly.picsum.photos/id/765/536/354.jpg?hmac=KdMEeWclG6G7uEBwImE8VC-vX6j6B7b2NbtqQNF80R0"
            ),
            navigationHostController = rememberNavController(),
            onChannelEvent = {}
        )
    }
}