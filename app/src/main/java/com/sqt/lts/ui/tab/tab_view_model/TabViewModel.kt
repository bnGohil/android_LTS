package com.example.lts.ui.tab.tab_view_model
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.viewModelScope
import com.example.lts.ui.tab.bottom_bar.BottomNavBarItem
import com.example.lts.ui.tab.event.TabEvent
import com.example.lts.ui.tab.repository.TabRepository
import com.example.lts.ui.tab.state.TabState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TabViewModel @Inject constructor(private val tabRepository: TabRepository) : ViewModel(){


    private val _tabState = MutableStateFlow(TabState())
    val tabState : StateFlow<TabState> = _tabState



    fun onEvent(event: TabEvent){

        when(event){
            is TabEvent.GetTabData -> {
                getTabListData()
            }
            is TabEvent.UpdateTabData -> {
                updateTabData(event.bottomNavBarItem)
            }
        }
    }

    private fun getTabListData(){

        viewModelScope.launch {
         val response = tabRepository.getTabListData()
         _tabState.value = _tabState.value.copy(tabList = response)
     }

    }

    private fun updateTabData(bottomNavBarItem: BottomNavBarItem){
        _tabState.value = _tabState.value.copy(selectedTab = bottomNavBarItem)
    }


}


//class TabViewModel : ViewModel() {
//
//
//
//
//    private val _updateBottomNavBarUiState = mutableStateOf<BottomNavBarItem>(BottomNavBarItem.home)
//    val updateBottomNavBarUiState: State<BottomNavBarItem> = _updateBottomNavBarUiState
//
//    private val _tabList = mutableListOf<BottomNavBarItem>()
//    val tabList : MutableList<BottomNavBarItem> = _tabList
//
//
////    private var _updateBottomNavBarUiState = MutableStateFlow<BottomNavBarItem>(BottomNavBarItem.home)
////    var updateBottomNavBarUiState = _updateBottomNavBarUiState
//
//
//    fun getTabList(isLogin: Boolean?=null){
//        when(isLogin){
//            true -> {
//            _tabList.add(BottomNavBarItem.home)
//            _tabList.add(BottomNavBarItem.categories)
//            _tabList.add(BottomNavBarItem.channels)
//            _tabList.add(BottomNavBarItem.trending)
//            _tabList.add(BottomNavBarItem.profile)
//            }
//            false -> {
//                _tabList.add(BottomNavBarItem.home)
//                _tabList.add(BottomNavBarItem.categories)
//                _tabList.add(BottomNavBarItem.channels)
//                _tabList.add(BottomNavBarItem.trending)
//            }
//            null -> TODO()
//        }
//    }
//
//    fun updateTabData(bottomNavBarItem: BottomNavBarItem?=null){
//        if(bottomNavBarItem == null) return
//        _updateBottomNavBarUiState.value = bottomNavBarItem
//    }
//
//
//}