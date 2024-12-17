package com.example.lts.ui.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sqt.lts.R
import com.example.lts.base.BaseCommonResponseModel
import com.example.lts.custom_component.CustomButton
import com.example.lts.custom_component.CustomCountryDropdown
import com.example.lts.custom_component.CustomTextFromFiled
import com.example.lts.enums.Status
//import com.sqt.lts.navigation.route.LoginRoute
import com.example.lts.ui.auth.data.request.CreateUserRequestModel
import com.example.lts.ui.auth.data.response.CountryData
import com.example.lts.ui.auth.event.AuthenticationEvent
import com.example.lts.ui.auth.state.CountryDataState
//import com.example.lts.navigation.route.AppRoutesName
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
fun Register(navController: NavHostController,countryDataState: CountryDataState?=null, authenticationViewModel:(AuthenticationEvent) -> Unit, createUserResponseModel: DataState<BaseCommonResponseModel.Data?>?=null) {

    var isCheckBoxSelected by remember { mutableStateOf<Boolean>(false) }
    var isCreateUserLoading by remember { mutableStateOf<Boolean?>(null) }
    var country by remember { mutableStateOf<CountryData?>(null) }
    var email by remember { mutableStateOf<String?>(null) }
    var fName by remember { mutableStateOf<String?>(null) }
    var lName by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current




    LaunchedEffect(key1 = Unit) {
        authenticationViewModel(AuthenticationEvent.GetCountryList)
    }




    LaunchedEffect(key1 = createUserResponseModel) {

        createUserResponseModel?.let {
            when(it){
                is DataState.Error -> {
                    Toast.makeText(context,it.exception.message,Toast.LENGTH_SHORT).show()
                    isCreateUserLoading = false
                }
                is DataState.Loading -> {
                    isCreateUserLoading = true
                }
                is DataState.Success -> {
                    isCreateUserLoading = false
                    navController.popBackStack()
                    Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                }
            }
        }

    }





    val isLoading = countryDataState?.status == Status.LOADING



    Scaffold(
        containerColor = kBackGround,
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp.scaleSize())
                .fillMaxWidth()
                .padding(paddingValue)
                .padding(top = 20.dp.scaleSize()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(painter = painterResource(id = R.drawable.app_icon), contentDescription = "app_icon", modifier = Modifier
                .height(70.dp.scaleSize())
                .width(200.dp.scaleSize()),
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.height(height = 30.dp.scaleSize()))

            Text(text = "Sign Up", style = TextStyle.Default.kWhiteW500FS17())
            Spacer(modifier = Modifier.height(height = 10.dp.scaleSize()))

            Text(text = "Welcome to listen to Senior ! Start by creating \nyour free account.", style = TextStyle.Default.kSecondaryTextColorW500FS15().copy(textAlign = TextAlign.Center))
            Spacer(modifier = Modifier.height(height = 30.dp.scaleSize()))

            CustomTextFromFiled(value = fName?:"", titleText = "Full Name", keyboardType = KeyboardType.Text, onValueChange = { fName = it })
            Spacer(modifier = Modifier.height(height = 20.dp.scaleSize()))

            CustomTextFromFiled(titleText = "Last Name", keyboardType = KeyboardType.Text,onValueChange = { lName = it }, value = lName?:"")
            Spacer(modifier = Modifier.height(height = 20.dp.scaleSize()))

            CustomTextFromFiled(titleText = "Email ID", keyboardType = KeyboardType.Email, imeAction = ImeAction.Done,onValueChange = {
                email = it
            }, value = email?:"")
            Spacer(modifier = Modifier.height(height = 20.dp.scaleSize()))

            CustomCountryDropdown(
                hintText = "Select country", title = "Country",
                onItemSelected = {
                    country = it
                },
                isLoading =isLoading, countryList = countryDataState?.getCountryDataModel?.data)
            Spacer(modifier = Modifier.height(height = 20.dp.scaleSize()))

//            CustomTextFromFiled(titleText = "Phone No", keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done,
//                onValueChange = {},
//                prefix = {
//                CustomCountryCodeDropdown(countryCode = CountryModel(countryName = "+91"))
//            })
//            Spacer(modifier = Modifier.height(height = 10.dp.scaleSize()))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                Checkbox(checked = isCheckBoxSelected, onCheckedChange = {
                    isCheckBoxSelected = it
                }, modifier = Modifier.padding(0.dp))
                val buildAnnotatedString = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = kWhite, fontWeight = FontWeight.W600, fontFamily = appFontFamily, fontSize = 14.sp.scaleFont())){
                        append("Please accept ")
                    }
                    withStyle(style = SpanStyle(color = kPrimaryColor, fontWeight = FontWeight.W600, fontFamily = appFontFamily, fontSize = 14.sp.scaleFont())){
                        append("privacy-policy ")
                    }
                    withStyle(style = SpanStyle(color = kWhite, fontWeight = FontWeight.W600, fontFamily = appFontFamily, fontSize = 14.sp.scaleFont())){
                        append("Please accept ")
                    }
                }
                Text(text = buildAnnotatedString)
            }
            CustomButton(btnText = "Sign Up", onClick = {

                if(fName == null || fName?.isEmpty() == true){
                    Toast.makeText(context,"Please add first name",Toast.LENGTH_SHORT).show()
                    return@CustomButton
                }

                if(lName == null || lName?.isEmpty() == true){
                    Toast.makeText(context,"Please add last name",Toast.LENGTH_SHORT).show()
                    return@CustomButton
                }

                if(email == null || email?.isEmpty() == true){
                    Toast.makeText(context,"Please add Email",Toast.LENGTH_SHORT).show()
                    return@CustomButton
                }

                if(country == null || country?.countryname?.isEmpty() == true){
                    Toast.makeText(context,"Please add Country",Toast.LENGTH_SHORT).show()
                    return@CustomButton
                }

                if(!isCheckBoxSelected){
                    Toast.makeText(context,"Please Accept Privacy Policy",Toast.LENGTH_SHORT).show()
                    return@CustomButton
                }

                authenticationViewModel(AuthenticationEvent.CreateUser(
                    createUserRequestModel = CreateUserRequestModel(country = country!!.countryname, email = email, fname = fName, lname =  lName)
                ))
            },isLoading = isCreateUserLoading)
            Spacer(modifier = Modifier.height(10.dp.scaleSize()))
            SpannedText(firstText = "Already have an account?", secondText = "Sign In", modifier = Modifier.clickable {
                navController.navigate(LoginRoute)
            })


        }
    }


}
@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    LtsTheme {
        Register(rememberNavController(), authenticationViewModel = {})
    }
}

