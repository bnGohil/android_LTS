package com.example.lts.repository

import com.example.lts.ui.categories.data.request.GetCategoryRequestModel
import com.example.lts.ui.categories.data.response.CategoryResponseModel
import com.example.lts.utils.network.DataState
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategoryData(getCategoryRequestModel: GetCategoryRequestModel): Flow<DataState<CategoryResponseModel.CategoryResponseData?>>
}