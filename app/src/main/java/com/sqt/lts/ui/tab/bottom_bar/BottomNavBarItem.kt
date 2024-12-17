package com.example.lts.ui.tab.bottom_bar

import com.sqt.lts.R

sealed class BottomNavBarItem(val itemName: String?=null,val itemImageID:Int?=null){
    data object home : BottomNavBarItem(itemName = "Home", itemImageID = R.drawable.home)
    data object categories : BottomNavBarItem(itemName = "Categories", itemImageID = R.drawable.categories)
    data object trending : BottomNavBarItem(itemName = "Trending", itemImageID = R.drawable.trending)
    data object history : BottomNavBarItem(itemName = "History", itemImageID = R.drawable.history)
    data object profile : BottomNavBarItem(itemName = "Profile")
    data object channels : BottomNavBarItem(itemName = "Channel", itemImageID = R.drawable.channel)
}




object BottomNavBarItemObject{

    var bottomNavBarItemList = listOf(
        BottomNavBarItem.home,
        BottomNavBarItem.categories,
        BottomNavBarItem.trending,
        BottomNavBarItem.history,
        BottomNavBarItem.profile,
    )

}
