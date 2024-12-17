package com.example.lts.ui.auth.auth_view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lts.base.BaseCommonResponseModel
import com.example.lts.enums.Status
import com.example.lts.repository.AuthRepository
import com.example.lts.ui.auth.data.request.CreateUserRequestModel
import com.example.lts.ui.auth.data.request.ForgotPasswordRequestModel
import com.example.lts.ui.auth.data.request.LoginUserRequestModel
import com.example.lts.ui.auth.data.response.CategoryData
import com.example.lts.ui.auth.data.response.GetCountryDataModel
import com.example.lts.ui.auth.data.response.LoginUserResponseModel
import com.example.lts.ui.auth.event.AuthenticationEvent
import com.example.lts.ui.auth.state.CountryDataState
import com.example.lts.ui.categories.data.response.CategoryResponseModel
import com.example.lts.utils.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel(){


    private var _loginState = Channel<DataState<LoginUserResponseModel.LoginResData?>>()
    val loginState = _loginState.receiveAsFlow()

    private val _forgotPasswordState = Channel<DataState<BaseCommonResponseModel.Data?>>()
    val forgotPasswordState = _forgotPasswordState.receiveAsFlow()

    private val _createUserState = Channel<DataState<BaseCommonResponseModel.Data?>>()
    val createUserState = _createUserState.receiveAsFlow()


    private val _getCountryList = MutableStateFlow(CountryDataState())
    val getCountryList : StateFlow<CountryDataState> = _getCountryList

//    private val _getCategoryList = MutableStateFlow(CategoryResponseModel.CategoryResponseData())
//    val getCategoryList : StateFlow<CategoryResponseModel.CategoryResponseData> = _getCategoryList





    init {
        onEvent(AuthenticationEvent.GetUserSaveData)
    }

    fun onEvent(authenticationEvent: AuthenticationEvent){

        when(authenticationEvent){

            is AuthenticationEvent.LoginUserEvent -> {
                login(authenticationEvent.loginUserRequestModel)
            }

            is AuthenticationEvent.LogoutStateDataEvent -> {

            }

            is AuthenticationEvent.SetAppStateEvent -> {

            }

            is AuthenticationEvent.ForgotPasswordEvent -> {
                forgotPassword(authenticationEvent.forgotPasswordRequestModel)
            }

            is AuthenticationEvent.GetCountryList -> {
                getCountryListData()
            }

            is AuthenticationEvent.CreateUser -> {
                createUser(authenticationEvent.createUserRequestModel)
            }

            is AuthenticationEvent.GetUserSaveData -> {

            }
        }
    }


    private fun forgotPassword(forgotPasswordRequestModel: ForgotPasswordRequestModel){
        repository.forgotPassword(forgotPasswordRequestModel).onEach {
            _forgotPasswordState.send(it)
        }.launchIn(viewModelScope)
    }

     private fun login(loginUserRequestModel: LoginUserRequestModel){
        repository.login(loginUserRequestModel).onEach {
            println("IS LOGIN TIME $it")
            _loginState.send(it)
        }.launchIn(viewModelScope)

    }

    private fun getCountryListData(){


        repository.getCountryList().onEach { dataState ->

            when(dataState){



                is DataState.Error -> {
                    _getCountryList.value = _getCountryList.value.copy(
                        status = Status.ERROR,
                        getCountryDataModel = GetCountryDataModel(data = arrayListOf())
                    )
                }

                is DataState.Loading -> {
                    _getCountryList.value = _getCountryList.value.copy(
                        status = Status.LOADING,
                        getCountryDataModel = GetCountryDataModel(data = arrayListOf())
                    )
                }
                is DataState.Success -> {
                    _getCountryList.update { it.copy(
                        status = Status.SUCCESS,
                        getCountryDataModel = GetCountryDataModel(data = dataState.data)
                    ) }

                }



            }

        }.launchIn(viewModelScope)

    }

    private fun createUser(createUserRequestModel: CreateUserRequestModel){
        repository.createUser(createUserRequestModel).onEach {
            _createUserState.send(it)
        }.launchIn(viewModelScope)
    }

    private fun getSaveUserData(){

    }

}

