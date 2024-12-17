package com.sqt.lts.repository

import com.example.lts.utils.network.DataState
import com.sqt.lts.ui.trending.data.request.TrendingRequestModel
import com.sqt.lts.ui.trending.data.response.TrendingResponseModel
import com.sqt.lts.ui.trending.data.response.TrendingVideoDetailModel
import kotlinx.coroutines.flow.Flow

interface TrendingRepository {
    fun getResourceData(trendingRequestModel:TrendingRequestModel?): Flow<DataState<TrendingResponseModel.TrendingData?>>
    fun getResourceDetailData(id: Int?=null): Flow<DataState<TrendingVideoDetailModel.Data?>>
}