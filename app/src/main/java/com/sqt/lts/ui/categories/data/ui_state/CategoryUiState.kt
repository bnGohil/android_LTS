package com.example.lts.ui.categories.data.ui_state

import com.example.lts.enums.PagingLoadingType
import com.example.lts.ui.categories.data.request.GetCategoryRequestModel

data class CategoryUiState(
    var currentPage : Int = 1,
    var loadingCount : Int = 0,
    var isLoading: Boolean?=null,
    val getCategoryRequestModel : GetCategoryRequestModel?=null,
    val pagingLoadingType: PagingLoadingType?=null,
)