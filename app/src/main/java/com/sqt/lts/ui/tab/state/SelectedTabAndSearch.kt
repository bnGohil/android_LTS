package com.sqt.lts.ui.tab.state

import com.example.lts.ui.tab.bottom_bar.BottomNavBarItem

data class SelectedTabAndSearch(
    var selectedTab : BottomNavBarItem?= BottomNavBarItem.home,
    var isSearch: Boolean?=false
)
