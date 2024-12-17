package com.sqt.lts.ui.auth.repository
import com.sqt.lts.datasource.remote.RestApiService
import com.example.lts.datasource.remote.utils.safeApiCallWithApiStatus
import com.example.lts.repository.AuthRepository
import com.example.lts.ui.auth.data.request.CreateUserRequestModel
import com.example.lts.ui.auth.data.request.ForgotPasswordRequestModel
import com.example.lts.ui.auth.data.request.LoginUserRequestModel
import javax.inject.Inject



class AuthenticationRepositoryImp @Inject constructor(
    private val restApiService: RestApiService,
) : AuthRepository {
    override fun login(loginUserRequestModel: LoginUserRequestModel) = safeApiCallWithApiStatus { restApiService.login(loginUserRequestModel) }
    override fun forgotPassword(forgotPasswordRequestModel: ForgotPasswordRequestModel) = safeApiCallWithApiStatus { restApiService.forgotPassword(forgotPasswordRequestModel) }
    override  fun getCountryList() = safeApiCallWithApiStatus { restApiService.getCountryList() }
    override fun createUser(createUserRequestModel: CreateUserRequestModel) = safeApiCallWithApiStatus { restApiService.createUser(createUserRequestModel) }
}