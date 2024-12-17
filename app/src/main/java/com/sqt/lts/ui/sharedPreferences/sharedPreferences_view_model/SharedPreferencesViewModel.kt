package com.example.lts.ui.sharedPreferences.sharedPreferences_view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.sqt.lts.navigation.route.LoginRoute
import com.sqt.lts.navigation.route.SplashRoute
import com.sqt.lts.navigation.route.TabRoute
import com.example.lts.ui.sharedPreferences.data.SaveLoginState
import com.example.lts.ui.sharedPreferences.event.SharedPreferencesEvents
import com.example.lts.ui.sharedPreferences.repository.SharedPrefRepository
import com.example.lts.utils.ResponseData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedPreferencesViewModel @Inject constructor(private val sharedPrefRepository: SharedPrefRepository) : ViewModel(){


    private val _isLoginState = MutableStateFlow(SaveLoginState())
    val isLoginState : StateFlow<SaveLoginState> = _isLoginState








    init {
        onEvent(SharedPreferencesEvents.GetLoginState)
        onEvent(SharedPreferencesEvents.CheckAuthentication())
    }



     fun onEvent(event: SharedPreferencesEvents){

         when(event){
             is SharedPreferencesEvents.SetLoginState -> {
                 setLoginState(event.saveLoginState)
                 getUserLoginData()
             }

             is SharedPreferencesEvents.GetLoginState -> {
                 getUserLoginData()
             }

             is SharedPreferencesEvents.CheckAuthentication -> {
                 checkAuthentication(event.isLoading)
             }

             is SharedPreferencesEvents.Logout -> {
                 clearAllData()
             }
         }

     }

    private fun clearAllData(){
        viewModelScope.launch {
            sharedPrefRepository.clearAllData()
        }
    }


    private fun getUserLoginData(){

        _isLoginState.value = _isLoginState.value.copy(response = ResponseData.LOADING)

        viewModelScope.launch {
            try {
                val response = sharedPrefRepository.getLoginState()
                 _isLoginState.value = _isLoginState.value.copy(
                     response = ResponseData.SUCCESS,
                     isLogin = response.isLogin,
                     token = response.token,
                     associationType = response.associationType,
                     associationId = response.associationId)
            }catch (e:Exception){
                _isLoginState.value = _isLoginState.value.copy(response = ResponseData.ERROR)
            }
        }
    }

    private  fun setLoginState(saveLoginStateData: SaveLoginState?=null){

        viewModelScope.launch {

            sharedPrefRepository.saveLoginState(saveLoginStateData)
        }

    }

    private fun checkAuthentication(isLoading: Boolean?) : Any{
        return when(isLoading){
            true -> TabRoute
            false -> LoginRoute
            null -> SplashRoute
        }
    }




}