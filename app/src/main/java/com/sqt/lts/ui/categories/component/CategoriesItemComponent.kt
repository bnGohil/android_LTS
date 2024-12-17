package com.sqt.lts.ui.categories.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lts.ui.categories.data.response.Category
//import com.example.lts.ui.categories.state.Category
import com.sqt.lts.ui.theme.kCardBackgroundColor
import com.example.lts.utils.extainstion.kWhiteW400FS13
import com.example.lts.utils.scaleSize
import com.sqt.lts.custom_component.CustomNetworkImageView
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.ui.theme.kPrimaryColor

@Composable
fun CategoriesItemComponent(category: Category?=null) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 5.dp)
        .clip(RoundedCornerShape(10.dp.scaleSize()))
        .background(kCardBackgroundColor)
        .padding(10.dp),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        if(category?.iscategoryassigned == 1){
            Box(modifier = Modifier.align(Alignment.End)) {
                Icon(Icons.Filled.CheckCircle, "", tint = kPrimaryColor)
            }
        }
        CustomNetworkImageView(modifier = Modifier
            .size(100.dp.scaleSize())
            .clip(CircleShape), imagePath = category?.photourl,
            contentScale = ContentScale.FillBounds)
        Spacer(modifier = Modifier.height(height = 25.dp.scaleSize()))
        Text(text = category?.categoryname ?: "", style = TextStyle.Default.kWhiteW400FS13(), textAlign = TextAlign.Center, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Spacer(modifier = Modifier.height(height = 25.dp.scaleSize()))
    }
}

@Preview(backgroundColor = 1L, showBackground = true)
@Composable
private fun CategoriesItemComponentPreview() {
    LtsTheme {
        CategoriesItemComponent(
            category = Category(
                categoryname = "My category",
                iscategoryassigned = 1
            )
        )
    }
}