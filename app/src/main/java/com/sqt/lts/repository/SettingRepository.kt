package com.sqt.lts.repository

import com.example.lts.base.BaseCommonResponseModel
import com.example.lts.utils.network.DataState
import com.sqt.lts.ui.profile.request.UserDetailUpdateRequestModel
import com.sqt.lts.ui.profile.response.UserProfileResponseModel
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    fun getUserDetail(): Flow<DataState<UserProfileResponseModel.Data?>>
    fun updateUserDetail(userDetailUpdateRequestModel:UserDetailUpdateRequestModel?): Flow<DataState<BaseCommonResponseModel.Data?>>
}