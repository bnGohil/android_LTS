package com.sqt.lts.ui.history.ui_state

import com.example.lts.base.BaseCommonResponseModel
import com.example.lts.utils.network.DataState
import java.lang.Exception

data class HistoryAndWatchListUpdateState(
    val exception: Exception?=null,
    val msg : String?=null,
    val isLoading : Boolean?=null,
    val dataState: DataState<BaseCommonResponseModel.Data?>?=null)