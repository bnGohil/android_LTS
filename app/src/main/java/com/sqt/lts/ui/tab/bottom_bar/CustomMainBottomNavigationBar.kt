package com.example.lts.ui.tab.bottom_bar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.sqt.lts.ui.categories.event.CategoriesEvent
import com.example.lts.ui.tab.event.TabEvent
import com.example.lts.ui.tab.state.TabState
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.ui.theme.kCardBackgroundColor
import com.sqt.lts.ui.theme.kPrimaryColor
import com.example.lts.utils.scaleSize


@Composable
fun CustomMainBottomNavigationBar(
    onTabEvent: (TabEvent) -> Unit,
    onCategoryEvent:(CategoriesEvent) -> Unit,
    state: TabState? =null) {



    Column {

        Spacer(modifier = Modifier
            .height(1.dp.scaleSize())
            .fillMaxWidth()
            .background(kCardBackgroundColor))

        Spacer(modifier = Modifier.height(15.dp.scaleSize()))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            items(state?.tabList?:arrayListOf()){
                if (it == BottomNavBarItem.profile) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {

                            onTabEvent(TabEvent.UpdateTabData(it))

                            when(it){
                                BottomNavBarItem.categories -> {

                                }
                                BottomNavBarItem.channels -> {}
                                BottomNavBarItem.history -> {}
                                BottomNavBarItem.home -> {}
                                BottomNavBarItem.profile -> {

                                }
                                BottomNavBarItem.trending -> {

                                }
                            }



                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .border(
                                    border = BorderStroke(color = kPrimaryColor, width = 1.dp),
                                    CircleShape
                                )
                                .padding(5.dp.scaleSize())
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(37.dp.scaleSize())
                                    .clip(CircleShape),
                                painter = rememberAsyncImagePainter("https://miro.medium.com/v2/resize:fit:1400/format:webp/1*U4gZLnRtHEeJuc6tdVLwPw.png"),
                                contentDescription = it.itemName ?: "",
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp.scaleSize()))
                        if(state?.selectedTab == it){
                            Box(
                                modifier = Modifier
                                    .size(8.dp.scaleSize())
                                    .clip(CircleShape)
                                    .background(kPrimaryColor)
                            )
                        }
                    }

                } else {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {
                            onTabEvent(TabEvent.UpdateTabData(it))
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(kCardBackgroundColor)
                                .padding(10.dp.scaleSize())
                        ) {
                            Image(
                                modifier = Modifier.size(24.dp.scaleSize()),
                                painter = painterResource(id = it.itemImageID ?: 0),
                                contentDescription = it.itemName ?: ""
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp.scaleSize()))
                        if(state?.selectedTab == it){
                            Box(
                                modifier = Modifier
                                    .size(8.dp.scaleSize())
                                    .clip(CircleShape)
                                    .background(kPrimaryColor)
                            )
                        }

                    }
                }
            }
}
        Spacer(modifier = Modifier.height(15.dp.scaleSize()))
    }
}

@Preview(showBackground = true, backgroundColor = 1L)
@Composable
private fun CustomMainBottomNavigationBarPreview() {
    LtsTheme {
        CustomMainBottomNavigationBar(
            onTabEvent = {},
            onCategoryEvent = {},
            state = TabState(
                selectedTab = BottomNavBarItem.home,
                tabList = arrayListOf(
                    BottomNavBarItem.home,
                    BottomNavBarItem.history,
                    BottomNavBarItem.trending,
                    BottomNavBarItem.channels,
                    BottomNavBarItem.categories,
                )
            )
        )

    }
}

