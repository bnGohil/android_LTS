package com.sqt.lts.repository

import com.example.lts.utils.network.DataState
import com.sqt.lts.base.BaseTermDataModel
import kotlinx.coroutines.flow.Flow

interface ProjectTermRepository {
    fun projectTermByTermCategory():Flow<DataState<BaseTermDataModel>>
}