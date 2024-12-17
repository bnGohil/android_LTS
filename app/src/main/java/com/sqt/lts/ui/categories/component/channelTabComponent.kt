package com.sqt.lts.ui.categories.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lts.ui.categories.data.request.GetCategoryRequestModel
import com.sqt.lts.ui.categories.event.CategoriesEvent
import com.sqt.lts.ui.categories.state.CategoryTabModel
import com.sqt.lts.ui.categories.state.CategoryType
import com.sqt.lts.ui.theme.kPrimaryColor
import com.sqt.lts.ui.theme.kSecondaryTextColor
import com.example.lts.utils.extainstion.kWhiteW400FS13
import com.example.lts.utils.scaleSize
import com.sqt.lts.ui.theme.LtsTheme

@Composable
fun CategoriesTabComponent(onCategoryEvent:(CategoriesEvent) -> Unit,selectedTab:CategoryType?=null,isLoading: Boolean?=false,onChangeTab:(CategoryType?)-> Unit) {

    val categoryTabList = arrayListOf<CategoryTabModel>(
        CategoryTabModel(
            name = "Popular Channels",
            categoriesType = CategoryType.MY_CATEGORY
        ),
        CategoryTabModel(
            name = "Other Categories",
            categoriesType = CategoryType.OTHER_CATEGORY
        )
    )

    Row(modifier = Modifier.fillMaxWidth()) {
        categoryTabList.map {
            Column(modifier = Modifier
                .weight(1F)
                .clickable {
                    if(isLoading == false){
                        onChangeTab(it.categoriesType)
                        onCategoryEvent(CategoriesEvent.SelectedCategoryTabData(it))
                        onCategoryEvent(CategoriesEvent.ClearCategories)
                        onCategoryEvent(CategoriesEvent.GetAllCategoryData(
                            isFirst = true,
                            getCategoryRequestModel = GetCategoryRequestModel(
                                displayLoginUserCategory = if(it.categoriesType == CategoryType.MY_CATEGORY) 1 else 0,
                                sortColumn = "",
                                sortDirection = "",
                                selected = it.categoriesType
                            ),
                        ))
                    }

                }) {
                Spacer(modifier = Modifier.height(10.dp.scaleSize()))
                Text(modifier = Modifier.fillMaxWidth(), text = it.name ?: "", textAlign = TextAlign.Center, style = TextStyle.Default.kWhiteW400FS13().copy(color = if(selectedTab == it.categoriesType) kPrimaryColor else kSecondaryTextColor))
                Spacer(modifier = Modifier.height(15.dp.scaleSize()))
                Divider(modifier = Modifier
                    .height(1.dp.scaleSize())
                    .background(kSecondaryTextColor))
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 1L,)
@Composable
private fun CategoriesTabComponentPreview() {
    LtsTheme {
        CategoriesTabComponent(onCategoryEvent = {},
            onChangeTab = {},
            selectedTab = CategoryType.MY_CATEGORY
            )
    }
}