package com.sqt.lts.ui.history.event

import com.example.lts.ui.tab.bottom_bar.BottomNavBarItem
import com.example.lts.ui.tab.data.NavigationDrawer
import com.sqt.lts.ui.history.request.HistoryAndWatchListRequestModel
import com.sqt.lts.utils.enums.AddAndRemoveWatchType

sealed class HistoryAndWatchListEvent {
    data class HistoryEvent(val historyAndWatchListRequestModel:HistoryAndWatchListRequestModel): HistoryAndWatchListEvent()
    data class WatchEvent(val historyAndWatchListRequestModel:HistoryAndWatchListRequestModel): HistoryAndWatchListEvent()
    data class AddWatchList(val resourceId : Int?,val type: NavigationDrawer?=null): HistoryAndWatchListEvent()
    data class RemoveWatchList(val resourceId : Int?,val type: NavigationDrawer?=null): HistoryAndWatchListEvent()
}

