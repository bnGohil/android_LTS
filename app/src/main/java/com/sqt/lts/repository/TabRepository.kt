package com.sqt.lts.repository

import android.icu.text.StringSearch
import com.example.lts.ui.tab.bottom_bar.BottomNavBarItem
import com.example.lts.utils.network.DataState
import com.sqt.lts.base.GlobalSearchData
import com.sqt.lts.base.GlobalSearchResponseModel
import kotlinx.coroutines.flow.Flow

interface TabRepository {
   fun getGlobalSearchData(search: String):Flow<DataState<List<GlobalSearchData?>?>>
   fun getTabListData() : Flow<List<BottomNavBarItem>>
}