package com.example.lts.ui.categories.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sqt.lts.ui.channels.event.ChannelEvent
import com.sqt.lts.ui.theme.kPrimaryColor
import com.sqt.lts.ui.theme.kSecondaryTextColor
import com.example.lts.utils.extainstion.kWhiteW400FS13
import com.example.lts.utils.scaleSize
import com.sqt.lts.ui.channels.state.SelectedChannel
import com.sqt.lts.ui.channels.state.SelectedChannelModel
import com.sqt.lts.ui.channels.state.SelectedChannelState
import com.sqt.lts.ui.theme.LtsTheme

@Composable
fun ChannelTabComponent(onChannelEvent:(ChannelEvent) -> Unit, selectedTab: SelectedChannel?=null, isLoading: Boolean?=false, onChangeTab:(SelectedChannel?)-> Unit) {

    val categoryTabList = arrayListOf<SelectedChannelModel>(
        SelectedChannelModel(
            name = "Popular Channel",
            selectedChannel = SelectedChannel.POPULAR_CHANNEL
        ),
        SelectedChannelModel(
            name = "Other Channel",
            selectedChannel = SelectedChannel.OTHER_CHANNEL
        )
    )

    Row(modifier = Modifier.fillMaxWidth()) {
        categoryTabList.map {
            Column(modifier = Modifier
                .weight(1F)
                .clickable {
                    if(isLoading == false){
                        onChangeTab(it.selectedChannel)
                        onChannelEvent(ChannelEvent.SelectedChannelTabData(
                            selectedChannelState = SelectedChannelState(selectedChannel = it.selectedChannel)
                        ))
//                        onCategoryEvent(CategoriesEvent.ClearCategories)
//                        onCategoryEvent(CategoriesEvent.GetAllCategoryData(displayLoginUserCategory = if(it.categoriesType == CategoryType.MY_CATEGORY) 1 else 0, isFirst = true))
                    }

                }) {
                Spacer(modifier = Modifier.height(10.dp.scaleSize()))
                Text(modifier = Modifier.fillMaxWidth(), text = it.name ?: "", textAlign = TextAlign.Center, style = TextStyle.Default.kWhiteW400FS13().copy(color = if(selectedTab == it.selectedChannel) kPrimaryColor else kSecondaryTextColor))
                Spacer(modifier = Modifier.height(15.dp.scaleSize()))
                Divider(modifier = Modifier
                    .height(1.dp.scaleSize())
                    .background(kSecondaryTextColor))
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 1L,)
@Composable
private fun CategoriesTabComponentPreview() {
    LtsTheme {
        ChannelTabComponent(
            onChannelEvent = {},
            onChangeTab = {},
            selectedTab = SelectedChannel.OTHER_CHANNEL
            )
    }
}