package com.sqt.lts.ui.history.repository
import com.example.lts.base.BaseCommonResponseModel
import com.example.lts.datasource.remote.utils.safeApiCallWithApiStatus
import com.example.lts.utils.network.DataState
import com.sqt.lts.datasource.remote.RestApiService
import com.sqt.lts.repository.HistoryAndWatchListRepository
import com.sqt.lts.ui.history.request.AddAndRemoveRequestModel
import com.sqt.lts.ui.history.request.HistoryAndWatchListRequestModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HistoryAndWatchListRepositoryImp @Inject constructor(private val restApiService: RestApiService) : HistoryAndWatchListRepository {

    override fun watchListData(historyAndWatchListRequestModel: HistoryAndWatchListRequestModel?) = safeApiCallWithApiStatus { restApiService.watchListData(historyAndWatchListRequestModel = historyAndWatchListRequestModel) }
    override fun historyListData(historyAndWatchListRequestModel: HistoryAndWatchListRequestModel?) = safeApiCallWithApiStatus { restApiService.historyListData(historyAndWatchListRequestModel = historyAndWatchListRequestModel) }
    override fun addWatchListData(resourceId: Int?) = safeApiCallWithApiStatus { restApiService.addWatchList(addAndRemoveRequestModel=AddAndRemoveRequestModel(resourceId = resourceId)) }
    override fun removeWatchListData(resourceId: Int?) = safeApiCallWithApiStatus { restApiService.removeWatchList(addAndRemoveRequestModel=AddAndRemoveRequestModel(resourceId = resourceId)) }

}