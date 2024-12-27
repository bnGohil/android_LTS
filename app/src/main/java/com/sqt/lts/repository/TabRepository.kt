package com.sqt.lts.repository

import android.icu.text.StringSearch
import com.example.lts.utils.network.DataState
import com.sqt.lts.base.GlobalSearchData
import com.sqt.lts.base.GlobalSearchResponseModel
import kotlinx.coroutines.flow.Flow

interface TabRepository {
   fun getGlobalSearchData(search: String):Flow<DataState<List<GlobalSearchData?>?>>
}