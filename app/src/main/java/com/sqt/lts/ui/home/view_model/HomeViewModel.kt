package com.sqt.lts.ui.home.view_model

import android.annotation.SuppressLint
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.media3.common.util.UnstableApi
import com.sqt.lts.ui.channels.event.FollowingType
import com.sqt.lts.ui.channels.data.response.ChannelData
import com.sqt.lts.ui.home.enums.HomeDataEnums
import com.sqt.lts.ui.home.event.HomeEvent
import com.sqt.lts.ui.home.homeUiState.HomeResourceAndChannelJoinModel
import com.sqt.lts.ui.home.homeUiState.HomeResourceAndChannelUiState
import com.sqt.lts.ui.trending.data.response.VideoAudio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.collections.ArrayList


class HomeViewModel() : ViewModel(){

    private val _homeList = mutableListOf<HomeResourceAndChannelJoinModel?>()
    val homeList : MutableList<HomeResourceAndChannelJoinModel?> = _homeList

    private var _homeUiState = MutableStateFlow(HomeResourceAndChannelUiState())
    val homeUiState : StateFlow<HomeResourceAndChannelUiState> = _homeUiState



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
                if(event.first == true){
                    getHomeList()
                }else{
                    videoData()
                }

            }

            HomeEvent.GetHomeData -> {}

            HomeEvent.ClearData -> { clear() }
            is HomeEvent.UpdateHomeFollowUnFollowData -> {
                updateChannelFollowUnFollow(event.channelId,event.followingType)
            }
        }

    }


    private fun getVideoList(videoList: ArrayList<VideoAudio?>?=arrayListOf()){
        if(videoList == null) return
        for (i in videoList){
            _videoList.add(i)
        }
    }

    private fun getChannelList(channelList: List<ChannelData?>?=arrayListOf(), isFirst: Boolean?=false){

        if(channelList == null) return

        var arrayList = arrayListOf<HomeResourceAndChannelJoinModel>()

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

                _homeList.add(_videoList.size, element = HomeResourceAndChannelJoinModel(channelList = _channelList.toList(), homeDataEnums = HomeDataEnums.CHANNEL))

            }
        }else{
            _homeList.add(element = HomeResourceAndChannelJoinModel(
                channelList = _channelList.toList(),
                homeDataEnums = HomeDataEnums.CHANNEL
            ))
        }


        _homeUiState.update { it.copy(homeDataList =_homeList.toList()) }

    }

    private fun videoData(){

        var arrayList = arrayListOf<HomeResourceAndChannelJoinModel>()



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


    @SuppressLint("SuspiciousIndentation")
    @OptIn(UnstableApi::class)
    private fun updateChannelFollowUnFollow(channelId: Int?,followingType: FollowingType?){

        if(followingType == null && channelId == null) return

        var arrayList = arrayListOf<HomeResourceAndChannelJoinModel>()

        println("followingType:$followingType")
        println("channelId:$channelId")

        println("_channelList:${_channelList.size}")


//        _channelList.forEach {
//            println("FIRST TIME ID channelId = ${it?.channelid} channelId = $channelId  ${it?.isfollowchannel}")
//        }


        var channelBaseAllLists = _homeUiState.value.homeDataList.find { it?.homeDataEnums == HomeDataEnums.CHANNEL }

        var data = _channelList.map {

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
//
//        channelBaseAllLists?.channelList?.forEach {
//            println("First TIME ID channelId = ${it?.channelid} channelId = $channelId  ${it?.isfollowchannel}")
//        }


        channelBaseAllLists?.channelList  = data


//        channelBaseAllLists?.channelList?.forEach {
//
//        }


        _homeUiState.update { it }










//             _homeUiState.value.homeDataList.forEach{
//
//         if (it?.homeDataEnums == HomeDataEnums.CHANNEL && it.channelList?.isNotEmpty() == true){
//             it.channelList?.forEach {
//                 Log.d("FIRST TIME id $channelId",it.toString())
//             }
//         }
//
//     }




//     _homeUiState.value.homeDataList.find { it?.homeDataEnums == HomeDataEnums.CHANNEL && it.channelList?.isNotEmpty() == true}?.channelList = data


//        _homeUiState.value.homeDataList.forEach{
//
//            if (it?.homeDataEnums == HomeDataEnums.CHANNEL && it.channelList?.isNotEmpty() == true){
//                it.channelList?.forEach {
//                    Log.d("SECOND TIME $channelId",it.toString())
//                }
//            }
//
//        }

        //     _homeUiState.value.homeDataList.forEach{
//
//         if (it?.homeDataEnums == HomeDataEnums.CHANNEL && it.channelList?.isNotEmpty() == true){
//             it.channelList?.forEach {
//
//             }
//         }
//
//     }



//        _homeUiState.update { it }












//       var allList = _homeUiState.value.homeDataList.map { if(it?.homeDataEnums == HomeDataEnums.CHANNEL) it else it?.copy() }.toList()

//        val list = allList.map { it?.channelList }.filter { it?.isNotEmpty() == true}
//
//        if(list.isNotEmpty()){
//
//            var isFirstListItem = list.first()
//
//            var data = isFirstListItem?.map {
//
//            if (it?.channelid == channelId) {
//                var count = if (followingType == FollowingType.FOLLOW) it?.countchannelmember?.plus(1) else ((it?.countchannelmember ?: (0 - 1)))
//                it?.copy(
//                    isfollowchannel = if (followingType == FollowingType.FOLLOW) 1 else 0,
//                    followers = "$count Followers"
//                )
//            } else {
//                it
//            }
//        }
//
//
//
//
//        }




//        _homeUiState.value = _homeUiState.value.copy(arrayList)

//        println("list is $list")







//        var data = _channelList.map {
//
//            if (it?.channelid == channelId) {
//                var count = if (followingType == FollowingType.FOLLOW) it?.countchannelmember?.plus(1) else ((it?.countchannelmember ?: (0 - 1)))
//                it?.copy(
//                    isfollowchannel = if (followingType == FollowingType.FOLLOW) 1 else 0,
//                    followers = "$count Followers"
//                )
//            } else {
//                it
//            }
//        }

//        _homeUiState.value = _homeUiState.value.copy(
//            homeDataList =
//        )


//        _getChannelResponse.value = _getChannelResponse.value.copy(data)

    }

    private fun clear(){
        _videoList.clear()
        _channelList.clear()
        _homeList.clear()
        _homeUiState.value = _homeUiState.value.copy(homeDataList = _homeList)
    }


}