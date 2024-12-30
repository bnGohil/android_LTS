package com.sqt.lts.ui.channels.channel_custom_component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.sqt.lts.ui.channels.event.ChannelEvent
import com.sqt.lts.ui.channels.event.FollowingType
import com.sqt.lts.R
import com.sqt.lts.ui.theme.kCardBackgroundColor
import com.sqt.lts.ui.theme.kPrimaryColor
import com.sqt.lts.ui.theme.kWhite
import com.example.lts.utils.extainstion.kLightTextColorW600FS20
import com.example.lts.utils.extainstion.kPrimaryColorW400FS15
import com.example.lts.utils.extainstion.kWhiteW500FS17
import com.example.lts.utils.network.DataState
import com.example.lts.utils.scaleSize
import com.sqt.lts.ui.channels.data.response.ChannelData
import com.sqt.lts.ui.channels.state.ChannelFollowingState
import com.sqt.lts.ui.home.event.HomeEvent
import com.sqt.lts.ui.home.homeUiState.HomeResourceAndChannelUiState
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.utils.enums.GlobalSearchORHomeData


@Composable
fun ChannelElementComponent(channel: ChannelData? = null,
                            isUpdateStatus: IsUpdateStatus?=null,
                            onChannelEvent:(ChannelEvent) -> Unit,
                            onHomeDataEvent:(HomeEvent) -> Unit,
                            channelDataState:ChannelFollowingState?=null,
                            onUpdateClick: () -> Unit,
                            homeResourceAndChannelUiState: HomeResourceAndChannelUiState?=null,
                            ) {






    Box(modifier = Modifier
        .padding(horizontal = 5.dp.scaleSize())
        .background(kCardBackgroundColor)
        .padding(20.dp.scaleSize())
    ){




        val isChannelLoading = channelDataState?.isLoading == true

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier
                    .size(100.dp.scaleSize())
                    .clip(CircleShape),
                painter = if(channel?.channelimgurl != "" && channel?.channelimgurl != null) rememberAsyncImagePainter(channel.channelimgurl) else painterResource(id = R.drawable.dummy_categories),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                )
            Spacer(modifier = Modifier.height(10.dp.scaleSize()))
            Text(text = channel?.channelname?:"", style = TextStyle.Default.kWhiteW500FS17())
            Spacer(modifier = Modifier.height(10.dp.scaleSize()))
            if(homeResourceAndChannelUiState?.typeForSelection != GlobalSearchORHomeData.GLOBAL_SEARCH){
                Text(text = "${channel?.followers ?: "0"} ", style = TextStyle.Default.kLightTextColorW600FS20())
                Spacer(modifier = Modifier.height(10.dp.scaleSize()))
                if(isUpdateStatus == IsUpdateStatus.UPDATE){
                    OutlinedButton(
                        colors = ButtonDefaults.buttonColors(containerColor = kPrimaryColor),
                        shape = RoundedCornerShape(10.dp.scaleSize()),
                        border = null,
                        onClick = onUpdateClick,
                        content = {
                            Text("Update",style = TextStyle.Default.kPrimaryColorW400FS15().copy(color = kWhite))
                        }
                    )
                }else {
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
            }




//            OutlinedButton(
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = if(channelData?.following == true) kPrimaryColor else Color.Transparent
//                ),
//                shape = RoundedCornerShape(10.dp.scaleSize()),
//                border = if(channelData?.following == false)  BorderStroke(
//                    color = kPrimaryColor,
//                    width = 1.dp.scaleSize()
//                ) else null,
//                onClick = {
//                    if(channelData == null) return@OutlinedButton
//                }) {
//                Text(text = isChangeText(channelData?.following?:false), style = TextStyle.Default.kPrimaryColorW400FS15().copy(
//                    color = if(channelData?.following == false) kPrimaryColor else kWhite
//                ))
//            }
        }
            

    }

}

fun isChangeText(isSelected: Boolean) : String{
    return when(isSelected){
        true -> "Follow"
        false -> "UnFollow"
    }
}


@Preview
@Composable
private fun ChannelElementComponentPreview() {
    LtsTheme {
        ChannelElementComponent(
            channel = ChannelData(
                channelname = "TEST"
            ),
            onChannelEvent = {},
            onHomeDataEvent = {},
            onUpdateClick = {}
//            onHomeDataEvent = {}
        )
    }
}

enum class IsUpdateStatus{UPDATE,NOT_UPDATE}