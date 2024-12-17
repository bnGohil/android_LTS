package com.example.lts.ui.categories.data.request

import com.sqt.lts.ui.categories.event.CategoriesEvent
import com.sqt.lts.ui.categories.state.CategoryType

data class GetCategoryRequestModel(
    val limit: Int?=0,
    var page: Int?=0,
    val displayLoginUserCategory: Int?=null,
    val sortColumn: String?=null,
    val sortDirection: String?=null,
    val selected: CategoryType?=null
    )