package com.sqt.lts.ui.categories.event
import com.example.lts.ui.categories.data.request.GetCategoryRequestModel
import com.example.lts.ui.categories.data.response.Category
import com.example.lts.ui.categories.data.ui_state.CategoryUiState
import com.sqt.lts.ui.categories.state.CategoryTabModel

sealed class CategoriesEvent {

    data  class  GetCategoryData(val categoryUiState: CategoryUiState): CategoriesEvent()
    data  class  GetCaAccountAllCategories(var isFirst: Boolean,val getCategoryRequestModel : GetCategoryRequestModel?=null,): CategoriesEvent()
    data  object ClearCategories : CategoriesEvent()
    data  class  CategorySelected(val category: Category?) : CategoriesEvent()
    data  class  CategorySelectedForTrending(val category: Category?) : CategoriesEvent()
    data  object SelectAllCategories : CategoriesEvent()
    data  object SelectAllForTrendingCategories : CategoriesEvent()
    data  object CategoryAllSelected : CategoriesEvent()
    data  class  UpdatePostVideoCategoriesValue(val categoriesId:Int,var isSelected: Boolean) : CategoriesEvent()
    data class  GetAllCategoryData(var isFirst: Boolean,val getCategoryRequestModel : GetCategoryRequestModel?=null,) : CategoriesEvent()
    data class  GetAllCategoryDataForPostVideo(var isFirst: Boolean,val getCategoryRequestModel : GetCategoryRequestModel?=null,) : CategoriesEvent()
    data class  GetAllCategoryDataForTrending(var isFirst: Boolean,val getCategoryRequestModel : GetCategoryRequestModel?=null,) : CategoriesEvent()

}