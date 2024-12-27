package com.sqt.lts.ui.history.viewmodel
import androidx.compose.material3.NavigationRailItem
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lts.base.BaseCommonResponseModel
import com.example.lts.ui.tab.data.NavigationDrawer
import com.example.lts.utils.network.DataState
import com.sqt.lts.repository.HistoryAndWatchListRepository
import com.sqt.lts.ui.history.event.HistoryAndWatchListEvent
import com.sqt.lts.ui.history.request.HistoryAndWatchListRequestModel
import com.sqt.lts.ui.history.state.HistoryAndWatchListUiState
import com.sqt.lts.ui.history.ui_state.HistoryAndWatchListUpdateState
import com.sqt.lts.ui.trending.data.response.VideoAudio
import com.sqt.lts.utils.enums.AddAndRemoveWatchType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HistoryWatchListViewModel @Inject constructor(val historyAndWatchListRepository: HistoryAndWatchListRepository) : ViewModel(){

    private var currentPage = 1
    private var videoList = arrayListOf<VideoAudio?>()

    private val _historyAndWatchListAppResponse = MutableStateFlow(HistoryAndWatchListUiState())
    val historyAndWatchListAppResponse : StateFlow<HistoryAndWatchListUiState> = _historyAndWatchListAppResponse

    private val _addAndRemoveWatchListAppResponse = MutableSharedFlow<HistoryAndWatchListUpdateState>()
    val addAndRemoveWatchListAppResponse = _addAndRemoveWatchListAppResponse.asSharedFlow()


    fun onEvent(event: HistoryAndWatchListEvent){

        when(event){

            is HistoryAndWatchListEvent.HistoryEvent -> {
                getHistoryListData(event.historyAndWatchListRequestModel)
            }

            is HistoryAndWatchListEvent.WatchEvent -> {
                getWatchListData(event.historyAndWatchListRequestModel)
            }

            is HistoryAndWatchListEvent.AddWatchList -> {
                addWatchList(resourceId =event.resourceId, type = event.type)
            }

            is HistoryAndWatchListEvent.RemoveWatchList -> {
                removeWatchList(resourceId =event.resourceId, type = event.type)
            }
        }
    }

    private fun getWatchListData(historyAndWatchListRequestModel:HistoryAndWatchListRequestModel){


        if(historyAndWatchListRequestModel.isFirst == true){
            currentPage = 1
            videoList.clear()
        }

        if(historyAndWatchListRequestModel.isFirst == false && videoList.size <= (_historyAndWatchListAppResponse.value.totalRecord?:0)) return

        historyAndWatchListRequestModel.page = currentPage

        historyAndWatchListRepository.watchListData(historyAndWatchListRequestModel).onEach {

            when(it){

                is DataState.Error -> {
                    _historyAndWatchListAppResponse.value = _historyAndWatchListAppResponse.value.copy(isLoading = false, videoAudioList = videoList)
                }

                DataState.Loading -> {
                    _historyAndWatchListAppResponse.value = _historyAndWatchListAppResponse.value.copy(isLoading = true, videoAudioList = videoList)
                }

                is DataState.Success -> {
                    currentPage+=1
                    videoList.addAll(it.data?.historyList?:arrayListOf())
                    _historyAndWatchListAppResponse.value = _historyAndWatchListAppResponse.value.copy(isLoading = false, videoAudioList = videoList, totalRecord = it.data?.totalRecords)
                }

            }

        }.launchIn(viewModelScope)
    }

    private fun getHistoryListData(historyAndWatchListRequestModel:HistoryAndWatchListRequestModel){

        if(historyAndWatchListRequestModel.isFirst == true){
            currentPage = 1
            videoList.clear()
        }

        if(historyAndWatchListRequestModel.isFirst == false && videoList.size <= (_historyAndWatchListAppResponse.value.totalRecord?:0)) return

        historyAndWatchListRequestModel.page = currentPage

        historyAndWatchListRepository.historyListData(historyAndWatchListRequestModel).onEach {

            when(it){

                is DataState.Error -> {
                    _historyAndWatchListAppResponse.value = _historyAndWatchListAppResponse.value.copy(isLoading = false, videoAudioList = videoList)
                }

                DataState.Loading -> {
                    _historyAndWatchListAppResponse.value = _historyAndWatchListAppResponse.value.copy(isLoading = true, videoAudioList = videoList)
                }

                is DataState.Success -> {
                    currentPage+=1
                    videoList.addAll(it.data?.historyList?:arrayListOf())
                    _historyAndWatchListAppResponse.value = _historyAndWatchListAppResponse.value.copy(isLoading = false, videoAudioList = videoList, totalRecord = it.data?.totalRecords)
                }

            }

        }.launchIn(viewModelScope)

    }


    private fun removeWatchList(resourceId: Int?,type: NavigationDrawer?=null){

        historyAndWatchListRepository.removeWatchListData(resourceId = resourceId).onEach { dataState ->

            when(dataState){

                is DataState.Error -> {
                    _addAndRemoveWatchListAppResponse.emit(HistoryAndWatchListUpdateState(dataState = dataState, exception = dataState.exception, isLoading = false))
                }

                is DataState.Loading -> {
                    _addAndRemoveWatchListAppResponse.emit(HistoryAndWatchListUpdateState(dataState = dataState, isLoading = true))
                }
                is DataState.Success -> {
                    if(type == NavigationDrawer.WATCHLIST){
                        val arrayList = arrayListOf<VideoAudio?>()
                        _historyAndWatchListAppResponse.value.videoAudioList.forEach {
                            arrayList.add(it)
                        }
                        val index = arrayList.indexOfFirst { it?.resourceid == resourceId }
                        arrayList.removeAt(index)
                        _historyAndWatchListAppResponse.update { it.copy(videoAudioList = arrayList) }
                        _addAndRemoveWatchListAppResponse.emit(HistoryAndWatchListUpdateState(
                            dataState = dataState,
                            msg = dataState.message,
                            isLoading = true))
                    }else{
                        val list = _historyAndWatchListAppResponse.value.videoAudioList.map { data-> if(data?.resourceid == resourceId) data?.copy(isaddedinwatchlist = 0) else data}
                        _historyAndWatchListAppResponse.update { it.copy(videoAudioList = list) }
                        _addAndRemoveWatchListAppResponse.emit(HistoryAndWatchListUpdateState(
                            dataState = dataState,
                            msg = dataState.message,
                            isLoading = false))
//                        _addAndRemoveWatchListAppResponse.emit(BaseCommonResponseModel.Data(isLoading = false))
                    }

                }

            }

        }.launchIn(viewModelScope)

    }

    private fun addWatchList(resourceId: Int?,type: NavigationDrawer?=null){

        historyAndWatchListRepository.addWatchListData(resourceId = resourceId).onEach {

            when(it){

                is DataState.Error -> {
                    _addAndRemoveWatchListAppResponse.emit(HistoryAndWatchListUpdateState(dataState = it, exception = it.exception, isLoading = false))
//                    _addAndRemoveWatchListAppResponse.emit(BaseCommonResponseModel.Data(isLoading = false))
                }

                is DataState.Loading -> {
                    _addAndRemoveWatchListAppResponse.emit(HistoryAndWatchListUpdateState(dataState = it, isLoading = true))
//                    _addAndRemoveWatchListAppResponse.emit(BaseCommonResponseModel.Data(isLoading = true))
                }

                is DataState.Success -> {
                    val list = _historyAndWatchListAppResponse.value.videoAudioList.map { data-> if(data?.resourceid == resourceId) data?.copy(isaddedinwatchlist = 1) else data}
                    _historyAndWatchListAppResponse.update { it.copy(videoAudioList = list) }
                    _addAndRemoveWatchListAppResponse.emit(HistoryAndWatchListUpdateState(dataState = it, isLoading = false, msg = it.message))
//                    _addAndRemoveWatchListAppResponse.emit(BaseCommonResponseModel.Data(isLoading = false))
                }

            }
        }.launchIn(viewModelScope)
    }

}