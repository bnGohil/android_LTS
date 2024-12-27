package com.sqt.lts.ui.trending.repository

import com.example.lts.base.BaseCommonResponseModel
import com.example.lts.utils.network.DataState
import com.sqt.lts.datasource.remote.RestApiService
import com.sqt.lts.datasource.remote.utils.safeApiCallWithApiStatus
import com.sqt.lts.repository.TrendingRepository
import com.sqt.lts.ui.trending.data.request.SharedTrendingRequestModel
import com.sqt.lts.ui.trending.data.request.TrendingRequestModel
import com.sqt.lts.ui.trending.data.response.TrendingLikeAndDisLikeModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class TrendingRepositoryImp @Inject constructor(private val restApiService: RestApiService) : TrendingRepository {
    override fun getResourceData(trendingRequestModel: TrendingRequestModel?) = safeApiCallWithApiStatus {
            restApiService.getResourceData(
                mediatype = trendingRequestModel?.mediaType,
                categoryIds = trendingRequestModel?.categoryIds,
                channelId = trendingRequestModel?.channelId,
                limit = trendingRequestModel?.limit,
                page = trendingRequestModel?.page,
                sortColumn = trendingRequestModel?.sortColumn,
                sortDirection = trendingRequestModel?.sortDirection,
                exceptResourceIds = trendingRequestModel?.exceptResourceIds,
                displayLoginUserUploaded = trendingRequestModel?.displayloginuseruploaded
                )
        }
    override fun getResourceDetailData(id: Int?) = safeApiCallWithApiStatus { restApiService.getTrendingVideoDetail(id) }
    override fun likeAndDisLikeVideoData(trendingLikeAndDisLikeModel: TrendingLikeAndDisLikeModel?) = safeApiCallWithApiStatus { restApiService.likeAndDislikeResource(trendingLikeAndDisLikeModel) }
    override fun sharedTrendingData(sharedTrendingRequestModel: SharedTrendingRequestModel) = safeApiCallWithApiStatus { restApiService.sharedTrendingData(sharedTrendingRequestModel) }
}