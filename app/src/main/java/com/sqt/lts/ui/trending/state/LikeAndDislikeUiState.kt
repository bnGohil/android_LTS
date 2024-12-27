package com.sqt.lts.ui.trending.state

import coil.compose.AsyncImagePainter
import com.example.lts.base.BaseCommonResponseModel
import com.sqt.lts.ui.trending.event.TrendingEvent
import java.lang.Exception

data class LikeAndDislikeUiState(
    val isLoading: Boolean?= null,
    val exception: Exception?=null,
    val data: BaseCommonResponseModel.Data?=null,
    val totalLike : Int?=null,
    val totalDisLike : Int?=null
)