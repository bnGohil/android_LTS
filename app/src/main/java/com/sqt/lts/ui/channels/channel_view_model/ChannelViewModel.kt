package com.sqt.lts.ui.channels.channel_view_model

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lts.base.BaseCommonResponseModel
import com.example.lts.ui.auth.data.response.CountryData
import com.sqt.lts.ui.channels.event.ChannelEvent
import com.sqt.lts.ui.channels.event.FollowingType
import com.example.lts.utils.network.DataState
import com.sqt.lts.repository.ChannelRepository
import com.sqt.lts.ui.channels.data.request.ChannelRequestModel
import com.sqt.lts.ui.channels.data.response.ChannelData
import com.sqt.lts.ui.channels.state.ChannelDetailUiState
import com.sqt.lts.ui.channels.state.ChannelFollowingState
import com.sqt.lts.ui.channels.state.ChannelUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
@HiltViewModel
class ChannelViewModel @Inject constructor(private val channelRepository: ChannelRepository) : ViewModel(){


    private var homeChannelJob : Job?=null

    private val _getHomeChannelResponse = MutableStateFlow(ChannelUiState())
    val getHomeChannelResponse : StateFlow<ChannelUiState> = _getHomeChannelResponse

    private val _getChannelResponse = MutableStateFlow(ChannelUiState())
    val getChannelResponse : StateFlow<ChannelUiState> = _getChannelResponse


    private val _getChannelForPostVideoResponse = MutableStateFlow(ChannelUiState())
    val getChannelForPostVideoResponse = _getChannelForPostVideoResponse.asStateFlow()

    private val _getCaAccountChannelResponse = MutableStateFlow(ChannelUiState())
    val getCaAccountChannelResponse : StateFlow<ChannelUiState> = _getCaAccountChannelResponse

    private val _getFollowingChannelResponse = MutableStateFlow(ChannelUiState())
    val getFollowingChannelResponse : StateFlow<ChannelUiState> = _getFollowingChannelResponse

    private val _channelDetailAppResponse = MutableStateFlow(ChannelDetailUiState())
    val channelDetailAppResponse = _channelDetailAppResponse.asStateFlow()

    private val _selectedListId = mutableListOf<Int>()
    val selectedListId : MutableList<Int> = _selectedListId

    private val _createChannelAppResponse = Channel<DataState<BaseCommonResponseModel.Data?>>()
    val createChannelAppResponse = _createChannelAppResponse.receiveAsFlow()

    private val _updateChannelAppResponse = Channel<DataState<BaseCommonResponseModel.Data?>>()
    val updateChannelAppResponse  = _updateChannelAppResponse.receiveAsFlow()

    private val _followUnFollowAppResponse = MutableStateFlow(ChannelFollowingState())
    val followUnFollowAppResponse : StateFlow<ChannelFollowingState> = _followUnFollowAppResponse




    private var currentHomeChannelPage = 1

    private var currentChannelPage = 1

    private var currentCaAccountChannelPage = 1

    private var currentFollowingChannelPage = 1

    val channelList = arrayListOf<ChannelData?>()
    private val channelCaAccountList = arrayListOf<ChannelData?>()
    private val channelFollowingList = arrayListOf<ChannelData?>()

    fun onEvent(channelEvent: ChannelEvent){

        when(channelEvent){
            is ChannelEvent.GetHomeChannelData -> {
                getHomeChannelData(channelEvent.channelRequestModel)
            }

            is ChannelEvent.ChangeStatusForFollowAndUnFollow -> {}

            is ChannelEvent.SelectedChannelTabData -> {

            }

            is ChannelEvent.GetChannelData -> {
                getSelectionBaseChannel(channelEvent.channelRequestModel)
            }

            ChannelEvent.ClearSelectedList -> {
                clearSelectedList()
            }

            is ChannelEvent.FollowChannelEvent -> {
                followChannel(channelEvent.channelId)

            }
            is ChannelEvent.UnFollowChannelEvent -> {
                unFollowChannel(channelEvent.channelId)
            }

            is ChannelEvent.FollowUnFollowUpdateChannel -> {

            }

            is ChannelEvent.GetCaAccountChannelData -> {
                getCaAccountChannelData(channelEvent.channelRequestModel)
            }

            is ChannelEvent.GetFollowingChannelData -> {
                getFollowingChannel(channelEvent.channelRequestModel)
            }

            is ChannelEvent.CreateChannelEvent -> {
                createChannel(channelName = channelEvent.channelName, photo = channelEvent.photo, banner = channelEvent.banner)
            }

            is ChannelEvent.GetChannelDetailEvent -> {
                getChannelDetail(channelEvent.channelId)
            }

            is ChannelEvent.UpdateChannelEvent -> {

                updateChannel(
                    channelId = channelEvent.channelId,
                    banner = channelEvent.banner,
                    channelName = channelEvent.channelName,
                    photo = channelEvent.photo
                )
            }

            is ChannelEvent.GetSavePostForChannelData -> {
                getChannelForPostVideoData(channelEvent.channelRequestModel)
            }
        }
    }

    private fun getHomeChannelData(channelRequestModel : ChannelRequestModel?){

        println("Channel CategoryIds : ${channelRequestModel?.categoryIds}")

        if(channelRequestModel?.isFirst == true){
            currentHomeChannelPage = 1
        }

        if(channelRequestModel?.isFirst == false && (channelRequestModel.currentRecord?:0) >= _getHomeChannelResponse.value.totalCount) return

        homeChannelJob?.cancel()
        homeChannelJob = channelRepository.getChannelData(channelRequestModel = ChannelRequestModel(

            page = currentHomeChannelPage,

            limit = 10,

            isFirst = channelRequestModel?.isFirst,
                    sortColumn=channelRequestModel?.sortColumn,
                    sortDirection=channelRequestModel?.sortDirection,
                    categoryIds=channelRequestModel?.categoryIds,
                    exceptChannelIds=channelRequestModel?.exceptChannelIds,
                    myCreatedChannel=channelRequestModel?.myCreatedChannel,
                    myFollowingChannel=channelRequestModel?.myFollowingChannel,

            )).onEach {

            when(it){

                is DataState.Error -> {
                    _getHomeChannelResponse.emit(ChannelUiState(dataState = it, channelList = arrayListOf(), isLoading = false))
                }

                is DataState.Loading -> {
                    _getHomeChannelResponse.emit(ChannelUiState(dataState = it, channelList = arrayListOf(), isLoading = true))
                }

                is DataState.Success -> {
                    currentHomeChannelPage +=1
                    _getHomeChannelResponse.emit(ChannelUiState(dataState = it, channelList = it.data?.channelList, isLoading = false,isFirst = channelRequestModel?.isFirst, totalCount = it.data?.totalRecords?:0, selectedList = _selectedListId))
                }
            }


        }.launchIn(viewModelScope)
    }

    private fun getChannelForPostVideoData(channelRequestModel : ChannelRequestModel?){

        channelRepository.getChannelData(channelRequestModel = ChannelRequestModel(

            page = 1,

            limit = 100,


            isFirst = channelRequestModel?.isFirst,
                    sortColumn=channelRequestModel?.sortColumn,
                    sortDirection=channelRequestModel?.sortDirection,
                    categoryIds=channelRequestModel?.categoryIds,
                    exceptChannelIds=channelRequestModel?.exceptChannelIds,
                    myCreatedChannel=channelRequestModel?.myCreatedChannel,
                    myFollowingChannel=channelRequestModel?.myFollowingChannel,

            )).onEach {

                println("it : $it")

            when(it){

                is DataState.Error -> {
                    _getChannelForPostVideoResponse.emit(ChannelUiState(dataState = it, channelList = arrayListOf(), isLoading = false))
                }

                is DataState.Loading -> {
                    _getChannelForPostVideoResponse.emit(ChannelUiState(dataState = it, channelList = arrayListOf(), isLoading = true))
                }

                is DataState.Success -> {
                    val countryList = arrayListOf<CountryData>()

                    if(it.data?.channelList != null){

                        for (value in it.data.channelList){

                        countryList.add(CountryData(
                            countryname = value?.channelname ?: "",
                            countrycode = value?.channelname ?: "",
                            countryid = value?.channelid))
                    }
                        println("countryList : $countryList")

                    }
                    _getChannelForPostVideoResponse.emit(ChannelUiState(dataState = it, countryList = countryList,
                        channelList = it.data?.channelList, isLoading = false,isFirst = channelRequestModel?.isFirst, totalCount = it.data?.totalRecords?:0, selectedList = _selectedListId))
                }
            }


        }.launchIn(viewModelScope)
    }


    private fun clearSelectedList(){
        _selectedListId.clear()
    }

    private fun getSelectionBaseChannel(channelRequestModel : ChannelRequestModel?){

        if(channelRequestModel?.isFirst == true){
            currentChannelPage = 1
            channelList.clear()
        }

        if(channelRequestModel?.isFirst == false && (channelRequestModel.currentRecord?:0) >= _getHomeChannelResponse.value.totalCount) return

        channelRepository.getChannelData(channelRequestModel = ChannelRequestModel(
            page = currentChannelPage,
            limit = 10,
            isFirst = channelRequestModel?.isFirst,
            sortColumn=channelRequestModel?.sortColumn,
            sortDirection=channelRequestModel?.sortDirection,
            categoryIds=channelRequestModel?.categoryIds,
            exceptChannelIds=channelRequestModel?.exceptChannelIds,
            myCreatedChannel=channelRequestModel?.myCreatedChannel,
            myFollowingChannel=channelRequestModel?.myFollowingChannel,
        )).onEach {

            when(it){
                is DataState.Error -> {
                    _getChannelResponse.emit(ChannelUiState(dataState = it, channelList = channelList, isLoading = false,selectedList = _selectedListId))
                }

                is DataState.Loading -> {
                    _getChannelResponse.emit(ChannelUiState(dataState = it, channelList = channelList, isLoading = true,selectedList = _selectedListId))
                }

                is DataState.Success -> {
                    currentChannelPage +=1
                    if(it.data?.channelList != null){
                        for (i in it.data.channelList){
                            _selectedListId.add(i?.channelid?:0)
                        }
                    }
                    channelList.addAll(it.data?.channelList?:arrayListOf())
                    _getChannelResponse.emit(ChannelUiState(dataState = it, channelList = channelList, isLoading = false,isFirst = channelRequestModel?.isFirst, totalCount = it.data?.totalRecords?:0, selectedList = _selectedListId))

                }
            }


        }.launchIn(viewModelScope)


    }

    private fun followChannel(channelId: Int){

        channelRepository.followChannel(channelId).onEach { dataState ->

            println("followChannel IS $dataState")

            when(dataState){

                is DataState.Error -> {
                    _followUnFollowAppResponse.emit(ChannelFollowingState(
                        errorMsg = dataState.exception.message,
                        isLoading = false,
                        data = dataState,
                        channelFollowingType = FollowingType.FOLLOW
                    ))
                }

                is DataState.Loading -> {
                    _followUnFollowAppResponse.emit(ChannelFollowingState(isLoading = true,data = dataState))
                }

                is DataState.Success -> {
                    _followUnFollowAppResponse.emit(ChannelFollowingState(isLoading = false, data = dataState, channelFollowingType = FollowingType.FOLLOW, channelId = channelId))
                    _channelDetailAppResponse.update { it.copy(channelData = it.channelData?.copy(isfollowchannel = 1)) }
                    updateChannelFollowUnFollow(channelId =channelId, followingType = FollowingType.FOLLOW)

                }
            }

        }.launchIn(viewModelScope)

    }

    private fun unFollowChannel(channelId: Int){

        channelRepository.unfollowChannel(channelId).onEach { dataState ->


            println("unfollowChannel IS $dataState")

            when(dataState){


                is DataState.Error -> {
                    _followUnFollowAppResponse.emit(ChannelFollowingState(errorMsg = dataState.exception.message,
                        data = dataState,
                        isLoading = false, channelFollowingType = FollowingType.UNFOLLOW))
                }

                is DataState.Loading -> {
                    _followUnFollowAppResponse.emit(ChannelFollowingState(isLoading = true,data = dataState))
                }

                is DataState.Success -> {
                    updateChannelFollowUnFollow(channelId =channelId, followingType = FollowingType.UNFOLLOW)
                    _channelDetailAppResponse.update { it.copy(channelData = it.channelData?.copy(isfollowchannel = 0)) }
                    _followUnFollowAppResponse.emit(ChannelFollowingState(isLoading = false, data = dataState, channelFollowingType = FollowingType.UNFOLLOW, channelId = channelId))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateChannelFollowUnFollow(channelId: Int?,followingType: FollowingType?){

        if(followingType == null && channelId == null) return

        var data = _getChannelResponse.value.channelList?.map {

            if (it?.channelid == channelId) {
                var count = if (followingType == FollowingType.FOLLOW) it?.countchannelmember?.plus(1) else ((it?.countchannelmember ?: (0 - 1)))
                it?.copy(
                    isfollowchannel = if (followingType == FollowingType.FOLLOW) 1 else 0,
                    followers = "$count Followers"
                )
            } else {
                it
            }
        }


        _getChannelResponse.value = _getChannelResponse.value.copy(data)

    }


    private fun getCaAccountChannelData(channelRequestModel : ChannelRequestModel?){

        if(channelRequestModel?.isFirst == true){
            currentCaAccountChannelPage = 1
            channelCaAccountList.clear()
            _getCaAccountChannelResponse.value.channelList = emptyList()
        }

        if(channelRequestModel?.isFirst == false && channelCaAccountList.size >= _getCaAccountChannelResponse.value.totalCount) return

        channelRepository.getChannelData(channelRequestModel = ChannelRequestModel(
            page = currentCaAccountChannelPage,
            limit = 10,
            isFirst = channelRequestModel?.isFirst,
            sortColumn=channelRequestModel?.sortColumn,
            sortDirection=channelRequestModel?.sortDirection,
            categoryIds=channelRequestModel?.categoryIds,
            exceptChannelIds=channelRequestModel?.exceptChannelIds,
            myCreatedChannel=channelRequestModel?.myCreatedChannel,
            myFollowingChannel=channelRequestModel?.myFollowingChannel,
        )).onEach {

            when(it){
                is DataState.Error -> {
                    _getCaAccountChannelResponse.emit(ChannelUiState(dataState = it, channelList = channelCaAccountList, isLoading = false,selectedList = _selectedListId))
                }

                is DataState.Loading -> {
                    _getCaAccountChannelResponse.emit(ChannelUiState(dataState = it, channelList = channelCaAccountList, isLoading = true,selectedList = _selectedListId))
                }

                is DataState.Success -> {
                    currentCaAccountChannelPage +=1

                    channelCaAccountList.addAll(it.data?.channelList?:arrayListOf())
                    _getCaAccountChannelResponse.emit(ChannelUiState(dataState = it, channelList = channelCaAccountList, isLoading = false,isFirst = channelRequestModel?.isFirst, totalCount = it.data?.totalRecords?:0))

                }
            }


        }.launchIn(viewModelScope)

    }

    private fun getFollowingChannel(channelRequestModel : ChannelRequestModel?){



        if(channelRequestModel?.isFirst == true){
            currentFollowingChannelPage = 1
            channelFollowingList.clear()
            _getFollowingChannelResponse.value.channelList = emptyList()
        }

        channelRequestModel?.page = currentFollowingChannelPage

        if(channelRequestModel?.isFirst == false && channelFollowingList.size >= _getFollowingChannelResponse.value.totalCount) return


        channelRepository.getChannelData(channelRequestModel=channelRequestModel).onEach {

            when(it){

                is DataState.Error -> {
                    _getFollowingChannelResponse.value = _getFollowingChannelResponse.value.copy(isLoading = false, channelList = channelFollowingList)
                }

                DataState.Loading -> {
                    _getFollowingChannelResponse.value = _getFollowingChannelResponse.value.copy(isLoading = true, channelList = channelFollowingList)
                }

                is DataState.Success -> {
                    currentFollowingChannelPage=+1
                    channelFollowingList.addAll(it.data?.channelList?:arrayListOf())
                    _getFollowingChannelResponse.value = _getFollowingChannelResponse.value.copy(isLoading = false, channelList = channelFollowingList, totalCount = it.data?.totalRecords?:0)
                }

            }

        }.launchIn(viewModelScope)

    }


    private fun createChannel(channelName: String?,photo:Uri?,banner: Uri?){

        channelRepository.createChannel(channelName=channelName, photo = photo,banner=banner).onEach {
            _createChannelAppResponse.send(it)
        }.launchIn(viewModelScope)

    }

    private fun updateChannel(channelId: Int?,channelName: String?,photo:Uri?,banner: Uri?){
        channelRepository.updateChannel(
            channelId=channelId?:0,
            channelName=channelName,
            photo = photo,banner=banner).onEach {
            _updateChannelAppResponse.send(it)
        }.launchIn(viewModelScope)

    }

    private fun getChannelDetail(id: Int){

        channelRepository.getChannelDetail(id).onEach {

            when(it){

                is DataState.Error -> {
                    _channelDetailAppResponse.value = _channelDetailAppResponse.value.copy(isLoading = false, exception = it.exception, channelDataState = it)
                }
                DataState.Loading -> {
                    _channelDetailAppResponse.value = _channelDetailAppResponse.value.copy(isLoading = true,channelDataState = it)
                }
                is DataState.Success -> {
                    _channelDetailAppResponse.value = _channelDetailAppResponse.value.copy(isLoading = false, channelData = it.data, message = it.message,channelDataState = it)
                }
            }


        }.launchIn(viewModelScope)

    }


}
