package com.sqt.lts.repository

import com.example.lts.base.BaseCommonResponseModel
import com.example.lts.utils.network.DataState
import com.sqt.lts.ui.trending.data.request.SharedTrendingRequestModel
import com.sqt.lts.ui.trending.data.request.TrendingRequestModel
import com.sqt.lts.ui.trending.data.response.TrendingLikeAndDisLikeModel
import com.sqt.lts.ui.trending.data.response.TrendingResponseModel
import com.sqt.lts.ui.trending.data.response.TrendingVideoDetailModel
import kotlinx.coroutines.flow.Flow

interface TrendingRepository {
    fun getResourceData(trendingRequestModel:TrendingRequestModel?): Flow<DataState<TrendingResponseModel.TrendingData?>>
    fun getResourceDetailData(id: Int?=null): Flow<DataState<TrendingVideoDetailModel.Data?>>
    fun likeAndDisLikeVideoData(trendingLikeAndDisLikeModel: TrendingLikeAndDisLikeModel?) : Flow<DataState<BaseCommonResponseModel.Data?>>
    fun sharedTrendingData(sharedTrendingRequestModel: SharedTrendingRequestModel) : Flow<DataState<BaseCommonResponseModel.Data?>>
}