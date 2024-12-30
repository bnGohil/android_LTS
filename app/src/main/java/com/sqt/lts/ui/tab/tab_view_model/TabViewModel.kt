package com.example.lts.ui.tab.tab_view_model
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.viewModelScope
import com.example.lts.ui.tab.bottom_bar.BottomNavBarItem
import com.example.lts.ui.tab.event.TabEvent
import com.example.lts.ui.tab.state.TabState
import com.example.lts.utils.network.DataState
import com.sqt.lts.repository.TabRepository
import com.sqt.lts.ui.channels.data.response.ChannelData
import com.sqt.lts.ui.home.enums.HomeDataEnums
import com.sqt.lts.ui.home.homeUiState.HomeResourceAndChannelJoinModel
import com.sqt.lts.ui.home.homeUiState.HomeResourceAndChannelUiState
import com.sqt.lts.ui.tab.state.SelectedTabAndSearch
import com.sqt.lts.ui.trending.data.response.VideoAudio
import com.sqt.lts.utils.enums.ApiResponseType
import com.sqt.lts.utils.enums.GlobalSearchORHomeData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class TabViewModel @Inject constructor(private val tabRepository: TabRepository) : ViewModel(){


    private val _tabState = MutableStateFlow(TabState())
    val tabState : StateFlow<TabState> = _tabState


    private val _selectedAndSearchValue = Channel<SelectedTabAndSearch>()
    val selectedAndSearchValue = _selectedAndSearchValue.receiveAsFlow()

    private var globalSearchJob : Job?=null

    private val _homeGlobalSearchAppResponse = MutableStateFlow(HomeResourceAndChannelUiState())
    val homeGlobalSearchAppResponse = _homeGlobalSearchAppResponse.asStateFlow()


    private var homeDataList  = arrayListOf<HomeResourceAndChannelJoinModel?>()




    fun onEvent(event: TabEvent){

        when(event){
            is TabEvent.GetTabData -> {
                getTabListData()
            }
            is TabEvent.UpdateTabData -> {
                updateTabData(event.bottomNavBarItem)
            }

            is TabEvent.GlobalSearchReq -> {
                updateAndShowHideData(bottomNavBarItem = event.bottomNavBarItem, isSearch = event.isSearch)
            }

            is TabEvent.GlobalHomeSearchData -> {
                getHomeGlobalSearchData(event.searchQry)
            }
        }
    }

    private fun getTabListData(){
        tabRepository.getTabListData().onEach {
            _tabState.value = _tabState.value.copy(tabList = it)
        }.launchIn(viewModelScope)
    }



    private fun updateTabData(bottomNavBarItem: BottomNavBarItem){
        _tabState.value = _tabState.value.copy(selectedTab = bottomNavBarItem)
    }

    private fun updateAndShowHideData(bottomNavBarItem: BottomNavBarItem?,isSearch: Boolean?){
     viewModelScope.launch {
         _selectedAndSearchValue.send(SelectedTabAndSearch(isSearch = isSearch, selectedTab = bottomNavBarItem))
     }
    }

    private fun getHomeGlobalSearchData(searchQry: String){




        println("searchQry : $searchQry")

        globalSearchJob?.cancel()
        globalSearchJob = tabRepository.getGlobalSearchData(searchQry).onEach { dataState ->

            when(dataState){

                is DataState.Error -> {
                    homeDataList.clear()
                    _homeGlobalSearchAppResponse.value = _homeGlobalSearchAppResponse.value.copy(isLoading = false, homeDataList = arrayListOf(), apiResponseType = ApiResponseType.ERROR, typeForSelection = GlobalSearchORHomeData.GLOBAL_SEARCH)
                }

                DataState.Loading -> {
                    homeDataList.clear()
                    _homeGlobalSearchAppResponse.value = _homeGlobalSearchAppResponse.value.copy(isLoading = true, homeDataList = arrayListOf(), apiResponseType = ApiResponseType.LOADING,typeForSelection = GlobalSearchORHomeData.GLOBAL_SEARCH)
                }

                is DataState.Success -> {

                    if(searchQry.isEmpty()) {
                        homeDataList.clear()
                        _homeGlobalSearchAppResponse.update { it.copy(
                            homeDataList = homeDataList,
                            typeForSelection = GlobalSearchORHomeData.HOME_DATA,
                            isLoading = false
                        ) }

                    }else{
                        runBlocking {


                            val channelList = dataState.data?.filter { it?.type == "Channel"}

                            val videoAudioList = dataState.data?.filter { (it?.type == "Video" || it?.type == "Audio")}

                            withContext(Dispatchers.Default){
                                videoAudioList?.forEach {
                                    homeDataList.add(HomeResourceAndChannelJoinModel(homeDataEnums = HomeDataEnums.RESOURCE, videoItem = VideoAudio(
                                        resourceid = it?.id,
                                        thumbimgurl = it?.thumbimgurl,
                                        title = it?.title,
                                        resourcedurationinminute = it?.resourcedurationinminute,
                                        categoryname = it?.categoryname,
                                        longdetails = it?.description
                                    )))
                                }

                            }

                            withContext(Dispatchers.Default){


                                val channel = arrayListOf<ChannelData>()

                                if(channelList?.isNotEmpty() == true){
                                    channelList.forEach { channel.add(ChannelData(channelid = it?.id, channelname = it?.title, channelimgurl = it?.thumbimgurl)) }
                                }

                                if(videoAudioList?.isNotEmpty() == true && videoAudioList.size >= 2){
                                    homeDataList.add(2, HomeResourceAndChannelJoinModel(homeDataEnums = HomeDataEnums.CHANNEL, channelList = channel))
                                }else{
                                    homeDataList.add(videoAudioList?.size ?:0, HomeResourceAndChannelJoinModel(homeDataEnums = HomeDataEnums.CHANNEL, channelList = channel))
                                }

                            }
                        }
                        _homeGlobalSearchAppResponse.value = _homeGlobalSearchAppResponse.value.copy(isLoading = false, homeDataList = homeDataList, apiResponseType = ApiResponseType.SUCCESS,typeForSelection = GlobalSearchORHomeData.GLOBAL_SEARCH)
                    }


                }
            }
        }.launchIn(viewModelScope)
    }


}

