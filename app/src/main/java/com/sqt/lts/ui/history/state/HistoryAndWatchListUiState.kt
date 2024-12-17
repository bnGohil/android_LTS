package com.sqt.lts.ui.history.state

import com.sqt.lts.ui.trending.data.response.VideoAudio

data class HistoryAndWatchListUiState(
    val isLoading : Boolean?=null,
    val videoAudioList : List<VideoAudio?> = arrayListOf(),
    val totalRecord: Int?=null
)