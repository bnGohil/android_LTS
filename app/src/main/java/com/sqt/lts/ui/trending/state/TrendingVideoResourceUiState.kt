package com.sqt.lts.ui.trending.state

import com.sqt.lts.ui.trending.data.response.TrendingVideoDetailModel

data class TrendingVideoResourceUiState(
    val isLoading: Boolean?=null,
    var data : TrendingVideoDetailModel.Data?=null
)