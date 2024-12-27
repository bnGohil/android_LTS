package com.sqt.lts.ui.trending.event

import com.sqt.lts.ui.trending.data.request.TrendingRequestModel
import com.sqt.lts.ui.trending.data.response.TrendingLikeAndDisLikeModel

sealed class TrendingEvent {
    data class GetTrendingDataForHome(val trendingRequestModel: TrendingRequestModel?) : TrendingEvent()
    data class GetTrendingData(val trendingRequestModel: TrendingRequestModel?) : TrendingEvent()
    data class GetChannelTrendingData(val trendingRequestModel: TrendingRequestModel?) : TrendingEvent()
    data class GetTrendingPlayListData(val trendingRequestModel: TrendingRequestModel?) : TrendingEvent()
    data class GetCaAccountResource(val trendingRequestModel: TrendingRequestModel?) : TrendingEvent()
    data object GetTrendingAllData : TrendingEvent()
    data class UpdateTrendingLikeAndDisLikeDetail(val trendingLikeAndDisLikeModel: TrendingLikeAndDisLikeModel?) : TrendingEvent()
    data class UpdateResourceForWatchData(val resourceId : Int?=null) : TrendingEvent()
    data class SharedTrendingData(val resourceId : Int) : TrendingEvent()
    data class GetResourceVideoDetail(var id: Int?=null) : TrendingEvent()

}