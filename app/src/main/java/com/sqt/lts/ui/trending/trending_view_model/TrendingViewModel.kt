package com.sqt.lts.ui.trending.trending_view_model

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lts.base.BaseCommonResponseModel
import com.sqt.lts.ui.trending.event.TrendingEvent
import com.example.lts.ui.trending.state.GetAllTrendingState
import com.example.lts.ui.trending.state.TrendingState
import com.example.lts.utils.ResponseData
import com.example.lts.utils.network.DataState
import com.sqt.lts.repository.TrendingRepository
import com.sqt.lts.ui.trending.data.request.SharedTrendingRequestModel
import com.sqt.lts.ui.trending.data.request.TrendingRequestModel
import com.sqt.lts.ui.trending.data.response.PlayListItem
import com.sqt.lts.ui.trending.data.response.TrendingLikeAndDisLikeModel
import com.sqt.lts.ui.trending.data.response.VideoAudio
import com.sqt.lts.ui.trending.state.LikeAndDislikeUiState
import com.sqt.lts.ui.trending.state.TrendingVideoResourceUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class TrendingViewModel @Inject constructor(private val trendingRepository: TrendingRepository) : ViewModel() {


    private var getTrendingDataForHomeJob : Job?=null

    private var getTrendingDataForTrendingJob : Job?=null

    private var getTrendingDataForChannelJob : Job?=null

    private val _trendingHomeState = MutableStateFlow(TrendingState())
    val trendingHomeState : StateFlow<TrendingState> = _trendingHomeState

    private val _trendingState = MutableStateFlow(TrendingState())
    val trendingState : StateFlow<TrendingState> = _trendingState

    private val _trendingChannelState = MutableStateFlow(TrendingState())
    val trendingChannelState : StateFlow<TrendingState> = _trendingChannelState

    private val _caAccountResourceData = MutableStateFlow(TrendingState())
    val caAccountResourceData : StateFlow<TrendingState> = _caAccountResourceData

    private val _trendingPlayListState = MutableStateFlow(TrendingState())
    val trendingPlayListState : StateFlow<TrendingState> = _trendingPlayListState

    private val _trendingAllState = MutableStateFlow(GetAllTrendingState())
    val trendingAllState : StateFlow<GetAllTrendingState> = _trendingAllState

    private val _resourceTrendingDetailState = MutableStateFlow(TrendingVideoResourceUiState())
    val resourceTrendingDetailState : StateFlow<TrendingVideoResourceUiState> = _resourceTrendingDetailState

    private val _updateLikeAndDisLikeTrendingState = MutableSharedFlow<LikeAndDislikeUiState>()
    val updateLikeAndDisLikeTrendingState = _updateLikeAndDisLikeTrendingState.asSharedFlow()

    private val _sharedTrendingDataState = MutableSharedFlow<BaseCommonResponseModel.Data?>()
    val sharedTrendingDataState = _sharedTrendingDataState.asSharedFlow()



    private var currentHomeTrendingPage = 1
    private val videoAudioForHomeDataList = arrayListOf<VideoAudio?>()
    private var currentCaAccountResourcePage = 1
    private var currentTrendingPage = 1
    private var currentPlayListTrendingPage = 1
    private var currentTrendingDataForChannelPage = 1

    private val videoAudioList = arrayListOf<VideoAudio?>()
    private val videoAudioForChannelList = arrayListOf<VideoAudio?>()
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

             is TrendingEvent.UpdateResourceForWatchData -> {
                 updateTrendingInWatchListData(event.resourceId)
             }

             is TrendingEvent.UpdateTrendingLikeAndDisLikeDetail -> {
                 likeAndDisLikeVideoDetail(event.trendingLikeAndDisLikeModel)
             }

             is TrendingEvent.SharedTrendingData -> {
                 sharedTrendingData(event.resourceId)
             }

             is TrendingEvent.GetChannelTrendingData -> {
                 getTrendingDataForChannel(event.trendingRequestModel)
             }
         }
    }

    private fun likeAndDisLikeVideoDetail(trendingLikeAndDisLikeModel: TrendingLikeAndDisLikeModel?){


        trendingRepository.likeAndDisLikeVideoData(trendingLikeAndDisLikeModel=trendingLikeAndDisLikeModel).onEach { dataState ->

            when(dataState){

                is DataState.Error -> {

                    _updateLikeAndDisLikeTrendingState.emit(LikeAndDislikeUiState(isLoading = false, exception = dataState.exception))

                }

                DataState.Loading -> {

                    _updateLikeAndDisLikeTrendingState.emit(LikeAndDislikeUiState(isLoading = true))

                }

                is DataState.Success -> {

                    if(trendingLikeAndDisLikeModel?.islike == true){

                        _resourceTrendingDetailState.update { it.copy(data = it.data?.copy(totallike = it.data?.totallike?.plus(1), totaldislike = it.data?.totaldislike?.rem(1), islike = 1, isdislike = 0)) }

                    }

                    if(trendingLikeAndDisLikeModel?.isdislike == true){

                        _resourceTrendingDetailState.update { it.copy(data = it.data?.copy(totallike = it.data?.totallike?.rem(1), totaldislike = it.data?.totaldislike?.plus(1), isdislike = 1, islike = 0)) }

                    }


                    _updateLikeAndDisLikeTrendingState.emit(LikeAndDislikeUiState(isLoading = false, data = dataState.data))

                }
            }
        }.launchIn(viewModelScope)

    }

    private fun sharedTrendingData(resourceId: Int){
        trendingRepository.sharedTrendingData(SharedTrendingRequestModel(resourceid = resourceId)).onEach { dataState ->
            when(dataState){
                is DataState.Error -> {
                    _sharedTrendingDataState.emit(null)
                }
                DataState.Loading -> {
                    _sharedTrendingDataState.emit(null)
                }
                is DataState.Success -> {
                    _sharedTrendingDataState.emit(dataState.data)
                    _resourceTrendingDetailState.update { it.copy(data = it.data?.copy(totalshare = if(it.data?.resourceid == resourceId) it.data?.totalshare?.plus(1) else it.data?.totalshare)) }
                }
            }
        }
    }


   private fun getTrendingHomeData(trendingRequestModel:TrendingRequestModel?){

       if(trendingRequestModel?.isFirst == true){
           currentHomeTrendingPage = 1
           videoAudioForHomeDataList.clear()
           _trendingHomeState.value.videoAudioList = emptyList()
       }



       if(trendingRequestModel?.isFirst == false && ((videoAudioForHomeDataList.size) >= (_trendingHomeState.value.totalRecord ?: 0))) return

       getTrendingDataForHomeJob?.cancel()

       getTrendingDataForHomeJob = trendingRepository.getResourceData(
           TrendingRequestModel(
           displayloginuseruploaded = trendingRequestModel?.displayloginuseruploaded,
           categoryIds = trendingRequestModel?.categoryIds,
           limit = trendingRequestModel?.limit,
           page = currentHomeTrendingPage,
           mediaType = trendingRequestModel?.mediaType,
           channelId = trendingRequestModel?.channelId,
           sortDirection = trendingRequestModel?.sortDirection,
           exceptResourceIds = trendingRequestModel?.exceptResourceIds,
           sortColumn = trendingRequestModel?.sortColumn,
           isFirst = trendingRequestModel?.isFirst
           )).onEach {

           when(it){

               is DataState.Error -> {
                   _trendingHomeState.emit(TrendingState(it, isLoading = false, videoAudioList = videoAudioForHomeDataList))
               }

               is DataState.Loading -> {
                   _trendingHomeState.emit(TrendingState(it, isLoading = true, videoAudioList = videoAudioForHomeDataList))
               }

               is DataState.Success -> {
                   videoAudioForHomeDataList.addAll(it.data?.videoAudioList?: arrayListOf())
                   _trendingHomeState.emit(TrendingState(it, isLoading = false,
                       videoAudioList = it.data?.videoAudioList,
                       totalRecord = it.data?.totalRecords,
                       isFirst = trendingRequestModel?.isFirst))
                   currentHomeTrendingPage += 1
               }

           }

       }.launchIn(viewModelScope)

   }




    private fun clearTrendingData(){
        currentTrendingPage = 1
        videoAudioList.clear()
        _trendingState.update { it.copy(videoAudioList = emptyList()) }
    }

    private fun getTrendingData(trendingRequestModel:TrendingRequestModel?){




        if(trendingRequestModel?.isFirst == true){
            clearTrendingData()
        }



        if(trendingRequestModel?.isFirst == false && ((videoAudioList.size) >= (_trendingState.value.totalRecord ?: 0))) return

        trendingRequestModel?.page = currentTrendingPage

        getTrendingDataForTrendingJob?.cancel()
        getTrendingDataForTrendingJob = trendingRepository.getResourceData(trendingRequestModel=trendingRequestModel).onEach {

            when(it){

                is DataState.Error -> {
                    _trendingState.emit(TrendingState(it, isLoading = false, videoAudioList = videoAudioList))
                }

                is DataState.Loading -> {
                    _trendingState.emit(TrendingState(it, isLoading = true, videoAudioList = videoAudioList))
                }

                is DataState.Success -> {

                    if(trendingRequestModel?.isFirst == true){
                        clearTrendingData()
                    }

                    videoAudioList.addAll(it.data?.videoAudioList?: arrayListOf())
                    _trendingState.emit(TrendingState(it,
                        isLoading = false,
                        videoAudioList = videoAudioList,
                        totalRecord = it.data?.totalRecords,
                        isFirst = trendingRequestModel?.isFirst
                    )
                    )
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

        trendingRepository.getResourceData(trendingRequestModel=trendingRequestModel).onEach { dataState ->

            when(dataState){

                is DataState.Error -> {
                    _trendingPlayListState.emit(TrendingState(dataState, isLoading = false, videoAudioList = videoPlayAudioList))
                }

                is DataState.Loading -> {
                    _trendingPlayListState.emit(TrendingState(dataState, isLoading = true, videoAudioList = videoPlayAudioList))
                }

                is DataState.Success -> {

                    videoPlayAudioList.addAll(dataState.data?.videoAudioList?: arrayListOf())

                    dataState.data?.videoAudioList?.forEach{
                        _videoPlayList.add(PlayListItem(id = it?.resourceid?:0, thumb = it?.thumbimgurl))
                    }

                    _trendingPlayListState.emit(TrendingState(dataState, isLoading = false,
                        videoAudioList = videoPlayAudioList,
                        totalRecord = dataState.data?.totalRecords,
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
            _caAccountResourceData.value.videoAudioList = emptyList()
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

    private fun updateTrendingInWatchListData(resourceId: Int?) {
        _trendingState.update { homeResourceAndChannelUiState -> homeResourceAndChannelUiState.copy(videoAudioList = homeResourceAndChannelUiState.videoAudioList?.map { if(it?.resourceid == resourceId) it?.copy(isaddedinwatchlist = if(it.isaddedinwatchlist == 0) 1 else 0) else it }) }
    }

    private fun getTrendingDataForChannel(trendingRequestModel: TrendingRequestModel?){

        if(trendingRequestModel?.isFirst == true){
            currentTrendingDataForChannelPage = 1
            videoAudioForChannelList.clear()
        }


        getTrendingDataForChannelJob?.cancel()


        if(trendingRequestModel?.isFirst == false && ((videoAudioForChannelList.size) >= (_trendingChannelState.value.totalRecord ?: 0))) return

        trendingRequestModel?.page = currentTrendingDataForChannelPage

        getTrendingDataForChannelJob = trendingRepository.getResourceData(trendingRequestModel=trendingRequestModel).onEach {

            when(it){

                is DataState.Error -> {
                    _trendingChannelState.emit(TrendingState(it, isLoading = false, videoAudioList = videoAudioForChannelList))
                }

                is DataState.Loading -> {
                    _trendingChannelState.emit(TrendingState(it, isLoading = true, videoAudioList = videoAudioForChannelList))
                }

                is DataState.Success -> {

                    if(trendingRequestModel?.isFirst == true){
                        clearTrendingData()
                    }

                    videoAudioForChannelList.addAll(it.data?.videoAudioList?: arrayListOf())
                    _trendingChannelState.emit(TrendingState(it,
                        isLoading = false,
                        videoAudioList = videoAudioForChannelList,
                        totalRecord = it.data?.totalRecords,
                        isFirst = trendingRequestModel?.isFirst
                    )
                    )
                    currentTrendingDataForChannelPage += 1
                }

            }

        }.launchIn(viewModelScope)







    }

}