package com.example.lts.ui.tab.state

import com.example.lts.ui.tab.bottom_bar.BottomNavBarItem

data class TabState(
    var tabList : List<BottomNavBarItem> = arrayListOf(),
    var selectedTab : BottomNavBarItem ?= BottomNavBarItem.home
)