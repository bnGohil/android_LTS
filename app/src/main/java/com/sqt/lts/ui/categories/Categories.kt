package com.sqt.lts.ui.categories

import android.annotation.SuppressLint
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.lts.ui.categories.data.request.GetCategoryRequestModel
import com.sqt.lts.ui.categories.component.CategoriesItemComponent
import com.sqt.lts.ui.categories.component.CategoriesTabComponent
import com.example.lts.ui.categories.data.response.Category
import com.sqt.lts.ui.categories.event.CategoriesEvent
import com.sqt.lts.ui.categories.state.CategoriesState
import com.sqt.lts.ui.categories.state.CategoryType
import com.example.lts.ui.shimmer.ShimmerEffectBox

import com.sqt.lts.ui.theme.LtsTheme


@OptIn(UnstableApi::class)
@SuppressLint("RememberReturnType")
@Composable
fun Categories(
    onCategoryEvent: (CategoriesEvent) -> Unit,
    categoriesState: CategoriesState?=null) {

    val gridState = rememberLazyGridState()

    var selectedTab = remember { mutableStateOf<CategoryType?>(CategoryType.MY_CATEGORY) }

    val isLoading = (categoriesState?.isLoading == true && categoriesState.categories.isEmpty())
    val isPagingLoading = (categoriesState?.isLoading == true && categoriesState.categories.isNotEmpty() == true)


    if(!isLoading){
        LaunchedEffect(gridState,isPagingLoading) {
            snapshotFlow { Pair(isPagingLoading,gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index) }
                .collect { (isPagingLoading,visibleIndices) ->
                    if(!isPagingLoading && gridState.layoutInfo.totalItemsCount >= 10 && visibleIndices?.plus(1) == gridState.layoutInfo.totalItemsCount){
                        onCategoryEvent(CategoriesEvent.GetAllCategoryData(
                            isFirst = false,
                            getCategoryRequestModel = GetCategoryRequestModel(
                                selected = selectedTab.value,
                                displayLoginUserCategory = if(selectedTab.value == CategoryType.MY_CATEGORY) 1 else 0,
                            ),

                        ))
                    }
                }
        }
    }

    LaunchedEffect(Unit) {
        onCategoryEvent(CategoriesEvent.GetAllCategoryData(getCategoryRequestModel = GetCategoryRequestModel(displayLoginUserCategory = if(selectedTab.value == CategoryType.MY_CATEGORY) 1 else 0, sortColumn = "", sortDirection = ""), isFirst = true,))
    }



    Column {

        CategoriesTabComponent(onCategoryEvent = onCategoryEvent, isLoading = isLoading, selectedTab = selectedTab.value, onChangeTab = { selectedTab.value = it })
        LazyVerticalGrid(state = gridState,columns = GridCells.Fixed(2)) {
            items(if(isLoading) 10 else (categoriesState?.categories?.size?:0)){
                ShimmerEffectBox(
                    isShow = isLoading,
                    modifier = if(isLoading) Modifier.fillMaxWidth().height(200.dp).padding(5.dp) else Modifier
                ) {
                    println("countresourcecategory : ${categoriesState?.categories?.get(it)?.countresourcecategory}")
                    CategoriesItemComponent(categoriesState?.categories?.get(it))
                }
            }

            if(isPagingLoading){
                item{
                  ShimmerEffectBox(
                      isShow = true,
                      modifier = Modifier.fillMaxWidth().height(200.dp).padding(5.dp)
                  ) {}
                }
            }
        }


    }

}

@Preview(backgroundColor = 1L, showBackground = true)
@Composable
private fun CategoriesPreview() {
    LtsTheme {
        Categories(
            onCategoryEvent = {},
            categoriesState = CategoriesState(
                categories = arrayListOf(
                    Category(categoryname = "Category1",iscategoryassigned = 1),
                    Category(categoryname = "Category2",iscategoryassigned = 1),
                    Category(categoryname = "Category3",iscategoryassigned = 1),
                    Category(categoryname = "Category4",iscategoryassigned = 1),
                    Category(categoryname = "Category5"),
                    Category(categoryname = "Category6"),
                    Category(categoryname = "Category7"),
                    Category(categoryname = "Category8"),
                    Category(categoryname = "Category9"),
                    Category(categoryname = "Category10"),
                )
            ),
        )
    }
}