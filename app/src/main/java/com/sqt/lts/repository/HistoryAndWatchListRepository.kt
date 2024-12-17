package com.sqt.lts.repository
import com.example.lts.base.BaseCommonResponseModel
import com.example.lts.utils.network.DataState
import com.sqt.lts.ui.history.request.HistoryAndWatchListRequestModel
import com.sqt.lts.ui.history.response.HistoryResponseModel
import com.sqt.lts.ui.history.response.WatchListResponseModel
import kotlinx.coroutines.flow.Flow


interface HistoryAndWatchListRepository {
    fun watchListData(historyAndWatchListRequestModel:HistoryAndWatchListRequestModel?): Flow<DataState<WatchListResponseModel.Data?>>
    fun historyListData(historyAndWatchListRequestModel:HistoryAndWatchListRequestModel?) : Flow<DataState<HistoryResponseModel.Data?>>
    fun addWatchListData(resourceId : Int?) : Flow<DataState<BaseCommonResponseModel.Data?>>
    fun removeWatchListData(resourceId : Int?) : Flow<DataState<BaseCommonResponseModel.Data?>>
}