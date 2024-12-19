package com.example.lts.ui.tab

import CustomTabTopBar
import LoginRoute
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sqt.lts.navigation.route.CaAccountRoute
import com.sqt.lts.navigation.route.CreateChannelRoute
import com.sqt.lts.navigation.route.FollowingRoute
import com.sqt.lts.navigation.route.HistoryAndWatchlistRoute
//import com.sqt.lts.navigation.route.LoginRoute
import com.sqt.lts.navigation.route.PostVideoRoute
import com.sqt.lts.navigation.route.ProfileSettingRoute
import com.sqt.lts.navigation.route.TabRoute
import com.sqt.lts.navigation.route.WebViewRoute
import com.sqt.lts.ui.categories.Categories
import com.sqt.lts.ui.categories.event.CategoriesEvent
import com.sqt.lts.ui.categories.state.CategoriesState
import com.sqt.lts.ui.categories.state.CategoryType
import com.sqt.lts.ui.channels.event.ChannelEvent
import com.sqt.lts.ui.channels.presentation.Channels
import com.example.lts.ui.home.Home
import com.example.lts.ui.profile.Profile
import com.example.lts.ui.sharedPreferences.data.SaveLoginState
import com.example.lts.ui.sharedPreferences.event.SharedPreferencesEvents
import com.example.lts.ui.tab.bottom_bar.BottomNavBarItem
import com.example.lts.ui.tab.bottom_bar.CustomMainBottomNavigationBar
import com.example.lts.ui.tab.data.NavigationDrawer
import com.example.lts.ui.tab.data.ProfileTabData
import com.example.lts.ui.tab.event.TabEvent
import com.example.lts.ui.tab.state.TabState
import com.sqt.lts.ui.theme.kBackGround
import com.sqt.lts.ui.theme.kPrimaryColor
import com.sqt.lts.ui.trending.presentation.Trending
import com.example.lts.ui.trending.state.TrendingState
import com.example.lts.utils.extainstion.kPrimaryColorW400FS15
import com.example.lts.utils.extainstion.kPrimaryColorW500FS15
import com.example.lts.utils.extainstion.kWhiteW500FS17
import com.example.lts.utils.scaleSize
import com.sqt.lts.custom_component.CustomNetworkImageView
import com.sqt.lts.ui.channels.data.response.ChannelData
import com.sqt.lts.ui.channels.state.ChannelFollowingState
import com.sqt.lts.ui.channels.state.ChannelUiState
import com.sqt.lts.ui.home.enums.HomeDataEnums
import com.sqt.lts.ui.home.event.HomeEvent
import com.sqt.lts.ui.home.homeUiState.HomeResourceAndChannelJoinModel
import com.sqt.lts.ui.profile.state.UserDetailGetState
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.ui.trending.data.response.VideoAudio
import com.sqt.lts.ui.trending.event.TrendingEvent
import com.sqt.lts.utils.enums.ChannelUpdateNotUpdateType
import kotlinx.coroutines.launch

@SuppressLint("WrongNavigateRouteType")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TabPage(
    homeList: List<HomeResourceAndChannelJoinModel?>?=arrayListOf(),
    navHostController: NavHostController,
    onCategoryEvent:(CategoriesEvent) -> Unit,
    onHomeDataEvent:(HomeEvent) -> Unit,
    onChannelEvent:(ChannelEvent) -> Unit,
    onTrendingEvent:(TrendingEvent) -> Unit,
    categoriesHomeState: CategoriesState? =null,
    categoriesState: CategoriesState? =null,
    categoryForTrendingState: CategoriesState? =null,
    channelHomeUiState: ChannelUiState? =null,
    channelUiState: ChannelUiState? =null,
    userDetailUiState: UserDetailGetState? =null,
    trendingHomeState: TrendingState? =null,
    trendingState: TrendingState? =null,
    tabState: TabState? =null,
    onTabEvent: (TabEvent) -> Unit,
    saveLoginState: SaveLoginState?=null,
    selectedTab:CategoryType?=null,
    channelDataState:ChannelFollowingState?=null,
    onSharedPreferencesEvent:(SharedPreferencesEvents) -> Unit,

    ) {





    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    fun close(){
        scope.launch { drawerState.close() }
    }

    var openAlertDialog by remember { mutableStateOf(false) }





    if(openAlertDialog){
        AlertDialog(
            title = {
                    Text(text = "Logout")
            },
            text = {
                   Text(text = "Are you sure you want to logout?")
            },
            onDismissRequest = { openAlertDialog = false },
            dismissButton = {
                TextButton(onClick = { openAlertDialog = false }) {
                    Text("Dismiss")
                }
            },
            confirmButton = { TextButton(onClick = {
                openAlertDialog = false
                onSharedPreferencesEvent(SharedPreferencesEvents.Logout)
                navHostController.navigate(LoginRoute){
                    popUpTo<TabRoute>(){
                        inclusive = true
                    }
                }
                onSharedPreferencesEvent(SharedPreferencesEvents.CheckAuthentication(false))
            }) {
                Text("Confirm")
            } }
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState()),
                drawerContainerColor = kBackGround,
            ) {

                IconButton(
                    onClick = {
                        close()
                },
                     modifier = Modifier
                         .align(Alignment.End)
                         .size(25.dp.scaleSize())
                    ) { Icon(imageVector = Icons.Filled.Clear, contentDescription = "clear", tint = kPrimaryColor) }

                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(top = 50.dp.scaleSize())
                        .width(width = (LocalConfiguration.current.screenWidthDp.dp.scaleSize() / (1.5F).dp.scaleSize().value).scaleSize())
                ) {
                    CustomNetworkImageView(
                        imagePath = userDetailUiState?.data?.photourl,
                        modifier = Modifier.size(100.dp.scaleSize()).clip(CircleShape),
                        contentScale = ContentScale.FillBounds
                    )
                    Spacer(modifier = Modifier.height(15.dp.scaleSize()))
                    Text(text = userDetailUiState?.data?.displayname?:"", style = TextStyle.Default.kWhiteW500FS17(), maxLines = 1, overflow = TextOverflow.Ellipsis)
                }

                Spacer(modifier = Modifier.height(50.dp.scaleSize()))


                ProfileTabData.listData.map {


                    if(it.id != null && it.name != null){

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .padding(
                                    vertical = 15.dp.scaleSize(),
                                    horizontal = 20.dp.scaleSize()
                                )
                                .clickable {

                                    when (it.navigationDrawer) {

                                        NavigationDrawer.CA_ACCOUNT -> {
                                            navHostController.navigate(CaAccountRoute)
                                        }

                                        NavigationDrawer.PROFILE_SETTING -> {
                                            navHostController.navigate(ProfileSettingRoute)
                                        }

                                        NavigationDrawer.FOLLOWING -> {
                                            navHostController.navigate(FollowingRoute)
                                        }

                                        NavigationDrawer.CREATE_CHANNEL -> {
                                            navHostController.navigate(CreateChannelRoute(channelUpdateNotUpdateType = ChannelUpdateNotUpdateType.CHANNEL_NOT_UPDATE))
                                        }

                                        NavigationDrawer.HISTORY -> {
                                            navHostController.navigate(HistoryAndWatchlistRoute(title = "History", navigationDrawer = it.navigationDrawer))
                                        }

                                        NavigationDrawer.WATCHLIST -> {
                                            navHostController.navigate(HistoryAndWatchlistRoute(title = "Watchlist", navigationDrawer = it.navigationDrawer))
                                        }

                                        NavigationDrawer.PRIVACY_POLICY -> {
                                            navHostController.navigate(WebViewRoute(title = "Privacy-Policy", url = "https://qa.listentoseniors.com/privacy-policy"))
                                        }

                                        NavigationDrawer.TERM_CONDITION -> {
                                            navHostController.navigate(WebViewRoute(title = "Terms-Conditions", url = "https://qa.listentoseniors.com/terms-conditions"))
                                        }

                                        NavigationDrawer.LOGOUT -> { openAlertDialog = true }

                                        null -> TODO()
                                    }

                                    close()

                                }) {
                            Image(modifier = Modifier.size(25.dp.scaleSize()), painter = painterResource(id = it.id), contentDescription = it.name, colorFilter = ColorFilter.tint(kPrimaryColor, BlendMode.SrcIn))
                            Spacer(modifier = Modifier.width(20.dp.scaleSize()))
                            Text(text = it.name, style = TextStyle.Default.kPrimaryColorW500FS15())
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp.scaleSize()))

                OutlinedButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    border = BorderStroke(color = kPrimaryColor, width = 1.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = kBackGround),
                    shape = RectangleShape,
                    onClick = {
                        navHostController.navigate(PostVideoRoute){
                            close()
                        }
                    }) {
                    Text(text = "Post Video", style = TextStyle.Default.kPrimaryColorW400FS15())
                }

            }
        },
    ) {
        Scaffold(containerColor = kBackGround, topBar = {
                CustomTabTopBar(
                    userDetailUiState = userDetailUiState,
                    saveLoginState = saveLoginState,
                    tabState = tabState,
                    onClick = {

                        if (saveLoginState?.isLogin == true) {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        } },

                    navHostController = navHostController,

                )
                     },
       bottomBar = {CustomMainBottomNavigationBar(onTabEvent = onTabEvent, state = tabState, onCategoryEvent = onCategoryEvent)}) {
       paddingValues -> Column(modifier = Modifier.padding(paddingValues)) {
       ReturnScreens(
           saveLoginState = saveLoginState,
           categoriesState = categoriesState,
           userDetailUiState = userDetailUiState,
           channelDataState=channelDataState,
           selectedTab = selectedTab,
           categoryForTrendingState = categoryForTrendingState,
           channelHomeUiState = channelHomeUiState,
           bottomNavBarItem = tabState?.selectedTab,
           navController = navHostController,
           categoriesHomeState =categoriesHomeState,
           onCategoryEvent = onCategoryEvent,
           onHomeDataEvent = onHomeDataEvent,
           trendingHomeState = trendingHomeState,
           homeList = homeList,
           onChannelEvent = onChannelEvent,
           onTrendingEvent = onTrendingEvent,
           channelUiState = channelUiState,
           trendingState = trendingState
       )
   }
   }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(backgroundColor = 1L, showBackground = true)
@Composable
private fun TabPagePreview() {
    LtsTheme {
        TabPage(
            navHostController = rememberNavController(),
            onCategoryEvent = {},
            homeList = listOf<HomeResourceAndChannelJoinModel>(


                HomeResourceAndChannelJoinModel(
                    homeDataEnums = HomeDataEnums.RESOURCE,
                    videoItem = VideoAudio(
                        categoryname = "tests,tests,tests",
                        resourcedurationinminute = "10:00:34",
                        timedurationupload = "1 month ago"
                    )
                ),

                HomeResourceAndChannelJoinModel(
                    homeDataEnums = HomeDataEnums.RESOURCE,
                    videoItem = VideoAudio(
                        categoryname = "tests,tests,tests",
                        resourcedurationinminute = "10:00:34",
                        timedurationupload = "1 month ago"
                    )
                ),

                HomeResourceAndChannelJoinModel(
                    homeDataEnums = HomeDataEnums.CHANNEL,
                    channelList = listOf<ChannelData?>(
                        ChannelData(channelname = "My Channel 1", followers = "1 Followers"),
                        ChannelData(channelname = "My Channel 2", followers = "2 Followers"),
                        ChannelData(channelname = "My Channel 3" , followers = "3 Followers"),
                        ChannelData(channelname = "My Channel 4" , followers = "4 Followers"),

                        ),

                    ),

                HomeResourceAndChannelJoinModel(
                    homeDataEnums = HomeDataEnums.RESOURCE,
                    videoItem = VideoAudio(
                        categoryname = "tests,tests,tests",
                        resourcedurationinminute = "10:00:34",
                        timedurationupload = "1 month ago"
                    )

                ),


                ),
            tabState = TabState(
                selectedTab = BottomNavBarItem.home,
                tabList = arrayListOf(
                    BottomNavBarItem.home,
                    BottomNavBarItem.categories,
                    BottomNavBarItem.channels,
                    BottomNavBarItem.trending,
                )
            ),
            onHomeDataEvent = {},
            onChannelEvent = {},
            onTrendingEvent = {},
            onTabEvent = {},

        ) {}
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReturnScreens(
    saveLoginState: SaveLoginState? =null,
    userDetailUiState: UserDetailGetState? =null,
    onTrendingEvent:(TrendingEvent) -> Unit,
    selectedTab:CategoryType?=null,
    homeList: List<HomeResourceAndChannelJoinModel?>?=arrayListOf(),
    trendingHomeState: TrendingState? =null,
    trendingState: TrendingState? =null,
    categoryForTrendingState: CategoriesState? =null,
    channelHomeUiState: ChannelUiState? =null,
    channelUiState: ChannelUiState? =null,
    onHomeDataEvent:(HomeEvent) -> Unit,
    onChannelEvent:(ChannelEvent) -> Unit,
    bottomNavBarItem: BottomNavBarItem? = null,
    navController: NavHostController,
    onCategoryEvent:(CategoriesEvent) -> Unit,
    categoriesHomeState: CategoriesState? =null,
    categoriesState: CategoriesState? =null,
    channelDataState:ChannelFollowingState?=null,
    ){

    when(bottomNavBarItem){
        BottomNavBarItem.categories -> {
            Categories(
                navController = navController,
                onCategoryEvent = onCategoryEvent,
                selectedTab = selectedTab,
                categoriesState=categoriesState
            )
        }
        BottomNavBarItem.channels -> {
            Channels(navController = navController,onChannelEvent = onChannelEvent,channelUiState = channelUiState, channelFollowingState = channelDataState)
        }
        BottomNavBarItem.history -> {

        }
        BottomNavBarItem.home -> {
            Home(
                saveLoginState = saveLoginState,
                channelDataState=channelDataState,
                channelUiState = channelHomeUiState,
                onHomeDataEvent = onHomeDataEvent,
                categoriesState = categoriesHomeState,
                navController = navController,
                onCategoryEvent = onCategoryEvent,
                trendingState = trendingHomeState,
                homeList = homeList,
                onChannelEvent = onChannelEvent,
                onTrendingEvent = onTrendingEvent
            )
        }
        BottomNavBarItem.profile -> {
            Profile(
                navHostController = navController,)
        }
        BottomNavBarItem.trending -> {
            Trending(navController = navController, categoryForTrendingState = categoryForTrendingState, onCategoryEvent = onCategoryEvent,onTrendingEvent=onTrendingEvent, trendingState = trendingState)
        }
        null -> {}
    }

}