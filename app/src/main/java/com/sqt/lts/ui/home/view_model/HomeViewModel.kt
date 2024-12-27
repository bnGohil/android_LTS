package com.sqt.lts.ui.home.view_model

import android.annotation.SuppressLint
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import com.sqt.lts.ui.channels.event.FollowingType
import com.sqt.lts.ui.channels.data.response.ChannelData
import com.sqt.lts.ui.home.enums.HomeDataEnums
import com.sqt.lts.ui.home.event.HomeEvent
import com.sqt.lts.ui.home.homeUiState.HomeResourceAndChannelJoinModel
import com.sqt.lts.ui.home.homeUiState.HomeResourceAndChannelUiState
import com.sqt.lts.ui.trending.data.response.VideoAudio
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList


class HomeViewModel() : ViewModel(){

    private val _homeList = mutableListOf<HomeResourceAndChannelJoinModel?>()
    val homeList : MutableList<HomeResourceAndChannelJoinModel?> = _homeList

    private var _homeUiState = MutableStateFlow(HomeResourceAndChannelUiState())
    val homeUiState = _homeUiState.asStateFlow()



    private val _videoList = mutableListOf<VideoAudio?>()
    val videoList : MutableList<VideoAudio?> = _videoList


    private val _channelList = mutableListOf<ChannelData?>()
    val channelList : MutableList<ChannelData?> = _channelList




    fun onEvent(event:HomeEvent){

        when(event){

            is HomeEvent.GetChannelList -> {
                getChannelList(event.list,event.isFirst)
            }

            is HomeEvent.GetVideoList -> {

                getVideoList(event.list)

                viewModelScope.launch {
                    delay(2000)
                }

                println("event.first : ${event.first}")

                if(event.first == true){
                    getHomeList()
                }else{
                    videoData()
                }

            }

            HomeEvent.GetHomeData -> {

            }

            HomeEvent.ClearData -> { clear() }
            is HomeEvent.UpdateHomeFollowUnFollowData -> {
                updateChannelFollowUnFollow(event.channelId,event.followingType)
            }

            is HomeEvent.UpdateResourceData -> {
                addAndRemoveWatchList(event.resourceId)
            }


        }

    }


    private fun getVideoList(videoList: List<VideoAudio?>?=arrayListOf()){
        if(videoList == null) return
        for (i in videoList){
            _videoList.add(i)
        }
    }

    private fun getChannelList(channelList: List<ChannelData?>?=arrayListOf(), isFirst: Boolean?=false){

        if(channelList == null) return


//        if(isFirst == true){
//            _channelList.clear()
//        }

        val arrayList = arrayListOf<HomeResourceAndChannelJoinModel>()

        for (i in channelList){
            _channelList.add(i)
        }


        if(isFirst == false){

            if(_videoList.isNotEmpty() && _channelList.isNotEmpty()){

                for (video in _videoList){
                    arrayList.add(HomeResourceAndChannelJoinModel(videoItem = video, homeDataEnums = HomeDataEnums.RESOURCE, channelList = arrayListOf()))
                }

                arrayList.add(2, HomeResourceAndChannelJoinModel(channelList = _channelList, homeDataEnums = HomeDataEnums.CHANNEL))

            }

            _homeUiState.value  = _homeUiState.value.copy(homeDataList = arrayList)

        }
    }

    private fun getHomeList(){



        if(_videoList.isNotEmpty()){

            for (i in _videoList){
                _homeList.add(HomeResourceAndChannelJoinModel(videoItem = i, homeDataEnums = HomeDataEnums.RESOURCE))
            }




            if(_videoList.size > 2){

                _homeList.add(2, element = HomeResourceAndChannelJoinModel(channelList = _channelList.toList(), homeDataEnums = HomeDataEnums.CHANNEL))

            }else{

                println("else part is ${_videoList.size}")
                println("else part is ${_channelList.size}")

                _homeList.add(_videoList.size, element = HomeResourceAndChannelJoinModel(channelList = _channelList.toList(), homeDataEnums = HomeDataEnums.CHANNEL))

            }
        }else{
            _homeList.add(element = HomeResourceAndChannelJoinModel(
                channelList = _channelList.toList(),
                homeDataEnums = HomeDataEnums.CHANNEL
            ))
        }


        println("_homeList is ${_homeList.size}")

        _homeList.forEach {
            println(it?.homeDataEnums)
        }



//        _homeUiState.value = _homeUiState.value.copy(homeDataList =_homeList.toList())


//        _homeList.forEach {
//            println("it?.channelList : ${it?.channelList}")
//        }

        println("RESPONSE WHEN IS CHECK DATA ${_homeUiState.value.homeDataList}")
        _homeUiState.updateAndGet { it.copy(homeDataList =_homeList.toList()) }


//        _homeUiState.value.homeDataList.forEach {
//            var videoData = it
//            println("Video Data $videoData")
//        }

    }

    private fun videoData(){

        val arrayList = arrayListOf<HomeResourceAndChannelJoinModel>()



        if(_homeUiState.value.homeDataList.isNotEmpty()){

            for (allVideo in _videoList){
                arrayList.add(HomeResourceAndChannelJoinModel(videoItem = allVideo))
            }

            if(arrayList.isNotEmpty() && _channelList.isNotEmpty()){
                arrayList.add(2, HomeResourceAndChannelJoinModel(channelList = _channelList, homeDataEnums = HomeDataEnums.CHANNEL))
            }
        }

        _homeUiState.value = _homeUiState.value.copy(
            homeDataList = arrayList
        )
    }


    private fun addAndRemoveWatchList(resourceId: Int?) {
        _homeUiState.update { homeResourceAndChannelUiState -> homeResourceAndChannelUiState.copy(
            homeDataList = homeResourceAndChannelUiState.homeDataList.map { it?.copy(videoItem = if(it.videoItem?.resourceid == resourceId) it.videoItem?.copy(isaddedinwatchlist = if(it.videoItem.isaddedinwatchlist == 0) 1 else 0) else it.videoItem)  })
        }
    }

    private fun updateChannelFollowUnFollow(channelId: Int?,followingType: FollowingType?){


        if(followingType == null && channelId == null) return

        _homeUiState.update { homeResourceAndChannelUiState ->
            homeResourceAndChannelUiState.copy(
            homeDataList = homeResourceAndChannelUiState.homeDataList.map { homeResourceAndChannelJoinModel ->
                if(homeResourceAndChannelJoinModel?.homeDataEnums == HomeDataEnums.CHANNEL) homeResourceAndChannelJoinModel.copy(
                    channelList = homeResourceAndChannelJoinModel.channelList?.map {
                        val count = if (followingType == FollowingType.FOLLOW) it?.countchannelmember?.plus(1) else ((it?.countchannelmember ?: (0 - 1)))
                        if(it?.channelid == channelId) it?.copy(isfollowchannel = if (followingType == FollowingType.FOLLOW) 1 else 0, followers = "$count Followers") else it }
            ) else homeResourceAndChannelJoinModel }
        ) }
    }

    private fun clear(){
        _videoList.clear()
        _channelList.clear()
        _homeList.clear()
        _homeUiState.value.homeDataList = emptyList()
        _homeUiState.update { it.copy(homeDataList = _homeList) }
    }


}