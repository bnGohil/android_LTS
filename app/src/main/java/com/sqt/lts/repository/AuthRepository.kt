package com.example.lts.repository

import com.example.lts.base.BaseCommonResponseModel
import com.example.lts.ui.auth.data.request.CreateUserRequestModel
import com.example.lts.ui.auth.data.request.ForgotPasswordRequestModel
import com.example.lts.ui.auth.data.request.LoginUserRequestModel
import com.example.lts.ui.auth.data.response.CountryData
import com.example.lts.ui.auth.data.response.LoginUserResponseModel
import com.example.lts.utils.network.DataState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
      fun login(loginUserRequestModel: LoginUserRequestModel):Flow<DataState<LoginUserResponseModel.LoginResData?>>
      fun forgotPassword(forgotPasswordRequestModel: ForgotPasswordRequestModel):Flow<DataState<BaseCommonResponseModel.Data?>>
      fun getCountryList(): Flow<DataState<ArrayList<CountryData>?>>
      fun createUser(createUserRequestModel: CreateUserRequestModel) : Flow<DataState<BaseCommonResponseModel.Data?>>
}