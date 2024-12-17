package com.sqt.lts.ui.trending.event

import com.sqt.lts.ui.trending.data.request.TrendingRequestModel

sealed class TrendingEvent {
    data class GetTrendingDataForHome(val trendingRequestModel: TrendingRequestModel?) : TrendingEvent()
    data class GetTrendingData(val trendingRequestModel: TrendingRequestModel?) : TrendingEvent()
    data class GetTrendingPlayListData(val trendingRequestModel: TrendingRequestModel?) : TrendingEvent()
    data class GetCaAccountResource(val trendingRequestModel: TrendingRequestModel?) : TrendingEvent()
    data object GetTrendingAllData : TrendingEvent()
    data class GetResourceVideoDetail(var id: Int?=null) : TrendingEvent()

}