package com.example.lts.custom_component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.ui.theme.kBackGround
import com.sqt.lts.ui.theme.kCardBackgroundColor
import com.sqt.lts.ui.theme.kWhite
import com.example.lts.utils.extainstion.kWhiteW500FS17
import com.example.lts.utils.scaleSize
import com.sqt.lts.ui.theme.kBackGround

@Composable
fun CustomTopBar(
    title: String? = null, navHostController: NavHostController,
    content: @Composable() (BoxScope.() -> Unit)? =null) {
    Column(modifier = Modifier.background(kBackGround)) {
        Row {
            IconButton(onClick = {
                navHostController.popBackStack()
            }) {
                Icon(
                    modifier = Modifier.size(30.dp.scaleSize()),
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "back",
                    tint = kWhite
                )
            }
            Box(
                modifier = Modifier.weight(1F)
            ) {
                Text(
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .align(Alignment.Center),
                    text = title ?: "", style = TextStyle.Default.kWhiteW500FS17()
                )
            }
            if(content != null){
                Box(content = content)
            }else{
                Box(modifier = Modifier.size(10.dp.scaleSize()))
            }

        }
        Spacer(modifier = Modifier.height(5.dp.scaleSize()))
        Spacer(modifier = Modifier
            .height(1.dp.scaleSize())
            .fillMaxWidth()
            .background(kCardBackgroundColor))
    }
}

@Preview
@Composable
private fun CustomTopBarPreview() {
  LtsTheme {
      CustomTopBar(
          title = "User",
          navHostController = rememberNavController()
      )
  }
}