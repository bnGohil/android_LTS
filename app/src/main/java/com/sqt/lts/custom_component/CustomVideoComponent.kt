package com.example.lts.custom_component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sqt.lts.R
import com.example.lts.utils.extainstion.kPrimaryColorW500FS15
import com.example.lts.utils.extainstion.kWhiteW400FS13
import com.example.lts.utils.extainstion.kWhiteW500FS17
import com.example.lts.utils.scaleSize

@Composable
fun CustomVideoComponent(title: String,description: String) {

    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp


    Column {
        Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
            Text(text = title, style = TextStyle.Default.kWhiteW500FS17())
            Text(text = description, style = TextStyle.Default.kWhiteW500FS17())
        }
        LazyRow {
            items(10){
                Row(modifier = Modifier
                    .width((screenWidth / (1.5F)))
                    .padding(vertical = 10.dp.scaleSize())) {
                    Image(modifier = Modifier
                        .height(100.dp.scaleSize())
                        .width(150.dp.scaleSize())
                        .clip(RoundedCornerShape(10.dp)), painter = painterResource(id = R.drawable.youtube_thumbnile), contentScale = ContentScale.FillBounds, contentDescription = "youtube_thumbnile")
                    Spacer(modifier = Modifier.width(10.dp.scaleSize()))
                    Column {
                        Text(
                            text = "Coding Lesson 14 - Review of Concepts High School / Computer Science / Programming Languages Program",
                            style = TextStyle.Default.kWhiteW400FS13(),
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(text = arrayListOf<String>("Education","Education","Education","Education").joinToString(separator = "\t•\t") { element -> element }
                            ?:"", style = TextStyle.Default.kPrimaryColorW500FS15(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                            )
                    }
                    Spacer(modifier = Modifier.width(5.dp.scaleSize()))
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun CustomVideoComponentPreview() {
    CustomVideoComponent(title = "Title", description = "Description")
}