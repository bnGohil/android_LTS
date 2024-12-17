package com.example.lts.ui.tab.repository

import com.example.lts.ui.tab.bottom_bar.BottomNavBarItem
import javax.inject.Inject

class TabRepository @Inject constructor(){

    suspend fun getTabListData():ArrayList<BottomNavBarItem>{

        return  arrayListOf<BottomNavBarItem>(BottomNavBarItem.home, BottomNavBarItem.categories, BottomNavBarItem.channels, BottomNavBarItem.trending)

    }

}