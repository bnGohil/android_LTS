package com.sqt.lts.ui.categories.viewModel
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.util.fastFilter
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastSumBy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lts.enums.PagingLoadingType
import com.example.lts.repository.CategoryRepository
import com.example.lts.ui.categories.data.request.GetCategoryRequestModel
import com.example.lts.ui.categories.data.response.Category
import com.example.lts.ui.categories.data.response.SelectedType
import com.example.lts.ui.categories.data.ui_state.CategoryUiState
import com.sqt.lts.ui.categories.event.CategoriesEvent
import com.sqt.lts.ui.categories.state.CategoriesState
import com.sqt.lts.ui.categories.state.CategoriesTabState
import com.sqt.lts.ui.categories.state.CategoryType
import com.example.lts.utils.network.DataState
import com.example.lts.utils.toggle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val repository: CategoryRepository) : ViewModel(){

    private val _categoryHomeState = MutableStateFlow(CategoriesState())
    val categoryHomeState : StateFlow<CategoriesState> = _categoryHomeState

    private val _categoryForTrendingState = MutableStateFlow(CategoriesState())
    val categoryForTrendingState : StateFlow<CategoriesState> = _categoryForTrendingState

    private val _categoryCaAccountState = MutableStateFlow(CategoriesState())
    val categoryCaAccountState : StateFlow<CategoriesState> = _categoryCaAccountState

    private val _updateCategoryData = Channel<Category>()
    val updateCategoryData = _updateCategoryData.consumeAsFlow()



    private val _isAllSelected = mutableStateOf<Boolean>(true)
    val isAllSelected : State<Boolean> = _isAllSelected

    private val _categoryTabState = MutableStateFlow(CategoriesTabState())
    val categoryTabState : StateFlow<CategoriesTabState> = _categoryTabState

    private val _selectedTab = mutableStateOf<CategoryType>(CategoryType.MY_CATEGORY)
    val selectedTab : State<CategoryType> = _selectedTab

    private var categoriesHomeArray = arrayListOf<Category>()
    private var categoriesTrendingArray = arrayListOf<Category>()
    private var caAccountCategoriesArray = arrayListOf<Category>()
    private var postVideoForCategoriesArray = arrayListOf<Category>()

    private var _selectedItemArray = MutableStateFlow<List<Category>>(arrayListOf())
    val selectedItemArray : StateFlow<List<Category>> = _selectedItemArray


   private var categoryCurrentPage = 1


    private var categoryCurrentPageForCategoryPage = 1
    private var categoryCurrentPageForPostVideoPage = 1
    private var categoryCurrentPageForTrendingPage = 1
    private var caAccountCategoryPage = 1

    private val _categoryDataState = MutableStateFlow(CategoriesState())
    val categoryDataState : StateFlow<CategoriesState> = _categoryDataState

    private val _categoriesForPostVideoAppResponse = MutableStateFlow(CategoriesState())
    val categoriesForPostVideoAppResponse : StateFlow<CategoriesState> = _categoriesForPostVideoAppResponse

    private var categoriesArray = arrayListOf<Category>()



















    init {

    }


     fun onEvent(event: CategoriesEvent){

        when(event){

            is CategoriesEvent.CategorySelected -> {
                selectCategory(event.category)
            }



            is CategoriesEvent.CategoryAllSelected -> {
                selectAllCategory()
            }





            is CategoriesEvent.GetCategoryData -> {
                getCategoryListData(event.categoryUiState)
            }

            is CategoriesEvent.GetAllCategoryData -> {
                getCategoryDataForCategoryPage(event.isFirst,event.getCategoryRequestModel)
            }

            CategoriesEvent.ClearCategories -> {
                clearAllCategories()
            }

            is CategoriesEvent.GetAllCategoryDataForTrending -> {
                getCategoryDataForTrendingPage(event.isFirst,event.getCategoryRequestModel)
            }

            is CategoriesEvent.GetCaAccountAllCategories -> {
                getCaAccountCategoriesData(event.isFirst,event.getCategoryRequestModel)
            }

            is CategoriesEvent.GetAllCategoryDataForPostVideo -> {
                getCategoriesForPostVideo(event.getCategoryRequestModel,event.isFirst)
            }

            is CategoriesEvent.UpdatePostVideoCategoriesValue -> {
                updateCategoriesDataValue(event.categoriesId,event.isSelected)
            }

            CategoriesEvent.SelectAllCategories -> {
                val data = categoriesHomeArray.map { if(it.type == SelectedType.ALL) it.copy(selectedCategory = true) else it.copy(selectedCategory = false) }
                _categoryHomeState.update { it.copy(data) }
                viewModelScope.launch {
                    _updateCategoryData.send(Category(type = SelectedType.ALL))
                }
            }
        }
    }






  private fun selectAllCategory(){
      _categoryHomeState.value.categories.forEach { element ->
          element.selectedCategory = true
      }
  }

    private fun selectCategory(category:Category?=null){


        if(category == null) return


        val list = _categoryHomeState.value.categories.map { e -> if(e.categoryid == category.categoryid) e.copy(
            selectedCategory = category.selectedCategory?.toggle()
        ) else e }.toList()

        val isSelected = list.filter {categoryData-> categoryData.type == SelectedType.ANY }.all { categoryData -> categoryData.selectedCategory == true}


        if(!isSelected){
            list.filter { categoryRes -> categoryRes.type == SelectedType.ALL}.forEach {
                it.selectedCategory = false
            }
        }else{
            list.filter { categoryRes -> categoryRes.type == SelectedType.ALL}.forEach {
                it.selectedCategory = true
            }
        }

        _selectedItemArray.value = _categoryHomeState.value.categories.filter { name -> name.selectedCategory == true}.toList()

        _categoryHomeState.update { it.copy(categories = list) }

        viewModelScope.launch {
            _updateCategoryData.send(category)
        }

    }






  private fun updateListItem(isLoading: Boolean){

      val list = arrayListOf<Category>()

      when(isLoading){
          true -> {
              list.add(Category())
          }
          false -> {
              list.remove(Category())
          }
      }

      _categoryHomeState.value = _categoryHomeState.value.copy(
          categories = list
      )
  }

  private fun getCategoryListData(categoryUiState: CategoryUiState) {


      if(categoryUiState.pagingLoadingType == PagingLoadingType.IS_LOADING){

          categoryCurrentPage += 1

      }else{

          categoriesHomeArray.clear()
          categoryCurrentPage = 1

      }

      if((categoryUiState.pagingLoadingType == PagingLoadingType.IS_LOADING) && (_categoryHomeState.value.totalRecord?:0) <= (categoriesHomeArray.size?:0)) return



      repository.getCategoryData(
          getCategoryRequestModel = GetCategoryRequestModel(
              limit = 10,
              sortDirection = categoryUiState.getCategoryRequestModel?.sortDirection,
              page = categoryCurrentPage,
              displayLoginUserCategory = 1,
              sortColumn = categoryUiState.getCategoryRequestModel?.sortColumn
          )
      ).onEach { dataState ->
          when(dataState){
              is DataState.Error -> {
                  _categoryHomeState.value = _categoryHomeState.value.copy(isLoading = false, categories = categoriesHomeArray)
              }
              is DataState.Loading -> {
                  _categoryHomeState.value = _categoryHomeState.value.copy(isLoading = true, categories = categoriesHomeArray)
              }
              is DataState.Success -> {
                  if(_selectedItemArray.value.isNotEmpty()){
                      categoriesHomeArray.filter { category -> category.categoryid in _selectedItemArray.value.map { it.categoryid } }.forEach {
                          it.selectedCategory=true
                     }

                      dataState.data?.categoryList?.forEach { t -> t.selectedCategory = false }

                      categoriesHomeArray.addAll(dataState.data?.categoryList?: arrayListOf())

                  }else{
                      if(categoriesHomeArray.isEmpty()){
                          categoriesHomeArray.add(0,Category(categoryname = "All", type = SelectedType.ALL, selectedCategory = true))
                      }

                      if(dataState.data?.categoryList != null){

                          dataState.data.categoryList.forEach {
                              category ->
                              if(category.type == null) {
                                category.type = SelectedType.ANY
                                category.selectedCategory = false
                              }
                          }

                          categoriesHomeArray.addAll(dataState.data.categoryList)

                      }


                  }

                  _categoryHomeState.value = _categoryHomeState.value.copy(
                      totalRecord = dataState.data?.totalRecords,
                      categories = categoriesHomeArray,isLoading = false, currentPage = dataState.data?.currentPage?:1, totalPages = dataState.data?.totalPages)
              }
          }
      }.launchIn(viewModelScope)
    }

    private fun clearAllCategories(){
        categoriesArray.clear()
        _categoryDataState.value = _categoryDataState.value.copy(categories = arrayListOf())
    }

   private fun getCategoryDataForCategoryPage(isFirst: Boolean,getCategoryRequestModel : GetCategoryRequestModel?=null,){

       if(isFirst == false && (_categoryDataState.value.categories.size >= (_categoryDataState.value.totalRecord?:0))) return

       if(isFirst){
           categoryCurrentPageForCategoryPage = 1
       }else{
           categoryCurrentPageForCategoryPage +=1
       }

       repository.getCategoryData(
           getCategoryRequestModel = GetCategoryRequestModel(
               limit = if(getCategoryRequestModel?.selected == CategoryType.OTHER_CATEGORY) 100 else 10,
               displayLoginUserCategory = getCategoryRequestModel?.displayLoginUserCategory,
               sortDirection = getCategoryRequestModel?.sortDirection,
               sortColumn = getCategoryRequestModel?.sortColumn,
               page = categoryCurrentPageForCategoryPage,
           )
       ).onEach {
           when(it){
               is DataState.Error -> {
                _categoryDataState.emit(CategoriesState(isLoading = false, categories = categoriesArray))
               }
               is DataState.Loading -> {
                   _categoryDataState.emit(CategoriesState(isLoading = true, categories = categoriesArray))
               }
               is DataState.Success -> {

                   if(isFirst){
                       categoriesArray.clear()
                       categoryCurrentPageForCategoryPage = 1
                   }



                   categoriesArray.addAll(it.data?.categoryList?:arrayListOf())

                   if(getCategoryRequestModel?.selected == CategoryType.OTHER_CATEGORY){

                       categoriesArray.removeAll { e->  e.iscategoryassigned == 1}

                   }

                   _categoryDataState.emit(CategoriesState(isLoading = false, categories = categoriesArray, totalRecord = it.data?.totalRecords))
               }
           }
       }.launchIn(viewModelScope)
   }


    private fun getCategoryDataForTrendingPage(isFirst: Boolean,getCategoryRequestModel : GetCategoryRequestModel?=null,){

       if(isFirst == false && (_categoryForTrendingState.value.categories.size >= (_categoryForTrendingState.value.totalRecord?:0))) return

       if(isFirst){
           categoryCurrentPageForTrendingPage = 1
       }else{
           categoryCurrentPageForTrendingPage +=1
       }

       repository.getCategoryData(
           getCategoryRequestModel = GetCategoryRequestModel(
               limit = 10,
               displayLoginUserCategory = getCategoryRequestModel?.displayLoginUserCategory,
               sortDirection = getCategoryRequestModel?.sortDirection,
               sortColumn = getCategoryRequestModel?.sortColumn,
               page = categoryCurrentPageForTrendingPage,
           )
       ).onEach {
           when(it){
               is DataState.Error -> {
                   _categoryForTrendingState.emit(CategoriesState(isLoading = false, categories = categoriesTrendingArray))
               }
               is DataState.Loading -> {
                   _categoryForTrendingState.emit(CategoriesState(isLoading = true, categories = categoriesTrendingArray))
               }
               is DataState.Success -> {

                   if(isFirst){
                       categoriesTrendingArray.clear()
                       categoryCurrentPageForTrendingPage = 1
                       categoriesTrendingArray.add(0,Category(categoryname = "All", type = SelectedType.ALL))
                   }

                   categoriesTrendingArray.addAll(it.data?.categoryList?:arrayListOf())

                   _categoryForTrendingState.emit(CategoriesState(isLoading = false, categories = categoriesTrendingArray, totalRecord = it.data?.totalRecords))
               }
           }
       }.launchIn(viewModelScope)
   }

    private fun getCaAccountCategoriesData(isFirst: Boolean,getCategoryRequestModel:GetCategoryRequestModel?){

        if(!isFirst && (caAccountCategoriesArray.size >= (_categoryCaAccountState.value.totalRecord?:0))) return

        if(isFirst){
            categoryCurrentPageForTrendingPage = 1
            caAccountCategoriesArray.clear()
            _categoryCaAccountState.value.categories = emptyList()
        }

        repository.getCategoryData(

            getCategoryRequestModel = GetCategoryRequestModel(
                limit = 10,
                displayLoginUserCategory = getCategoryRequestModel?.displayLoginUserCategory,
                sortDirection = getCategoryRequestModel?.sortDirection,
                sortColumn = getCategoryRequestModel?.sortColumn,
                page = caAccountCategoryPage,
            )

        ).onEach {

            when(it){

                is DataState.Error -> {
                    _categoryCaAccountState.emit(CategoriesState(isLoading = false, categories = caAccountCategoriesArray))
                }

                is DataState.Loading -> {
                    _categoryCaAccountState.emit(CategoriesState(isLoading = true, categories = caAccountCategoriesArray))
                }

                is DataState.Success -> {
                    caAccountCategoryPage +=1
                    caAccountCategoriesArray.addAll(it.data?.categoryList?:arrayListOf())
                    _categoryCaAccountState.emit(CategoriesState(isLoading = false, categories = caAccountCategoriesArray, totalRecord = it.data?.totalRecords))
                }
            }
        }.launchIn(viewModelScope)
        
        
    }

    private fun getCategoriesForPostVideo(
        getCategoryRequestModel: GetCategoryRequestModel?,
        first: Boolean
    ){



        if(first){
            categoryCurrentPageForPostVideoPage = 1
            postVideoForCategoriesArray.clear()
        }


//        if(!first && (_categoriesForPostVideoAppResponse.value.totalRecord ?:0) >= (postVideoForCategoriesArray.size)) return
        if(!first && (postVideoForCategoriesArray.size >= (_categoriesForPostVideoAppResponse.value.totalRecord?:0))) return

        repository.getCategoryData(

            getCategoryRequestModel = GetCategoryRequestModel(
                limit = getCategoryRequestModel?.limit,
                displayLoginUserCategory = getCategoryRequestModel?.displayLoginUserCategory,
                sortDirection = getCategoryRequestModel?.sortDirection,
                sortColumn = getCategoryRequestModel?.sortColumn,
                page = categoryCurrentPageForPostVideoPage,
            )

        ).onEach {

            when(it){

                is DataState.Error -> {
                    _categoriesForPostVideoAppResponse.emit(CategoriesState(isLoading = false, categories = postVideoForCategoriesArray))
                }

                is DataState.Loading -> {
                    _categoriesForPostVideoAppResponse.emit(CategoriesState(isLoading = true, categories = postVideoForCategoriesArray))
                }

                is DataState.Success -> {

                    if(first){
                        caAccountCategoryPage = 1
                        postVideoForCategoriesArray.clear()
                    }

                    postVideoForCategoriesArray.addAll(it.data?.categoryList?: arrayListOf())

                    _categoriesForPostVideoAppResponse.emit(CategoriesState(isLoading = false, categories = postVideoForCategoriesArray, totalRecord = it.data?.totalRecords))
                    categoryCurrentPageForPostVideoPage += 1
                }
            }
        }.launchIn(viewModelScope)


    }

    private fun updateCategoriesDataValue(categoriesId: Int, selected: Boolean){
//        println("selected is $selected")
       val response = _categoriesForPostVideoAppResponse.value.categories.map { if(it.categoryid == categoriesId) it.copy(selectedCategory = selected) else it }

        _categoriesForPostVideoAppResponse.update { it.copy(categories = response) }

        println("response is $response")

//        _categoriesForPostVideoAppResponse.value.categories.filter { categoriesId == it.categoryid }.forEach {
//            println("selected is ${it.selectedCategory}")
//            println("selected is ${selected}")
//            it.selectedCategory = selected
//        }
//        _categoriesForPostVideoAppResponse.update { it }
    }


    data class Item(val id: Int, val value: String)

    private fun findItemsNotInSecondArray(firstArray: List<Category>, secondArray: List<Category>): List<Category> {
        val secondIds = secondArray.map { it.categoryid } // Extract IDs from the second array
        return firstArray.filter { it.categoryid in secondIds }
    }





    }