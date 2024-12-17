package com.sqt.lts.ui.categories.state

import com.example.lts.enums.PagingLoadingType
import com.example.lts.ui.categories.data.response.Category

data class CategoriesState(
    var categories: List<Category> = arrayListOf(),
    val isLoading: Boolean = false,
    val totalPages:Int?= 0,
    val totalRecord: Int?= 0,
    val currentPage:Int =1,
    val pagingLoadingType: PagingLoadingType?=null,
    val error: String? = null
)

data class CategoriesTabState(
    val categoriesTab: List<CategoryTabModel> = arrayListOf()
)

//data class Category(val categoriesName:String?=null,var isSelectedCategory: Boolean?=false,val id:String?=null)
data class CategoryTabModel(val categoriesType : CategoryType?= null,var name: String?= null)

enum class CategoryType{
    MY_CATEGORY,OTHER_CATEGORY
}

