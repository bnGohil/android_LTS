package com.sqt.lts.navigation.route


import android.net.Uri
import com.example.lts.ui.tab.data.NavigationDrawer
import com.sqt.lts.utils.enums.ChannelUpdateNotUpdateType
import kotlinx.serialization.Serializable


//import kotlinx.serialization.Serializable

//import kotlinx.serialization.Serializable


@Serializable
data object SplashRoute
//
//@Serializable
//class LoginRoute
@Serializable
data object TabRoute
@Serializable
data object RegisterRoute
@Serializable
data object ForgotRoute
@Serializable
data object TrendingRoute
@Serializable
data class TrendingDetailRoute(var id: Int?=null)
@Serializable
data object HomeRoute
@Serializable
data object CategoriesRoute
@Serializable
data object CaAccountRoute
@Serializable
data object ProfileSettingRoute
@Serializable
data object FollowingRoute
@Serializable
data object FollowingDetailRoute
@Serializable
data class CreateChannelRoute(val channelUpdateNotUpdateType:ChannelUpdateNotUpdateType?=null,val channelId: Int?=null)
@Serializable
data object PostVideoRoute
@Serializable
data class PostSaveVideoRoute(val url : String ?= null)
@Serializable
data class HistoryAndWatchlistRoute(val title: String? = null,val navigationDrawer: NavigationDrawer?=null)
@Serializable
data class WebViewRoute(val url: String?= null,val title: String?=null)




