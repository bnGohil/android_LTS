package com.sqt.lts.ui.categories.viewModel
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
import com.sqt.lts.ui.categories.state.CategoryTabModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val repository: CategoryRepository) : ViewModel(){

    private val _categoryHomeState = MutableStateFlow(CategoriesState())
    val categoryHomeState : StateFlow<CategoriesState> = _categoryHomeState

    private val _categoryForTrendingState = MutableStateFlow(CategoriesState())
    val categoryForTrendingState : StateFlow<CategoriesState> = _categoryForTrendingState

    private val _categoryCaAccountState = MutableStateFlow(CategoriesState())
    val categoryCaAccountState : StateFlow<CategoriesState> = _categoryCaAccountState



    private val _isAllSelected = mutableStateOf<Boolean>(true)
    val isAllSelected : State<Boolean> = _isAllSelected

    private val _categoryTabState = MutableStateFlow(CategoriesTabState())
    val categoryTabState : StateFlow<CategoriesTabState> = _categoryTabState

    private val _selectedTab = mutableStateOf<CategoryType>(CategoryType.MY_CATEGORY)
    val selectedTab : State<CategoryType> = _selectedTab

    private var categoriesHomeArray = arrayListOf<Category>()
    private var categoriesTrendingArray = arrayListOf<Category>()
    private var caAccountCategoriesArray = arrayListOf<Category>()

    private var _selectedItemArray = MutableStateFlow<List<Category>>(arrayListOf())
    val selectedItemArray : StateFlow<List<Category>> = _selectedItemArray


   private var categoryCurrentPage = 1


    private var categoryCurrentPageForCategoryPage = 1
    private var categoryCurrentPageForTrendingPage = 1
    private var caAccountCategoryPage = 1

    private val _categoryDataState = MutableStateFlow(CategoriesState())
    val categoryDataState : StateFlow<CategoriesState> = _categoryDataState

    private var categoriesArray = arrayListOf<Category>()



















    init {

    }


     fun onEvent(event: CategoriesEvent){

        when(event){

            is CategoriesEvent.CategorySelected -> {
                selectCategory(event.category)
            }

            is CategoriesEvent.LoadCategories -> {
//                getCategoryListData(event.categoryUiState)
            }

            is CategoriesEvent.CategoryAllSelected -> {
                selectAllCategory()
            }

            is CategoriesEvent.GetCategoryTabData -> {
//                getTabList(event.isLogin)
            }

            is CategoriesEvent.SelectedCategoryTabData -> {
//                selectedTabCategories(CategoryTabModel.categoriesType ?:CategoryType.OTHER_CATEGORY)
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
            categoryname = category.categoryname,
            any = category.any,
            categoryid = category.categoryid,
            selectedCategory = category.selectedCategory?.toggle(),
            countresourcecategory = category.countresourcecategory,
            createdby = category.createdby,
            createdon = category.createdon,
            isactive = category.isactive,
            iscategoryassigned = category.iscategoryassigned,
            longdetails = category.longdetails,
            photo = category.photo,
            photourl = category.photourl,
            rownumber = category.rownumber) else e}.toList()

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

        _categoryHomeState.value = _categoryHomeState.value.copy(categories = list)

        _selectedItemArray.value = _categoryHomeState.value.categories.filter { name -> name.selectedCategory == true}.toList()

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

      if(categoryUiState.pagingLoadingType == PagingLoadingType.IS_LOADING && (_categoryHomeState.value.totalRecord?:0) <= (_categoryHomeState.value.categories?.size?:0)) return



      repository.getCategoryData(
          getCategoryRequestModel = GetCategoryRequestModel(
              limit = 10,
              sortDirection = categoryUiState.getCategoryRequestModel?.sortDirection,
              page = categoryCurrentPage,
              displayLoginUserCategory = 1,
              sortColumn = categoryUiState.getCategoryRequestModel?.sortColumn
          )
      ).onEach {
          when(it){
              is DataState.Error -> {
                  _categoryHomeState.value = _categoryHomeState.value.copy(isLoading = false, categories = categoriesHomeArray)
              }
              is DataState.Loading -> {
                  _categoryHomeState.value = _categoryHomeState.value.copy(isLoading = true, categories = categoriesHomeArray)
              }
              is DataState.Success -> {
                  if(_selectedItemArray.value.isNotEmpty()){

                      for (selectedCategory in _selectedItemArray.value){
                          it.data?.categoryList?.forEach {
                                  category: Category -> if(selectedCategory.categoryid == category.categoryid){
                              category.selectedCategory = true
                          }
                          }
                      }

                      if(categoriesHomeArray.isEmpty()){
                          categoriesHomeArray.add(0,Category(categoryname = "All",type = SelectedType.ALL))
                      }
                      if(it.data?.categoryList != null){
                          it.data.categoryList.forEach { category -> if(category.type == null){
                              category.type = SelectedType.ANY
                          } }
                          categoriesHomeArray.addAll(it.data.categoryList)
                      }

                  }else{
                      if(categoriesHomeArray.isEmpty()){
                          categoriesHomeArray.add(0,Category(categoryname = "All", type = SelectedType.ALL))
                      }
                      if(it.data?.categoryList != null){
                          it.data.categoryList.forEach { category -> if(category.type == null){
                              category.type = SelectedType.ANY
                          } }
                          categoriesHomeArray.addAll(it.data.categoryList)
                      }
                      selectAllCategory()
                  }

                  _categoryHomeState.value = _categoryHomeState.value.copy(
                      totalRecord = it.data?.totalRecords,
                      categories = categoriesHomeArray,isLoading = false, currentPage = it.data?.currentPage?:1, totalPages = it.data?.totalPages)
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

        if(isFirst == false && (caAccountCategoriesArray.size >= (_categoryCaAccountState.value.totalRecord?:0))) return

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
}