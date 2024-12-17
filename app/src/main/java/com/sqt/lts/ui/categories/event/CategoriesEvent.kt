package com.sqt.lts.ui.categories.event
import com.example.lts.ui.categories.data.request.GetCategoryRequestModel
import com.example.lts.ui.categories.data.response.Category
import com.example.lts.ui.categories.data.ui_state.CategoryUiState
import com.sqt.lts.ui.categories.state.CategoryTabModel

sealed class CategoriesEvent {

    data class GetCategoryData(val categoryUiState: CategoryUiState): CategoriesEvent()
    data class GetCaAccountAllCategories(var isFirst: Boolean,val getCategoryRequestModel : GetCategoryRequestModel?=null,): CategoriesEvent()
    data object LoadCategories : CategoriesEvent()
    data object ClearCategories : CategoriesEvent()
    data class CategorySelected(val category: Category?) : CategoriesEvent()
    data object CategoryAllSelected : CategoriesEvent()
    data class GetCategoryTabData(val isLogin:Boolean) : CategoriesEvent()
    data class SelectedCategoryTabData(val categoryTabModel: CategoryTabModel) : CategoriesEvent()
    data class GetAllCategoryData(var isFirst: Boolean,val getCategoryRequestModel : GetCategoryRequestModel?=null,) : CategoriesEvent()
    data class GetAllCategoryDataForTrending(var isFirst: Boolean,val getCategoryRequestModel : GetCategoryRequestModel?=null,) : CategoriesEvent()

}