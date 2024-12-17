package com.example.lts.ui.tab.data

import com.sqt.lts.R

data class ProfileTabModelData(val name:String?=null,val id:Int?=null,val navigationDrawer:NavigationDrawer?=null)


object ProfileTabData{
    val listData = arrayListOf<ProfileTabModelData>(
        ProfileTabModelData(name = "CA Account",id = R.drawable.myacc,NavigationDrawer.CA_ACCOUNT),
        ProfileTabModelData(name = "Profile & Setting",id = R.drawable.setting,NavigationDrawer.PROFILE_SETTING),
        ProfileTabModelData(name = "Following",id = R.drawable.following,NavigationDrawer.FOLLOWING),
        ProfileTabModelData(name = "Add Channel",id = R.drawable.add_channel,NavigationDrawer.CREATE_CHANNEL),
        ProfileTabModelData(name = "History",id = R.drawable.history,NavigationDrawer.HISTORY),
        ProfileTabModelData(name = "Watchlist",id = R.drawable.watchlist,NavigationDrawer.WATCHLIST),
        ProfileTabModelData(name = "Privacy - Policy",id = R.drawable.privacypolicy,NavigationDrawer.PRIVACY_POLICY),
        ProfileTabModelData(name = "Term-Condition",id = R.drawable.termcondition,NavigationDrawer.TERM_CONDITION),
        ProfileTabModelData(name = "Logout",id = R.drawable.logout,NavigationDrawer.LOGOUT)
    )
}

enum class NavigationDrawer{
    CA_ACCOUNT,PROFILE_SETTING,FOLLOWING,CREATE_CHANNEL,HISTORY,WATCHLIST,PRIVACY_POLICY,TERM_CONDITION,LOGOUT
}