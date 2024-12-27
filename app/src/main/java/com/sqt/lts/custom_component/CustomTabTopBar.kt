
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import com.example.lts.navigation.navigation_view_model.NavigationViewModel
import com.example.lts.ui.sharedPreferences.data.SaveLoginState
import com.example.lts.ui.tab.bottom_bar.BottomNavBarItem
import com.example.lts.ui.tab.event.TabEvent
import com.example.lts.ui.tab.state.TabState
import com.example.lts.ui.tab.tab_view_model.TabViewModel
import com.example.lts.utils.extainstion.kPrimaryColorW400FS15
import com.example.lts.utils.extainstion.kSecondaryTextColorW500FS15
import com.example.lts.utils.extainstion.kWhiteW400FS13
import com.example.lts.utils.extainstion.kWhiteW500FS17
import com.example.lts.utils.scaleSize
import com.sqt.lts.ui.theme.kCardBackgroundColor
import com.sqt.lts.ui.theme.kPrimaryColor

import com.sqt.lts.R
import com.sqt.lts.ui.profile.state.UserDetailGetState
import com.sqt.lts.ui.tab.state.SelectedTabAndSearch
import com.sqt.lts.ui.theme.kWhite

@Composable
fun CustomTabTopBar(
    saveLoginState: SaveLoginState?=null,
    userDetailUiState: UserDetailGetState? =null,
    navHostController: NavHostController,
    tabState: TabState? =null,
    onClick: () -> Unit,
    selectedTabEvent: (TabEvent) -> Unit,
) {




    val isSearch = remember { mutableStateOf<Boolean>(false) }
    val searchText = remember { mutableStateOf<String>("") }

//    Row(
//        modifier = Modifier.padding(horizontal = 10.dp),
//        horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
//        Box(modifier = Modifier
//            .weight(1F)
//            .border(
//                shape = CircleShape,
//                border = BorderStroke(
//                    color = kWhite,
//                    width = 1.dp,
//                )
//            )
//            .padding(vertical = 10.dp)) {
//
//
//            if(searchText.value.isEmpty()){
//                Text("Search", style = TextStyle.Default.kWhiteW500FS17(), modifier = Modifier.padding(horizontal = 14.dp.scaleSize()))
//            }
//
//            BasicTextField(
//                singleLine = true,
//                modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
//                keyboardOptions = KeyboardOptions(
//                    imeAction = ImeAction.Done,
//                    keyboardType = KeyboardType.Text
//                ),
//                value = searchText.value,
//                onValueChange = {
//                    searchText.value = it
//                    onValueChange(it)
//                },
//                textStyle = TextStyle.Default.kSecondaryTextColorW500FS15(),
//                cursorBrush = SolidColor(kWhite)
//            )
//
//
//        }
//        OutlinedIconButton(
//            border = BorderStroke(color = kWhite, width = 1.dp),
//            onClick = {
//                isSearch.value = !isSearch.value
//            }) {
//            Icon(Icons.Default.Clear, "clear", tint = kWhite)
//        }
//    }


    Column() {
        Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp.scaleSize(), horizontal = 10.dp.scaleSize())
            ) {
                Image(
                    modifier = Modifier
                        .size(50.dp.scaleSize())
                        .clickable {
                            onClick.invoke()
                        },
                    painter = painterResource(id = R.drawable.subtract),
                    contentDescription = "",
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.width(20.dp.scaleSize()))

                if(saveLoginState?.isLogin == true){
                    if(tabState?.selectedTab ==  BottomNavBarItem.home){
                        Column(
                            modifier = Modifier.weight(1F),
                        ) {
                            Text(
                                text = userDetailUiState?.data?.displayname ?: "",
                                style = TextStyle.Default.kWhiteW500FS17(),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = userDetailUiState?.data?.email ?: "",
                                style = TextStyle.Default.kWhiteW500FS17(),
                                textAlign = TextAlign.Center
                            )

                        }
                    }else{
                        Text(modifier = Modifier.weight(1F),text = tabState?.selectedTab?.itemName?:"", style = TextStyle.Default.kWhiteW500FS17(), textAlign = TextAlign.Center)
                    }

                }else{
                    Text(modifier = Modifier.weight(1F),text = if(tabState?.selectedTab ==  BottomNavBarItem.home) "Listen to\n" + "Seniors" else (tabState?.selectedTab?.itemName ?: ""), style = TextStyle.Default.kWhiteW500FS17(), textAlign = TextAlign.Center)
                }

                if(saveLoginState?.isLogin == true){
                    Row() {


                        Image(
                            painter = painterResource(id = R.drawable.notification),
                            contentDescription = "",
                            modifier = Modifier.size(25.dp.scaleSize()),
                            contentScale = ContentScale.FillBounds
                        )
                        Spacer(modifier = Modifier.width(15.dp.scaleSize()))

                    }

                    Image(
                        painter = if(!isSearch.value)  painterResource(id = R.drawable.search_icon) else painterResource(R.drawable.close),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(color = Color.White, BlendMode.SrcIn),
                        modifier = Modifier
                            .size(25.dp.scaleSize())
                            .clickable {
                                isSearch.value = !isSearch.value
                                selectedTabEvent(TabEvent.GlobalSearchReq(
                                    bottomNavBarItem = tabState?.selectedTab,
                                    isSearch = isSearch.value
                                ))
                            },
                        contentScale = ContentScale.FillBounds
                    )
                }else{
                    Box(modifier = Modifier
                        .border(border = BorderStroke(color = kPrimaryColor, width = 1.dp.scaleSize()))
                        .clickable {
                            navHostController.navigate(LoginRoute)
//                        navigationViewModel.onEvent(NavigationEvent.UpdateDestination(AppRoutesName.kLoginRoute))
                        }) {
                        Text(text = "Login", style = TextStyle.Default.kPrimaryColorW400FS15(), modifier = Modifier.padding(horizontal = 15.dp.scaleSize(), vertical = 10.dp.scaleSize()))
                    }
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
    CustomTabTopBar(
        navHostController = rememberNavController(),
        onClick = {},
        selectedTabEvent = {},
    )
}