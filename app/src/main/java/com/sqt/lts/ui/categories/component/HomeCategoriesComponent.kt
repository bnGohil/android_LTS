package com.example.lts.ui.categories.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.lts.enums.PagingLoadingType
import com.example.lts.ui.categories.data.response.Category
import com.example.lts.ui.categories.data.ui_state.CategoryUiState
import com.sqt.lts.ui.categories.event.CategoriesEvent
import com.sqt.lts.ui.categories.state.CategoriesState
import com.example.lts.ui.shimmer.ShimmerEffectBox
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.ui.theme.kCardBackgroundColor
import com.sqt.lts.ui.theme.kPrimaryColor
import com.example.lts.utils.extainstion.kWhiteW400FS13
import com.example.lts.utils.extainstion.kWhiteW500FS17
import com.example.lts.utils.scaleSize

@Composable
fun HomeCategoriesComponent(
    categoriesState: CategoriesState? =null,
    onCategoryEvent:(CategoriesEvent) -> Unit,
    onCategoriesClick :(Category?) -> Unit,
    listState: LazyListState = rememberLazyListState(),
    ) {

    val isLoading = (categoriesState?.isLoading == true && categoriesState.categories.isEmpty())
    val isPaging = (categoriesState?.categories?.isNotEmpty() == true && categoriesState.isLoading)

    LazyRow(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 20.dp.scaleSize()), state = listState)
    {

        items(if(isLoading) 5 else (categoriesState?.categories?.size ?:0)){


            ShimmerEffectBox(
                modifier = if(isLoading) Modifier
                    .padding(5.dp.scaleSize())
                    .size(height = 40.dp.scaleSize(), width = 100.dp.scaleSize())
                    .clip(CircleShape) else Modifier,
                isShow = isLoading
            ) {


                if(!isLoading){
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 5.dp.scaleSize())
                            .clip(CircleShape)
                            .background(if (categoriesState?.categories?.get(it)?.selectedCategory == true) kPrimaryColor else kCardBackgroundColor)
                            .padding(horizontal = 10.dp.scaleSize(), vertical = 5.dp.scaleSize())
                            .clickable {
                                onCategoriesClick(categoriesState?.categories?.get(it))
                            }
                    ) {



                        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                modifier = Modifier.size(25.dp.scaleSize()).clip(CircleShape),
                                painter = rememberAsyncImagePainter(model = categoriesState?.categories?.get(it)?.photourl ?:""),
                                contentDescription = null)
                            Spacer(modifier = Modifier.width(10.dp.scaleSize()))
                            Text(
                                text =  categoriesState?.categories?.get(it)?.categoryname?:"",
                                style = TextStyle.Default.kWhiteW400FS13()
                            )
                        }
                    }
                }
            }

            if(isPaging && (it.plus(1) == categoriesState?.categories?.size)){
                ShimmerEffectBox( modifier = Modifier
                    .size(height = 40.dp.scaleSize(), width = 100.dp.scaleSize())
                    .clip(CircleShape),
                    isShow = true
                ) {}
            }

        }






    }
}

@Preview
@Composable
private fun HomeCategoriesComponentPreview() {
    LtsTheme {
        HomeCategoriesComponent(
            onCategoriesClick = {},
            categoriesState = CategoriesState(
            categories = arrayListOf<Category>(
                Category(categoryname = "Category1", categoryid = 1),
                Category(categoryname = "Category2", categoryid = 2),
                Category(categoryname = "Category3", categoryid = 3),
                Category(categoryname = "Category4", categoryid = 4),
                Category(categoryname = "Category5", categoryid = 5),
                Category(categoryname = "Category6", categoryid = 6),
            )
        ), onCategoryEvent = {})
    }

}