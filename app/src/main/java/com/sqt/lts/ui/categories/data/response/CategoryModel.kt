package com.example.lts.ui.categories.data.response


import com.example.lts.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class CategoryResponseModel(
    @SerializedName("data")
    override val data: CategoryResponseData?,
    ):BaseResponse<CategoryResponseModel.CategoryResponseData>(){
    data class CategoryResponseData(
        @SerializedName("categoryList")
        val categoryList: ArrayList<Category>? = null,
        @SerializedName("currentPage")
        val currentPage: Int? = null,
        @SerializedName("limit")
        val limit: String? = null,
        @SerializedName("totalPages")
        val totalPages: Int? = null,
        @SerializedName("totalRecords")
        val totalRecords: Int? = null
    )
}