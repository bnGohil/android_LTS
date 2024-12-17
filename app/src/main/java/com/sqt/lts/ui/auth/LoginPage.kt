package com.example.lts.ui.auth

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
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sqt.lts.R
import com.example.lts.custom_component.CustomButton
import com.example.lts.custom_component.CustomTextFromFiled
import com.sqt.lts.navigation.route.ForgotRoute
//import com.sqt.lts.navigation.route.LoginRoute
import com.sqt.lts.navigation.route.RegisterRoute
import com.sqt.lts.navigation.route.TabRoute
import com.example.lts.ui.auth.data.request.LoginUserRequestModel
import com.example.lts.ui.auth.data.response.LoginUserResponseModel
import com.example.lts.ui.auth.event.AuthenticationEvent
import com.example.lts.ui.sharedPreferences.data.SaveLoginState
import com.example.lts.ui.sharedPreferences.event.SharedPreferencesEvents
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.ui.theme.kBackGround
import com.sqt.lts.ui.theme.kPrimaryColor
import com.sqt.lts.ui.theme.kWhite
import com.sqt.lts.utils.extainstion.appFontFamily
import com.example.lts.utils.extainstion.kSecondaryTextColorW500FS15
import com.example.lts.utils.extainstion.kWhiteW500FS17
import com.example.lts.utils.network.DataState
import com.example.lts.utils.scaleFont
import com.example.lts.utils.scaleSize

@Composable
fun LoginPage(
    onAuthenticationEvent:(AuthenticationEvent) -> Unit,
    onSharedPreferencesEvent:(SharedPreferencesEvents) -> Unit,
    navController: NavHostController,
    loginUserResponseModel: DataState<LoginUserResponseModel.LoginResData?>?=null
) {

    var email by remember { mutableStateOf<String?>("casenior@yopmail.com") }
    var password by remember { mutableStateOf<String?>("Admin@123") }
    val isPassword = remember { mutableStateOf<Boolean>(false) }
    var isLoading by remember { mutableStateOf<Boolean?>(false) }

    val context = LocalContext.current

    LaunchedEffect(key1 = loginUserResponseModel, block = {
       loginUserResponseModel?.let {
           when(it){
               is DataState.Error -> {
                   isLoading = false
                   Toast.makeText(context,it.exception.message,Toast.LENGTH_SHORT).show()
               }
               is DataState.Loading -> {
                   isLoading = true
               }
               is DataState.Success -> {
                   isLoading = false
                   onSharedPreferencesEvent(SharedPreferencesEvents.SetLoginState(saveLoginState = SaveLoginState(isLogin = true, token = it.data?.token?:"", associationId = it.data?.associationId, associationType = it.data?.associationtype)))
                   navController.navigate(TabRoute){
                       popUpTo<LoginRoute>(){
                           inclusive = true
                       }
                   }
                   Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
               }
           }
       }
    })


    Scaffold(
        containerColor = kBackGround
    ) {
          paddingValues -> Column(
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
                .width(200.dp.scaleSize()),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.height(50.dp.scaleSize()))
            Text(text = "Welcome To Listen to Senier", style = TextStyle.Default.kWhiteW500FS17())
            Spacer(modifier = Modifier.height(15.dp.scaleSize()))
            Text(text = "Welcome to listen to Senior Enter your\n email password to get started", style = TextStyle.Default.kSecondaryTextColorW500FS15().copy(textAlign = TextAlign.Center))
            Spacer(modifier = Modifier.height(30.dp.scaleSize()))
            CustomTextFromFiled(
                titleText = "Email", keyboardType = KeyboardType.Email,
                value = email?:"",
                onValueChange = { email = it },

            )
            Spacer(modifier = Modifier.height(20.dp.scaleSize()))
            CustomTextFromFiled(
                value = password?:"",
                onValueChange = { password = it },
                titleText = "Password", suffix = {
                    IconButton(
                        modifier = Modifier
                            .height(24.dp.scaleSize())
                            .width(24.dp.scaleSize()),
                        onClick = {
                            isPassword.value =! isPassword.value
                        }) {
                        Image(painter = painterResource(id = if(isPassword.value) R.drawable.eye_password_show else R.drawable.eye_password_hide), contentDescription = "", colorFilter = ColorFilter.tint(kWhite, BlendMode.SrcIn))
                    }
                }, imeAction = ImeAction.Done, keyboardType = KeyboardType.Password,
                visualTransformation = if(isPassword.value) VisualTransformation.None else PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(10.dp.scaleSize()))
            Text(text = "Forgot Password?", style = TextStyle.Default.kSecondaryTextColorW500FS15().copy(
                textAlign = TextAlign.Right
            ), modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(ForgotRoute)
                })
            Spacer(modifier = Modifier.height(50.dp.scaleSize()))

            CustomButton(
                isLoading = isLoading,
                btnText = "Sign In", onClick = {

                  if(email == null || email?.isEmpty() == true){
                      Toast.makeText(context,"Please enter email",Toast.LENGTH_SHORT).show() // in Activity
                      return@CustomButton
                  }

                  if(password == null || password?.isEmpty() == true){
                      Toast.makeText(context,"Please enter password",Toast.LENGTH_SHORT).show() // in Activity
                      return@CustomButton
                  }


                onAuthenticationEvent(AuthenticationEvent.LoginUserEvent(
                    LoginUserRequestModel(
                        password = password?:"",
                        username = email?:""
                    )
                ))
            })
            Spacer(modifier = Modifier.height(10.dp.scaleSize()))
            Text(text = "OR", style = TextStyle.Default.kWhiteW500FS17())
            Spacer(modifier = Modifier.height(10.dp.scaleSize()))
            CustomButton(btnText = "Guest User", onClick = {
                navController.navigate(TabRoute)
            })
            Spacer(modifier = Modifier.height(10.dp.scaleSize()))
            SpannedText(firstText = "Don’t have an account?", secondText = "Sign Up", modifier = Modifier.clickable {
                navController.navigate(RegisterRoute)
            })


        }


    }
}

@Composable
fun SpannedText(firstText:String?= null,secondText:String?= null,modifier: Modifier){
    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = kWhite,
                fontSize = 14.sp.scaleFont(),
                fontFamily = appFontFamily,
                fontWeight = FontWeight.W400
            )
        ){
            append(firstText)
        }


        withStyle(
            style = SpanStyle(
                color = kPrimaryColor,
                fontSize = 14.sp.scaleFont(),
                fontFamily = appFontFamily,
                fontWeight = FontWeight.W700
            )
        ){
            append("\t $secondText")
        }
    }

    Text(text = annotatedString,modifier)
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LtsTheme {
        LoginPage(onAuthenticationEvent = {}, navController = rememberNavController(), onSharedPreferencesEvent = {})
    }
}