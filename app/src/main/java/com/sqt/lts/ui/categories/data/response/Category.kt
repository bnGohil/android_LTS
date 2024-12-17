package com.example.lts.ui.categories.data.response


import com.google.gson.annotations.SerializedName

enum class SelectedType{ALL,ANY}

data class Category(
    @SerializedName("categoryid")
    val categoryid: Int? = null,
    val isAllSelected: Boolean? = null,
    @SerializedName("categoryname")
    val categoryname: String? = null,
    @SerializedName("countresourcecategory")
    val countresourcecategory: Int? = null,
    @SerializedName("createdby")
    val createdby: Any? = null,
    @SerializedName("createdon")
    val createdon: String? = null,
    @SerializedName("isactive")
    val isactive: Int? = null,
    @SerializedName("iscategoryassigned")
    val iscategoryassigned: Int? = null,
    @SerializedName("longdetails")
    val longdetails: String? = null,
    @SerializedName("photo")
    val photo: String? = null,
    @SerializedName("photourl")
    val photourl: String? = null,
    @SerializedName("rownumber")
    val rownumber: Int? = null,
    @SerializedName("shortdetails")
    val shortdetails: String? = null,
    @SerializedName("totalrecords")
    val totalrecords: Int? = null,
    @SerializedName("updatedby")
    val updatedby: Any? = null,
    @SerializedName("updatedon")
    val updatedon: String? = null,
    var selectedCategory:Boolean?=false,
    var type:SelectedType?=null,
    val any: Boolean?=null
)