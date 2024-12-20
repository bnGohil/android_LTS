package com.sqt.lts.ui.trending.trending_view_model

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sqt.lts.ui.trending.event.TrendingEvent
import com.example.lts.ui.trending.state.GetAllTrendingState
import com.example.lts.ui.trending.state.TrendingState
import com.example.lts.utils.ResponseData
import com.example.lts.utils.network.DataState
import com.sqt.lts.repository.TrendingRepository
import com.sqt.lts.ui.trending.data.request.TrendingRequestModel
import com.sqt.lts.ui.trending.data.response.PlayListItem
import com.sqt.lts.ui.trending.data.response.VideoAudio
import com.sqt.lts.ui.trending.state.TrendingVideoResourceUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class TrendingViewModel @Inject constructor(private val trendingRepository: TrendingRepository) : ViewModel() {


    private var job : Job?=null

    private val _trendingHomeState = MutableStateFlow(TrendingState())
    val trendingHomeState : StateFlow<TrendingState> = _trendingHomeState

    private val _trendingState = MutableStateFlow(TrendingState())
    val trendingState : StateFlow<TrendingState> = _trendingState

    private val _caAccountResourceData = MutableStateFlow(TrendingState())
    val caAccountResourceData : StateFlow<TrendingState> = _caAccountResourceData

    private val _trendingPlayListState = MutableStateFlow(TrendingState())
    val trendingPlayListState : StateFlow<TrendingState> = _trendingPlayListState

    private val _trendingAllState = MutableStateFlow(GetAllTrendingState())
    val trendingAllState : StateFlow<GetAllTrendingState> = _trendingAllState

    private val _resourceTrendingDetailState = MutableStateFlow(TrendingVideoResourceUiState())
    val resourceTrendingDetailState : StateFlow<TrendingVideoResourceUiState> = _resourceTrendingDetailState



    private var currentHomeTrendingPage = 1
    private var currentCaAccountResourcePage = 1
    private var currentTrendingPage = 1
    private var currentPlayListTrendingPage = 1

    private val videoAudioList = arrayListOf<VideoAudio?>()
    private val videoPlayAudioList = arrayListOf<VideoAudio?>()
    private val caeResourceList = arrayListOf<VideoAudio?>()

    private val _videoPlayList = mutableListOf<PlayListItem?>()
    val videoPlayList : MutableList<PlayListItem?> = _videoPlayList






     fun onEvent(event: TrendingEvent){
        when (event){

           is TrendingEvent.GetTrendingDataForHome -> {
                getTrendingHomeData(event.trendingRequestModel)
            }

            TrendingEvent.GetTrendingAllData -> {
                getAllTrendingData()
            }

            is TrendingEvent.GetTrendingData -> {
                getTrendingData(event.trendingRequestModel)
            }

            is TrendingEvent.GetResourceVideoDetail -> {
                getResourceDetailData(event.id)
            }

            is TrendingEvent.GetTrendingPlayListData -> {
                getPlayListTrendingData(event.trendingRequestModel)
            }

            is TrendingEvent.GetCaAccountResource -> {
                getCaAccountResourceData(event.trendingRequestModel)
            }
        }
    }


   private fun getTrendingHomeData(trendingRequestModel:TrendingRequestModel?){


       println("Video CategoryIds : ${trendingRequestModel?.categoryIds}")



       if(trendingRequestModel?.isFirst == true){
           currentHomeTrendingPage = 1
       }



       if(trendingRequestModel?.isFirst == false && ((trendingRequestModel.currentRecord ?: 0) >= (_trendingHomeState.value.totalRecord ?: 0))) return

       job?.cancel()

       job = trendingRepository.getResourceData(
           TrendingRequestModel(
           displayloginuseruploaded = trendingRequestModel?.displayloginuseruploaded,
           categoryIds = trendingRequestModel?.categoryIds,
           limit = trendingRequestModel?.limit,
           page = currentHomeTrendingPage
           )).onEach {

           when(it){

               is DataState.Error -> {
                   _trendingHomeState.emit(TrendingState(it, isLoading = false, videoAudioList = arrayListOf()))
               }

               is DataState.Loading -> {
                   _trendingHomeState.emit(TrendingState(it, isLoading = true, videoAudioList = arrayListOf()))
               }

               is DataState.Success -> {
                   _trendingHomeState.emit(TrendingState(it, isLoading = false,
                       videoAudioList = it.data?.videoAudioList,
                       totalRecord = it.data?.totalRecords,
                       isFirst = trendingRequestModel?.isFirst))
                   currentHomeTrendingPage += 1
               }

           }

       }.launchIn(viewModelScope)

   }



    @SuppressLint("SuspiciousIndentation")
    private fun getTrendingData(trendingRequestModel:TrendingRequestModel?){

        if(trendingRequestModel?.isFirst == true){
            currentTrendingPage = 1
            videoAudioList.clear()
        }

        if(trendingRequestModel?.isFirst == false && ((trendingRequestModel.currentRecord ?: 0) >= (_trendingState.value.totalRecord ?: 0))) return

           trendingRequestModel?.page = currentTrendingPage

        trendingRepository.getResourceData(trendingRequestModel=trendingRequestModel).onEach {

            when(it){

                is DataState.Error -> {
                    _trendingState.emit(TrendingState(it, isLoading = false, videoAudioList = videoAudioList))
                }

                is DataState.Loading -> {
                    _trendingState.emit(TrendingState(it, isLoading = true, videoAudioList = videoAudioList))
                }

                is DataState.Success -> {


                    videoAudioList.addAll(it.data?.videoAudioList?: arrayListOf())

                    _trendingState.emit(TrendingState(it, isLoading = false,
                        videoAudioList = videoAudioList,
                        totalRecord = it.data?.totalRecords,
                        isFirst = trendingRequestModel?.isFirst))
                    currentTrendingPage += 1
                }

            }

        }.launchIn(viewModelScope)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun getPlayListTrendingData(trendingRequestModel:TrendingRequestModel?){

        if(trendingRequestModel?.isFirst == true){
            currentPlayListTrendingPage = 1
            videoPlayAudioList.clear()
        }

        if(trendingRequestModel?.isFirst == false && ((trendingRequestModel.currentRecord ?: 0) >= (_trendingPlayListState.value.totalRecord ?: 0))) return

           trendingRequestModel?.page = currentPlayListTrendingPage

        trendingRepository.getResourceData(trendingRequestModel=trendingRequestModel).onEach {

            when(it){

                is DataState.Error -> {
                    _trendingPlayListState.emit(TrendingState(it, isLoading = false, videoAudioList = videoPlayAudioList))
                }

                is DataState.Loading -> {
                    _trendingPlayListState.emit(TrendingState(it, isLoading = true, videoAudioList = videoPlayAudioList))
                }

                is DataState.Success -> {

                    videoPlayAudioList.addAll(it.data?.videoAudioList?: arrayListOf())

                    it.data?.videoAudioList?.forEach{
                        _videoPlayList.add(PlayListItem(id = it?.resourceid?:0, thumb = it?.thumbimgurl))
                    }

                    _trendingPlayListState.emit(TrendingState(it, isLoading = false,
                        videoAudioList = videoPlayAudioList,
                        totalRecord = it.data?.totalRecords,
                        isFirst = trendingRequestModel?.isFirst))
                    currentPlayListTrendingPage += 1

                }

            }

        }.launchIn(viewModelScope)
    }




    private fun getAllTrendingData(){
        _trendingAllState.value = _trendingAllState.value.copy(ResponseData.LOADING)
    }

    private fun getResourceDetailData(id: Int?=null){
        trendingRepository.getResourceDetailData(id).onEach {
            when(it){
                is DataState.Error -> {
                    _resourceTrendingDetailState.value = _resourceTrendingDetailState.value.copy(isLoading = false)
                }

                DataState.Loading -> {
                    _resourceTrendingDetailState.value = _resourceTrendingDetailState.value.copy(isLoading = true)
                }

                is DataState.Success -> {
                    if(it.data?.resourceurl != null){
                        _videoPlayList.add(0,PlayListItem(id = it.data.resourceid?:0, thumb = it.data.thumbimgurl))
                    }
                    _resourceTrendingDetailState.value = _resourceTrendingDetailState.value.copy(isLoading = false, data = it.data)
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun getCaAccountResourceData(trendingRequestModel:TrendingRequestModel?){

        if(trendingRequestModel?.isFirst == true){
            caeResourceList.clear()
            currentCaAccountResourcePage = 1
            _caAccountResourceData.value.videoAudioList?.clear()
        }



        if(trendingRequestModel?.isFirst == false && ((caeResourceList.size) >= (_caAccountResourceData.value.totalRecord?:0))) return


        trendingRepository.getResourceData(TrendingRequestModel(
            limit = trendingRequestModel?.limit,
            page = currentCaAccountResourcePage,
            sortDirection = trendingRequestModel?.sortDirection,
            sortColumn = trendingRequestModel?.sortColumn,
            categoryIds = trendingRequestModel?.categoryIds,
            channelId = trendingRequestModel?.channelId,
            mediaType = trendingRequestModel?.mediaType,
            exceptResourceIds = trendingRequestModel?.exceptResourceIds,
            displayloginuseruploaded = trendingRequestModel?.displayloginuseruploaded,
            isFirst = trendingRequestModel?.isFirst

        )).onEach {

            when(it){

                is DataState.Error -> {
                    _caAccountResourceData.emit(TrendingState(it, isLoading = false, videoAudioList = caeResourceList))
                }

                is DataState.Loading -> {
                    _caAccountResourceData.emit(TrendingState(it, isLoading = true, videoAudioList = caeResourceList))
                }

                is DataState.Success -> {
                    caeResourceList.addAll(it.data?.videoAudioList?:arrayListOf())
                    _caAccountResourceData.emit(TrendingState(it, isLoading = false,
                        videoAudioList = caeResourceList,
                        totalRecord = it.data?.totalRecords,
                        isFirst = trendingRequestModel?.isFirst))
                    currentCaAccountResourcePage += 1
                }

            }

        }.launchIn(viewModelScope)

    }

}