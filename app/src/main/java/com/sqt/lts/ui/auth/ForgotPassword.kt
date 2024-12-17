package com.sqt.lts.ui.auth
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sqt.lts.R
import com.example.lts.base.BaseCommonResponseModel
import com.example.lts.custom_component.CustomButton
import com.example.lts.custom_component.CustomTextFromFiled
//import com.sqt.lts.navigation.route.LoginRoute
import com.example.lts.ui.auth.data.request.ForgotPasswordRequestModel
import com.example.lts.ui.auth.event.AuthenticationEvent
import com.sqt.lts.ui.theme.LtsTheme
//import com.exampxle.lts.navigation.route.AppRoutesName
import com.sqt.lts.ui.theme.kBackGround
import com.example.lts.utils.extainstion.kSecondaryTextColorW500FS15
import com.example.lts.utils.extainstion.kWhiteW500FS17
import com.example.lts.utils.network.DataState
import com.example.lts.utils.scaleSize

@Composable
fun ForgotPassword(
    navController: NavController,
    onAuthenticationEvent: (AuthenticationEvent)-> Unit,
    forgotData: DataState<BaseCommonResponseModel.Data?>?=null
) {

    var email by remember {
        mutableStateOf<String?>("casenior@yopmail.com")
    }

    println("forgotData:$forgotData")

    val isLoading = DataState.Loading == forgotData.let { it }

    val context = LocalContext.current

    LaunchedEffect(key1 = forgotData, block = {

        forgotData?.let {
            when(it){

                is DataState.Error -> {
                    Toast.makeText(context,it.exception.message,Toast.LENGTH_LONG).show()
                }

                is DataState.Loading -> {

                }

                is DataState.Success -> {
                    Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
                    navController.popBackStack()
                }
            }
        }

    })





    Scaffold(
        containerColor = kBackGround
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .padding(horizontal = 20.dp.scaleSize())
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())

        ) {
            Image(painter = painterResource(id = R.drawable.app_icon), contentDescription = "app_icon", modifier = Modifier
                .height(70.dp.scaleSize())
                .width(200.dp.scaleSize()), contentScale = ContentScale.FillBounds)
            Spacer(modifier = Modifier.height(50.dp.scaleSize()))
            Text(text = "Forgot Password?\n", style = TextStyle.Default.kWhiteW500FS17())
            Spacer(modifier = Modifier.height(15.dp.scaleSize()))
            Text(text = "Enter your email to reset it. Check your inbox for a link to create a new password.\n" + "\n", style = TextStyle.Default.kSecondaryTextColorW500FS15().copy(textAlign = TextAlign.Center))
            Spacer(modifier = Modifier.height(30.dp.scaleSize()))
            CustomTextFromFiled(
                titleText = "Email ID",
                value = email,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
                onValueChange = { email = it }
            )

            Spacer(modifier = Modifier.height(10.dp.scaleSize()))
            CustomButton(btnText = "Reset Password",
                isLoading=isLoading,
                onClick = {

                if(email == null || email?.isEmpty() == true){
                    Toast.makeText(context,"Please Enter First Email",Toast.LENGTH_SHORT).show()
                    return@CustomButton
                }

                onAuthenticationEvent(AuthenticationEvent.ForgotPasswordEvent(
                    forgotPasswordRequestModel = ForgotPasswordRequestModel(email = email)
                ))
            })
            Spacer(modifier = Modifier.height(20.dp.scaleSize()))
            Text(text = "Back to Login", style = TextStyle.Default.kSecondaryTextColorW500FS15().copy(textAlign = TextAlign.Right), modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(LoginRoute)
                })
        }


    }

}

@Preview(showBackground = true)
@Composable
private fun ForgotPasswordPreview() {
   LtsTheme {
       ForgotPassword(
           navController = rememberNavController(),
           onAuthenticationEvent = {}
       )
   }
}