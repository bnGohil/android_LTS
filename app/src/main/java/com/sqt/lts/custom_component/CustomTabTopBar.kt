
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import com.example.lts.navigation.navigation_view_model.NavigationViewModel
import com.example.lts.ui.sharedPreferences.data.SaveLoginState
import com.example.lts.ui.tab.bottom_bar.BottomNavBarItem
import com.example.lts.ui.tab.tab_view_model.TabViewModel
import com.example.lts.utils.extainstion.kPrimaryColorW400FS15
import com.example.lts.utils.extainstion.kWhiteW500FS17
import com.example.lts.utils.scaleSize
import com.sqt.lts.ui.theme.kCardBackgroundColor
import com.sqt.lts.ui.theme.kPrimaryColor

import com.sqt.lts.R

@Composable
fun CustomTabTopBar(
    saveLoginState: SaveLoginState?=null,
    navHostController: NavHostController,
    onClick: () -> Unit,
) {




    val context = LocalContext.current


    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp.scaleSize(), horizontal = 10.dp.scaleSize())
        ) {
            Image(
                modifier = Modifier.size(50.dp.scaleSize()).clickable {
                    onClick.invoke()
                },
                painter = painterResource(id = R.drawable.subtract),
                contentDescription = "",
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(20.dp.scaleSize()))
//            Text(modifier = Modifier.weight(1F),text = if(tabState.selectedTab ==  BottomNavBarItem.home) "Listen to\n" + "Seniors" else (tabState.selectedTab?.itemName ?: ""), style = TextStyle.Default.kWhiteW500FS17(), textAlign = TextAlign.Center)
//            if(state.isLogin == true){
//                Column(modifier = Modifier.weight(1F)) {
//                    Text(text = "Bhadresh Gohil", style = TextStyle.Default.kWhiteW700FS20())
//                    Text(text = "bhadreshGohil@gmail.com", style = TextStyle.Default.kWhiteW700FS20())
//                }
//            }else{
//                Text(modifier = Modifier.weight(1F),text = if(tabState.selectedTab ==  BottomNavBarItem.home) "Listen to\n" + "Seniors" else (tabState.selectedTab?.itemName ?: ""), style = TextStyle.Default.kWhiteW700FS20(), textAlign = TextAlign.Center)
//            }

            if(saveLoginState?.isLogin == true){
                Row() {


                    Image(
                        painter = painterResource(id = R.drawable.notification),
                        contentDescription = "",
                        modifier = Modifier.size(25.dp.scaleSize()),
                        contentScale = ContentScale.FillBounds
                    )
                    Spacer(modifier = Modifier.width(15.dp.scaleSize()))
                    Image(
                        painter = painterResource(id = R.drawable.search_icon),
                        contentDescription = "",
                        modifier = Modifier.size(25.dp.scaleSize()),
                        contentScale = ContentScale.FillBounds
                    )
                }
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
        onClick = {}
    )
}