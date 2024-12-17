package com.example.lts.ui.auth.event

import com.example.lts.ui.auth.data.request.CreateUserRequestModel
import com.example.lts.ui.auth.data.request.ForgotPasswordRequestModel
import com.example.lts.ui.auth.data.request.LoginUserRequestModel
import com.example.lts.ui.auth.state.AuthenticationState

sealed class AuthenticationEvent {

    data class LoginUserEvent(val loginUserRequestModel: LoginUserRequestModel): AuthenticationEvent()

    data class SetAppStateEvent(val appState: AuthenticationState) : AuthenticationEvent()

    data class LogoutStateDataEvent(val appState: AuthenticationState) : AuthenticationEvent()

    data class ForgotPasswordEvent(val forgotPasswordRequestModel: ForgotPasswordRequestModel) : AuthenticationEvent()

    data object GetCountryList : AuthenticationEvent()
    data object GetUserSaveData : AuthenticationEvent()

    data class CreateUser(val createUserRequestModel: CreateUserRequestModel):AuthenticationEvent()

}