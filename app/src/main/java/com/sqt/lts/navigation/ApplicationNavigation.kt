package com.example.lts.navigation

import LoginRoute
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.lts.enums.PagingLoadingType
import com.example.lts.navigation.navigation_view_model.NavigationViewModel
import com.sqt.lts.ui.auth.ForgotPassword
import com.example.lts.ui.auth.LoginPage
import com.example.lts.ui.auth.Register
import com.sqt.lts.ui.categories.viewModel.CategoryViewModel
//import com.sqt.lts.navigation.route.LoginRoute
import com.example.lts.ui.auth.auth_view_model.AuthenticationViewModel
import com.example.lts.ui.auth.event.AuthenticationEvent
import com.example.lts.ui.categories.data.request.GetCategoryRequestModel
import com.example.lts.ui.categories.data.ui_state.CategoryUiState
import com.sqt.lts.ui.categories.event.CategoriesEvent
import com.sqt.lts.ui.channels.channel_view_model.ChannelViewModel
import com.sqt.lts.ui.channels.event.ChannelEvent
import com.sqt.lts.ui.channels.presentation.CreateChannelsPage
import com.example.lts.ui.channels.presentation.FollowingDetailPage
import com.example.lts.ui.channels.presentation.FollowingPage
import com.example.lts.ui.history.HistoryScreen
import com.example.lts.ui.post_video.PostVideoPage
import com.example.lts.ui.profile.CaAccountPage
import com.example.lts.ui.profile.ProfileSettingPage
import com.example.lts.ui.sharedPreferences.sharedPreferences_view_model.SharedPreferencesViewModel
import com.example.lts.ui.splash.SplashPage
import com.example.lts.ui.tab.TabPage
import com.example.lts.ui.tab.data.NavigationDrawer
import com.example.lts.ui.tab.event.TabEvent
import com.example.lts.ui.tab.tab_view_model.TabViewModel
import com.sqt.lts.ui.trending.event.TrendingEvent
import com.example.lts.ui.trending.presentation.TrendingVideoDetail
import com.example.lts.ui.webview.WebViewPage
import com.sqt.lts.navigation.route.CaAccountRoute
import com.sqt.lts.navigation.route.CreateChannelRoute
import com.sqt.lts.navigation.route.FollowingDetailRoute
import com.sqt.lts.navigation.route.FollowingRoute
import com.sqt.lts.navigation.route.ForgotRoute
import com.sqt.lts.navigation.route.HistoryAndWatchlistRoute
import com.sqt.lts.navigation.route.PostSaveVideoRoute
import com.sqt.lts.navigation.route.PostVideoRoute
import com.sqt.lts.navigation.route.ProfileSettingRoute
import com.sqt.lts.navigation.route.RegisterRoute
import com.sqt.lts.navigation.route.SplashRoute
import com.sqt.lts.navigation.route.TabRoute
import com.sqt.lts.navigation.route.TrendingDetailRoute
import com.sqt.lts.navigation.route.WebViewRoute
import com.sqt.lts.ui.channels.data.request.ChannelRequestModel
import com.sqt.lts.ui.history.event.HistoryAndWatchListEvent
import com.sqt.lts.ui.history.request.HistoryAndWatchListRequestModel
import com.sqt.lts.ui.history.viewmodel.HistoryWatchListViewModel
import com.sqt.lts.ui.home.view_model.HomeViewModel
import com.sqt.lts.ui.post_video.SaveVideoPostPage
import com.sqt.lts.ui.profile.event.ProfileEvent
import com.sqt.lts.ui.profile.viewmodel.SettingViewModel
import com.sqt.lts.ui.trending.data.request.TrendingRequestModel
import com.sqt.lts.ui.trending.trending_view_model.TrendingViewModel


@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("StateFlowValueCalledInComposition", "RestrictedApi", "SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ApplicationNavigation(isLogin: Boolean?=null){






    val navController = rememberNavController()

    val tabViewModel = hiltViewModel<TabViewModel>()

    val settingViewModel = hiltViewModel<SettingViewModel>()

    val navigationViewModel: NavigationViewModel = viewModel()

//    val categoriesViewModel : CategoryViewModel = viewModel()

//    val sharedPreferencesViewModel : SharedPreferencesViewModel = viewModel()



//    val authenticationViewModel : AuthenticationViewModel = viewModel()

//    val channelViewModel : ChannelViewModel = viewModel()





    val authenticationViewModel = hiltViewModel<AuthenticationViewModel>()

    val sharedPreferencesViewModel = hiltViewModel<SharedPreferencesViewModel>()

    val categoriesViewModel = hiltViewModel<CategoryViewModel>()

    val trendingViewModel = hiltViewModel<TrendingViewModel>()

    val homeProvider = viewModel<HomeViewModel>()

    val channelViewModel = hiltViewModel<ChannelViewModel>()

    val historyAndWatchViewModel = hiltViewModel<HistoryWatchListViewModel>()


    val loginResponseState by sharedPreferencesViewModel.isLoginState.collectAsStateWithLifecycle(initialValue = null)





    NavHost(
        builder = {

            composable<LoginRoute> {
                val loginUserResponseModel by authenticationViewModel.loginState.collectAsStateWithLifecycle(initialValue = null)
                LoginPage(
                    navController = navController,
                    onAuthenticationEvent = authenticationViewModel::onEvent,
                    onSharedPreferencesEvent = sharedPreferencesViewModel::onEvent,
                    loginUserResponseModel = loginUserResponseModel)

            }

            composable<SplashRoute> {
                SplashPage()
            }

            composable<TabRoute> {



                val saveLoginState = sharedPreferencesViewModel.isLoginState.collectAsStateWithLifecycle(initialValue = null)
                val channelDataState = channelViewModel.getHomeChannelResponse.collectAsStateWithLifecycle(initialValue = null)
                val getChannelResponse = channelViewModel.getChannelResponse.collectAsStateWithLifecycle(initialValue = null)
                val categoryHomeState = categoriesViewModel.categoryHomeState.collectAsStateWithLifecycle(initialValue = null)
                val categoryForTrendingState = categoriesViewModel.categoryForTrendingState.collectAsStateWithLifecycle(initialValue = null)
                val categoryDataState = categoriesViewModel.categoryDataState.collectAsStateWithLifecycle(initialValue = null)
                val trendingHomeState = trendingViewModel.trendingHomeState.collectAsStateWithLifecycle(initialValue = null)
                val trendingState = trendingViewModel.trendingState.collectAsStateWithLifecycle(initialValue = null)
                val homeUiState = homeProvider.homeUiState.collectAsStateWithLifecycle(initialValue = null)
                val tabState = tabViewModel.tabState.collectAsStateWithLifecycle(initialValue = null)
                val selectedTab = categoriesViewModel.selectedTab.value
                val channelUiState = channelViewModel.followUnFollowAppResponse.collectAsStateWithLifecycle(null)
                val userDetailUiState = settingViewModel.userDetailAppResponse.collectAsStateWithLifecycle(null)





                LaunchedEffect(tabViewModel) {
                    tabViewModel.onEvent(TabEvent.GetTabData)
                }






                LaunchedEffect(key1 = categoriesViewModel) {
                    categoriesViewModel.onEvent(
                        CategoriesEvent.GetCategoryData(
                            categoryUiState = CategoryUiState(
                                pagingLoadingType = PagingLoadingType.SHIMMER,
                                getCategoryRequestModel = GetCategoryRequestModel(
                                    sortColumn = "",
                                    displayLoginUserCategory = if(loginResponseState?.isLogin == false) 1 else 0,
                                    page = 1,
                                    limit = 100,
                                    sortDirection = "desc"
                                )
                            )
                        )
                    )

                    categoriesViewModel.onEvent(CategoriesEvent.CategoryAllSelected)
                }


                LaunchedEffect(settingViewModel) {
                    settingViewModel.onEvent(ProfileEvent.UserDetailGetEvent)
                }



                if(homeProvider.channelList.isEmpty()){
                    LaunchedEffect(key1 = channelViewModel) {
                        channelViewModel.onEvent(ChannelEvent.GetHomeChannelData(
                            channelRequestModel = ChannelRequestModel(
                                isFirst = true,
                                limit = 10,
                                page = 1,
                                sortColumn = "trending",
                                sortDirection = "desc",
                                categoryIds ="",
                                exceptChannelIds = "",
                                myCreatedChannel = 0,
                                myFollowingChannel = 0
                            )
                        ))
                    }
                }


                if(homeProvider.videoList.isEmpty()){
                    LaunchedEffect(key1 = trendingViewModel) {
                        trendingViewModel.onEvent(TrendingEvent.GetTrendingDataForHome(
                            trendingRequestModel = TrendingRequestModel(
                                isFirst = true,
                                page = 1,
                                limit = 3,
                            )
                        ))
                    }
                }

                TabPage(
                    userDetailUiState = userDetailUiState.value,
                    channelDataState = channelUiState.value,
                    channelHomeUiState = channelDataState.value,
                    categoriesHomeState = categoryHomeState.value,
                    saveLoginState = saveLoginState.value,
                    onSharedPreferencesEvent = sharedPreferencesViewModel::onEvent,
                    navHostController = navController,
                    onCategoryEvent = categoriesViewModel::onEvent,
                    onHomeDataEvent = homeProvider::onEvent,
                    trendingHomeState = trendingHomeState.value,
                    homeList = homeUiState.value?.homeDataList,
                    onChannelEvent=channelViewModel::onEvent,
                    onTrendingEvent = trendingViewModel::onEvent,
                    onTabEvent = tabViewModel::onEvent,
                    tabState = tabState.value,
                    selectedTab = selectedTab,
                    categoriesState = categoryDataState.value,
                    channelUiState = getChannelResponse.value,
                    categoryForTrendingState = categoryForTrendingState.value,
                    trendingState = trendingState.value,
                )
            }

            composable<RegisterRoute> {



                val value by authenticationViewModel.getCountryList.collectAsStateWithLifecycle()

                val createUserResponse by authenticationViewModel.createUserState.collectAsStateWithLifecycle(initialValue = null)



                Register(
                    navController = navController,
                    authenticationViewModel = authenticationViewModel::onEvent,
                    countryDataState = value,
                    createUserResponseModel = createUserResponse
                )
            }

            composable<PostVideoRoute> {

                val getChannelForPostVideoResponse = channelViewModel.getChannelForPostVideoResponse.collectAsStateWithLifecycle(null)

                val categoriesForPostVideoAppResponse = categoriesViewModel.categoriesForPostVideoAppResponse.collectAsStateWithLifecycle(null)

                PostVideoPage(
                    navHostController = navController,
                    kChannelFunction = channelViewModel::onEvent,
                    channelUiState = getChannelForPostVideoResponse.value,
                    kCategoriesFunction=categoriesViewModel::onEvent,
                    categoriesState=categoriesForPostVideoAppResponse.value
                )
            }

            composable<PostSaveVideoRoute> {

                val argument = it.toRoute<PostSaveVideoRoute>()

                SaveVideoPostPage(navHostController = navController,url = argument.url)
            }

            composable<ForgotRoute>(){
                val forgotPassword by authenticationViewModel.forgotPasswordState.collectAsStateWithLifecycle(initialValue = null)
                ForgotPassword(
                    navController =navController,
                    onAuthenticationEvent = authenticationViewModel::onEvent,
                    forgotData = forgotPassword
                )
            }

            composable<TrendingDetailRoute>(){


                val argument = it.toRoute<TrendingDetailRoute>()

                val trendingVideoResourceUiState = trendingViewModel.resourceTrendingDetailState.collectAsStateWithLifecycle(null)
                val trendingVideoResourcePlayListUiState = trendingViewModel.trendingPlayListState.collectAsStateWithLifecycle(null)
                val channelUiState = channelViewModel.followUnFollowAppResponse.collectAsStateWithLifecycle(null)

                TrendingVideoDetail(
                    id = argument.id,
                    channelDataState = channelUiState.value,
                    list = trendingViewModel.videoPlayList.toList(),
                    onTrendingEvent = trendingViewModel::onEvent,
                    trendingVideoResourceUiState = trendingVideoResourceUiState.value,
                    trendingPlayListState = trendingVideoResourcePlayListUiState.value,
                    navController = navController,
                    onChannelEvent = channelViewModel::onEvent
                )
            }


            composable<CaAccountRoute>(){

                val caAccountResourceData = trendingViewModel.caAccountResourceData.collectAsStateWithLifecycle(initialValue = null)
                val categoryCaAccountState = categoriesViewModel.categoryCaAccountState.collectAsStateWithLifecycle(initialValue = null)
                val getCaAccountChannelResponse = channelViewModel.getCaAccountChannelResponse.collectAsStateWithLifecycle(initialValue = null)


                CaAccountPage(
                    navHostController = navController,
                    onCategoryEvent = categoriesViewModel::onEvent,
                    onChannelEvent = channelViewModel::onEvent,
                    onTrendingEvent = trendingViewModel::onEvent,
                    caAccountResourceData = caAccountResourceData.value,
                    categoriesState = categoryCaAccountState.value,
                    channelUiState = getCaAccountChannelResponse.value,
                    onHomeEvent = {}
                )
            }

            composable<ProfileSettingRoute>(){


                LaunchedEffect(Unit) {
                    authenticationViewModel.onEvent(AuthenticationEvent.GetCountryList)
                }

                val userDetailUiState = settingViewModel.userDetailAppResponse.collectAsStateWithLifecycle(null)

                val updateUserDetail = settingViewModel.updateUserDetail.collectAsStateWithLifecycle(null)

                val getCountryUiState = authenticationViewModel.getCountryList.collectAsStateWithLifecycle(null)

                ProfileSettingPage(
                    navHostController = navController,
                    onSettingEvent = settingViewModel::onEvent,
                    userDetailGetState = userDetailUiState.value,
                    countryUiState = getCountryUiState.value,
                    updateDetail = updateUserDetail.value
                    )
            }

            composable<FollowingRoute>(){

                LaunchedEffect(Unit) {
                    channelViewModel.onEvent(ChannelEvent.GetFollowingChannelData(
                        channelRequestModel = ChannelRequestModel(
                            limit = 15,
                            isFirst = true,
                            sortDirection = "desc",
                            sortColumn = "",
                            exceptChannelIds = "",
                            myCreatedChannel = 0,
                            myFollowingChannel = 0,
                        )
                    ))
                }



                val getFollowingChannelResponse = channelViewModel.getFollowingChannelResponse.collectAsStateWithLifecycle(null)

                FollowingPage(navigationHostController = navController, onChannelEvent = channelViewModel::onEvent, channelUiState = getFollowingChannelResponse.value)

            }
            composable<FollowingDetailRoute>(){
                FollowingDetailPage(navController, trendingViewModel = trendingViewModel, navigationViewModel = navigationViewModel, channelViewModel =  channelViewModel)
            }
            composable<CreateChannelRoute>(){
                val createChannelAppResponse = channelViewModel.createChannelAppResponse.collectAsStateWithLifecycle(null)
                val channelDetailAppResponse = channelViewModel.channelDetailAppResponse.collectAsStateWithLifecycle(null)
                val updateChannelAppResponse = channelViewModel.updateChannelAppResponse.collectAsStateWithLifecycle(null)
                var argument = it.toRoute<CreateChannelRoute>()
                CreateChannelsPage(
                    createChannelRoute = argument,
                    navHostController=navController,
                    onChannel = channelViewModel::onEvent,
                    channelDetailUiState = channelDetailAppResponse.value,
                    updateChannelData = updateChannelAppResponse.value,
                    baseCommonResponseModel = createChannelAppResponse.value)
            }
            composable<HistoryAndWatchlistRoute> {

                val argument = it.toRoute<HistoryAndWatchlistRoute>()

                val historyAndWatchListUiState = historyAndWatchViewModel.historyAndWatchListAppResponse.collectAsStateWithLifecycle(null)

                if(argument.navigationDrawer == NavigationDrawer.HISTORY){

                    LaunchedEffect(Unit) {
                        historyAndWatchViewModel.onEvent(HistoryAndWatchListEvent.HistoryEvent(
                            historyAndWatchListRequestModel = HistoryAndWatchListRequestModel(
                                limit = 10, sortcolumn = "date", days = 30, isFirst = true, sortdirection = "desc")
                        )
                        )
                    }

                }else if(argument.navigationDrawer == NavigationDrawer.WATCHLIST){
                    LaunchedEffect(Unit) {
                        historyAndWatchViewModel.onEvent(HistoryAndWatchListEvent.WatchEvent(
                            historyAndWatchListRequestModel = HistoryAndWatchListRequestModel(
                                limit = 10,
                                sortcolumn = "date",
                                isFirst = true,
                                sortdirection = "desc"
                            )
                        ))
                    }


                }


                HistoryScreen(

                    navHostController = navController,data = argument, onHistoryAndWatchListEvent = historyAndWatchViewModel::onEvent, historyAndWatchListUiState = historyAndWatchListUiState.value)

            }

            composable<WebViewRoute> {

                val argument = it.toRoute<WebViewRoute>()

                WebViewPage(argument,navController)
            }
        },
        navController = navController,
        startDestination = if(loginResponseState?.isLogin == null) SplashRoute else if(loginResponseState?.isLogin == true) TabRoute else LoginRoute)







//    if(loginResponseState?.isLogin == true) TabRoute else LoginRoute

//    NavHost(
//        navController = navController,
//        startDestination = LoginRoute,
//        builder = {
//            composable<LoginRoute>{
//                Column {
//                    Text("Bhadresh")
//                }
//            }
//        }
//    )

    /*NavHost(
        navController = navController,
        startDestination = if(loginResponseState?.isLogin == null) "kSplashPage" else if(loginResponseState?.isLogin == true) "kTabPage" else "kLoginPage",
        builder = {

            composable(route = "kLoginPage") {
                val loginUserResponseModel by authenticationViewModel.loginState.collectAsStateWithLifecycle(initialValue = null)
                LoginPage(
                    navController = navController,
                    onAuthenticationEvent = authenticationViewModel::onEvent,
                    onSharedPreferencesEvent = sharedPreferencesViewModel::onEvent,
                    loginUserResponseModel = loginUserResponseModel)
            }

            composable("KForgotPage"){
            val forgotPassword by authenticationViewModel.forgotPasswordState.collectAsStateWithLifecycle(initialValue = null)
            ForgotPassword(
                navController =navController,
                onAuthenticationEvent = authenticationViewModel::onEvent,
                forgotData = forgotPassword
            )
        }

            composable(route = "kTabPage") {



            val saveLoginState = sharedPreferencesViewModel.isLoginState.collectAsStateWithLifecycle(initialValue = null)


            LaunchedEffect(key1 = categoriesViewModel) {
                categoriesViewModel.onEvent(
                    CategoriesEvent.GetCategoryData(
                        categoryUiState = CategoryUiState(
                            pagingLoadingType = PagingLoadingType.SHIMMER,
                            getCategoryRequestModel = GetCategoryRequestModel(
                                sortColumn = "",
                                displayLoginUserCategory = 1,
                                page = 1,
                                limit = 100,
                                sortDirection = "desc"
                            )
                        )
                    )
                )
                categoriesViewModel.onEvent(CategoriesEvent.CategoryAllSelected)
            }

            val categoriesState = categoriesViewModel.categoryState.collectAsStateWithLifecycle(initialValue = null)

            TabPage(
                categoriesState = categoriesState.value,
                saveLoginState = saveLoginState.value,
                onSharedPreferencesEvent = sharedPreferencesViewModel::onEvent,
                navHostController = navController,
                tabViewModel = tabViewModel,
                categoriesViewModel = categoriesViewModel,
                trendingViewModel = trendingViewModel,
                channelViewModel = channelViewModel,
                navigationViewModel = navigationViewModel,
                onCategoryEvent = categoriesViewModel::onEvent
            )
        }

            composable(route = "kSplashPage") { SplashPage() }

            composable("KRegisterPage") {

            val value by authenticationViewModel.getCountryList.collectAsStateWithLifecycle()

            val createUserResponse by authenticationViewModel.createUserState.collectAsStateWithLifecycle(initialValue = null)

                Register(
                navController = navController,
                authenticationViewModel = authenticationViewModel::onEvent,
                countryDataState = value,
                createUserResponseModel = createUserResponse
            )
        }

            composable("KTrendingPage"){
            TrendingVideoDetail(
                navHostController =navController,
                trendingViewModel = trendingViewModel

            )
        }





















//            composable<Test>{
//                Column {  }
//            }



//            composable<LoginRoute> {
//                val loginUserResponseModel by authenticationViewModel.loginState.collectAsStateWithLifecycle(initialValue = null)
//                LoginPage(
//                    navController = navController,
//                    onAuthenticationEvent = authenticationViewModel::onEvent,
//                    onSharedPreferencesEvent = sharedPreferencesViewModel::onEvent,
//                    loginUserResponseModel = loginUserResponseModel)
//
//        }
//
//       composable<SplashRoute> {
//           SplashPage()
//       }
//
//        composable<TabRoute> {
//
//
//
//            val saveLoginState = sharedPreferencesViewModel.isLoginState.collectAsStateWithLifecycle(initialValue = null)
//
//
//            LaunchedEffect(key1 = categoriesViewModel) {
//                categoriesViewModel.onEvent(
//                    CategoriesEvent.GetCategoryData(
//                        categoryUiState = CategoryUiState(
//                            pagingLoadingType = PagingLoadingType.SHIMMER,
//                            getCategoryRequestModel = GetCategoryRequestModel(
//                                sortColumn = "",
//                                displayLoginUserCategory = 1,
//                                page = 1,
//                                limit = 100,
//                                sortDirection = "desc"
//                            )
//                        )
//                    )
//                )
//                categoriesViewModel.onEvent(CategoriesEvent.CategoryAllSelected)
//            }
//
//            val categoriesState = categoriesViewModel.categoryState.collectAsStateWithLifecycle(initialValue = null)
//
//            TabPage(
//                categoriesState = categoriesState.value,
//                saveLoginState = saveLoginState.value,
//                onSharedPreferencesEvent = sharedPreferencesViewModel::onEvent,
//                navHostController = navController,
//                tabViewModel = tabViewModel,
//                categoriesViewModel = categoriesViewModel,
//                trendingViewModel = trendingViewModel,
//                channelViewModel = channelViewModel,
//                navigationViewModel = navigationViewModel,
//                onCategoryEvent = categoriesViewModel::onEvent
//            )
//        }
//
//        composable<RegisterRoute> {
//
//
//
//            val value by authenticationViewModel.getCountryList.collectAsStateWithLifecycle()
//
//            val createUserResponse by authenticationViewModel.createUserState.collectAsStateWithLifecycle(initialValue = null)
//
//
//
//            Register(
//                navController = navController,
//                authenticationViewModel = authenticationViewModel::onEvent,
//                countryDataState = value,
//                createUserResponseModel = createUserResponse
//            )
//        }
//
//            composable<PostVideoRoute> {
//                PostVideoPage(
//                    navHostController = navController
//                )
//            }
//
//        composable<ForgotRoute>(){
//            val forgotPassword by authenticationViewModel.forgotPasswordState.collectAsStateWithLifecycle(initialValue = null)
//            ForgotPassword(
//                navController =navController,
//                onAuthenticationEvent = authenticationViewModel::onEvent,
//                forgotData = forgotPassword
//            )
//        }
//
//        composable<TrendingDetailRoute>(){
//            TrendingVideoDetail(
//                navHostController =navController,
//                trendingViewModel = trendingViewModel
//
//            )
//        }
//
//        composable<HomeRoute> {
//
//            val categoriesState = categoriesViewModel.categoryState.collectAsStateWithLifecycle(initialValue = null)
//
//
//
//            Home(
//                navigationViewModel = navigationViewModel,
//                channelViewModel = channelViewModel,
//                navController = navController,
//                categoriesViewModel = categoriesViewModel,
//                trendingViewModel = trendingViewModel,
////                categoriesState = categoriesState.value,
//                onCategoryEvent = categoriesViewModel::onEvent,
//            )
//        }
//
//        composable<ChannelRoute>(){
//            Channels(
//                navController=navController,
//                channelViewModel=channelViewModel
//            )
//        }
//
//        composable<CategoriesRoute>(){
//            Categories(
//                navController=navController,
//                categoriesViewModel=categoriesViewModel
//            )
//        }
//
//        composable<TrendingRoute>(){
//            Trending(
//                navController=navController,
//                categoriesViewModel=categoriesViewModel,
//                trendingViewModel =trendingViewModel,
//                channelViewModel = channelViewModel,
//                navigationViewModel = navigationViewModel
//            )
//        }
//
//            composable<CaAccountRoute>(){
//                CaAccountOPage(
//                    navHostController = navController,
//                    trendingViewModel = trendingViewModel,
//                    navigationViewModel = navigationViewModel,
//                    categoriesViewModel,channelViewModel
//                )
//            }
//
//            composable<ProfileSettingRoute>(){
//                ProfileSettingPage(navHostController = navController)
//            }
//
//            composable<FollowingRoute>(){
//                FollowingPage(navigationHostController = navController)
//            }
//            composable<FollowingDetailRoute>(){
//                FollowingDetailPage(navController, trendingViewModel = trendingViewModel, navigationViewModel = navigationViewModel, channelViewModel =  channelViewModel)
//            }
//            composable<CreateChannelRoute>(){
//                CreateChannelsPage(navController)
//            }
//            composable<HistoryAndWatchlistRoute> {
//
//                val argument = it.toRoute<HistoryAndWatchlistRoute>()
//
//                HistoryScreen(navHostController = navController,data = argument,channelViewModel,navigationViewModel,trendingViewModel)
//
//            }
//
//            composable<WebViewRoute> {
//
//                val argument = it.toRoute<WebViewRoute>()
//
//                WebViewPage(argument,navController)
//            }
        })*/

//    NavHost(
//            navController = navController,
//    startDestination = LoginRoute,
//    builder = {
//
//
////            composable(route = AppRoutesName.kTabRoute){
////                DummyAndDemoForComponent(dummyViewModel)
////            }
//
//        composable<LoginRoute>(){
//            LoginPage(
//                navController = navController,
//                sharedPreferencesViewModel = sharedPreferencesViewModel,
//                navigationViewModel = navigationViewModel,
//                tabViewModel = tabViewModel
//            )
//        }
//
//        composable<TabRoute>(){
//            TabPage(navHostController = navController,
//                tabViewModel = tabViewModel,
//                categoriesViewModel = categoriesViewModel,
//                sharedPreferencesViewModel = sharedPreferencesViewModel,
//                trendingViewModel = trendingViewModel,
//                channelViewModel = channelViewModel,
//                navigationViewModel = navigationViewModel
//            )
//        }
//
//        composable<RegisterRoute>(){
//            Register(
//                navController=navController
//            )
//        }
//
//        composable<ForgotRoute>(){
//            ForgotPassword(
//                navController=navController
//            )
//        }
//
//        composable(route = AppRoutesName.kTrendingDetailRoute){
//            TrendingVideoDetail(
//                navHostController =navController,
//                trendingViewModel = trendingViewModel
//
//            )
//        }
//
//        composable(route = AppRoutesName.kHomeRoute){
//            Home(
//                navigationViewModel = navigationViewModel,
//                channelViewModel = channelViewModel,
//                navController = navController,
//                categoriesViewModel = categoriesViewModel,
//                trendingViewModel = trendingViewModel
//            )
//        }
//
//        composable(route = AppRoutesName.kChannelsRoute){
//            Channels(
//                navController=navController,
//                channelViewModel=channelViewModel
//            )
//        }
//
//        composable(route = AppRoutesName.kCategoriesRoute){
//            Categories(
//                navController=navController,
//                categoriesViewModel=categoriesViewModel
//            )
//        }
//
//        composable(route = AppRoutesName.kTrendingRoute){
//            Trending(
//                navController=navController,
//                categoriesViewModel=categoriesViewModel,
//                trendingViewModel =trendingViewModel,
//                channelViewModel = channelViewModel,
//                navigationViewModel = navigationViewModel
//            )
//        }
//
//        composable(route = AppRoutesName.kProfileRoute){
//            Profile(
//                navHostController = navController,
//                sharedPreferencesViewModel = sharedPreferencesViewModel,
//                navigationViewModel = navigationViewModel
//            )
//        }
//
//        composable(route = AppRoutesName.kCaAccountRoute){
//            CaAccountOPage(navHostController = navController, trendingViewModel = trendingViewModel, navigationViewModel = navigationViewModel,categoriesViewModel,channelViewModel)
//        }
//
//        composable(route = AppRoutesName.kProfileSettingRoute){
//            ProfileSettingPage(navHostController = navController)
//        }
//
//        composable(route = AppRoutesName.kFollowingRoute){
//            FollowingPage(navigationHostController = navController)
//        }
//        composable(route = AppRoutesName.kFollowingDetailRoute){
//            FollowingDetailPage(navController, trendingViewModel = trendingViewModel, navigationViewModel = navigationViewModel, channelViewModel =  channelViewModel)
//        }
//        composable(route = AppRoutesName.kCreateChannelRoute){
//            CreateChannelsPage(navController)
//        }
//        composable(route = AppRoutesName.kHistoryRoute+"type={type}", arguments = arrayListOf(
//            navArgument("type"){
//
//            }
//        )){
//            HistoryScreen(navController)
//        }
//
//        composable(route = AppRoutesName.kWatchlistRoute){
//            WatchlistScreen(navController)
//        }
//
//
//
//
//
//
//
//
//    })




}

