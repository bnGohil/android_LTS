package com.example.lts.ui.trending.state

import com.example.lts.ui.trending.data.TrendingItemResponseData
import com.example.lts.utils.ResponseData
import com.example.lts.utils.network.DataState
import com.sqt.lts.ui.home.homeUiState.HomeResourceAndChannelJoinModel
import com.sqt.lts.ui.trending.data.response.TrendingResponseModel
import com.sqt.lts.ui.trending.data.response.VideoAudio


data class TrendingState(
    var dataState: DataState<TrendingResponseModel.TrendingData?>? =null,
    var homeList: List<HomeResourceAndChannelJoinModel?>?=arrayListOf(),
    val videoAudioList: ArrayList<VideoAudio?>? = arrayListOf(),
    val isLoading: Boolean? =false,
    val isFirst: Boolean? =false,
    var totalRecord: Int? = 0
)
data class GetAllTrendingState(var responseData: ResponseData?=null,var trendingList:ArrayList<TrendingItemResponseData> = arrayListOf())
