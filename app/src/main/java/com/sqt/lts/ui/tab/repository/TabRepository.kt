package com.sqt.lts.ui.tab.repository

import com.example.lts.ui.tab.bottom_bar.BottomNavBarItem
import com.example.lts.utils.network.DataState
import com.sqt.lts.base.GlobalSearchData
import com.sqt.lts.base.GlobalSearchResponseModel
import com.sqt.lts.datasource.remote.RestApiService
import com.sqt.lts.datasource.remote.utils.safeApiCallWithApiStatus
import com.sqt.lts.repository.TabRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

class TabRepositoryImp @Inject constructor(private val restApiService: RestApiService) : TabRepository {

    override fun getGlobalSearchData(search: String) = safeApiCallWithApiStatus { restApiService.getSearchGlobalData(searchText = search) }
    override fun getTabListData() = {
        arrayListOf<BottomNavBarItem>(
            BottomNavBarItem.home,
            BottomNavBarItem.categories,
            BottomNavBarItem.channels,
            BottomNavBarItem.trending,
        )
    }.asFlow()

}