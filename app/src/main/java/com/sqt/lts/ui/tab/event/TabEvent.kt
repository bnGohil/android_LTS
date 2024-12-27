package com.example.lts.ui.tab.event

import com.example.lts.ui.tab.bottom_bar.BottomNavBarItem

sealed class TabEvent {
    data object GetTabData : TabEvent()

    data class UpdateTabData(var bottomNavBarItem: BottomNavBarItem) : TabEvent()

    data class GlobalSearchReq(var bottomNavBarItem: BottomNavBarItem?,val isSearch: Boolean?) : TabEvent()


}