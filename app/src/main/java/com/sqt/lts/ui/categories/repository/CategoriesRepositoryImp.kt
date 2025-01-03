package com.example.lts.ui.categories.repository

import com.sqt.lts.datasource.remote.RestApiService
import com.sqt.lts.datasource.remote.utils.safeApiCallWithApiStatus
import com.example.lts.repository.CategoryRepository
import com.example.lts.ui.categories.data.request.GetCategoryRequestModel
import javax.inject.Inject

class CategoriesRepositoryImp  @Inject constructor(
    private val restApiService: RestApiService
): CategoryRepository{

    override fun getCategoryData(getCategoryRequestModel: GetCategoryRequestModel) = safeApiCallWithApiStatus {
        restApiService.getCategoryData(sortColumn=getCategoryRequestModel.sortColumn,
            limit = getCategoryRequestModel.limit,
            displayLoginUserCategory = getCategoryRequestModel.displayLoginUserCategory,
            page = getCategoryRequestModel.page,
            sortDirection = getCategoryRequestModel.sortDirection)
    }

}