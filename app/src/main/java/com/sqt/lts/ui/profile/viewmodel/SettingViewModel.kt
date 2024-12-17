package com.sqt.lts.ui.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lts.base.BaseCommonResponseModel
import com.example.lts.utils.network.DataState
import com.sqt.lts.repository.SettingRepository
import com.sqt.lts.ui.profile.event.ProfileEvent
import com.sqt.lts.ui.profile.request.UserDetailUpdateRequestModel
import com.sqt.lts.ui.profile.state.UserDetailGetState
import com.sqt.lts.utils.enums.ProfileSettingEnums
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(val settingRepository: SettingRepository) : ViewModel(){


    private var _userDetailAppResponse = MutableStateFlow(UserDetailGetState())
    val userDetailAppResponse : StateFlow<UserDetailGetState> = _userDetailAppResponse

    private val _updateUserDetail = Channel<DataState<BaseCommonResponseModel.Data?>>()
    val updateUserDetail = _updateUserDetail.receiveAsFlow()



    fun onEvent(event:ProfileEvent){
        when(event){
            ProfileEvent.UserDetailGetEvent -> {
                getUserDetail()
            }

            is ProfileEvent.UpdateTextDataEvent -> {
                updateString(event.text?:"",event.type)
            }

            is ProfileEvent.UpdateUserDetailEvent -> {
                userUpdateDetail(event.data)
            }
        }
    }


    private fun getUserDetail(){
        settingRepository.getUserDetail().onEach {
            when(it){
                is DataState.Error -> {
                    _userDetailAppResponse.value = _userDetailAppResponse.value.copy(isLoading = false, exception = it.exception)
                }
                DataState.Loading -> {
                    _userDetailAppResponse.value = _userDetailAppResponse.value.copy(isLoading = true)
                }
                is DataState.Success -> {
                    _userDetailAppResponse.value = _userDetailAppResponse.value.copy(isLoading = false, data = it.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateString(text: String,type:ProfileSettingEnums?=null){

        when(type){

            ProfileSettingEnums.FIRST_NAME -> {
                _userDetailAppResponse.value = _userDetailAppResponse.value.copy(data = _userDetailAppResponse.value.data?.copy(fname = text))
            }

            ProfileSettingEnums.LAST_NAME -> {
                _userDetailAppResponse.value = _userDetailAppResponse.value.copy(data = _userDetailAppResponse.value.data?.copy(lname = text))
            }

            ProfileSettingEnums.EMAIL -> {
                _userDetailAppResponse.value = _userDetailAppResponse.value.copy(data = _userDetailAppResponse.value.data?.copy(email = text))
            }

            ProfileSettingEnums.ADD_1 -> {
                _userDetailAppResponse.value = _userDetailAppResponse.value.copy(data = _userDetailAppResponse.value.data?.copy(add1 = text))
            }

            ProfileSettingEnums.ADD_2 -> {
                _userDetailAppResponse.value = _userDetailAppResponse.value.copy(data = _userDetailAppResponse.value.data?.copy(add2 = text))
            }

            ProfileSettingEnums.ZIP_CODE -> {
                _userDetailAppResponse.value = _userDetailAppResponse.value.copy(data = _userDetailAppResponse.value.data?.copy(zipcode = text))
            }

            ProfileSettingEnums.CITY -> {
                _userDetailAppResponse.value = _userDetailAppResponse.value.copy(data = _userDetailAppResponse.value.data?.copy(city = text))
            }

            ProfileSettingEnums.STATE -> {
                _userDetailAppResponse.value = _userDetailAppResponse.value.copy(data = _userDetailAppResponse.value.data?.copy(state = text))
            }

            ProfileSettingEnums.FACEBOOK -> {
                _userDetailAppResponse.value = _userDetailAppResponse.value.copy(data = _userDetailAppResponse.value.data?.copy(facebookprofile = text))
            }

            ProfileSettingEnums.TWITTER -> {
                _userDetailAppResponse.value = _userDetailAppResponse.value.copy(data = _userDetailAppResponse.value.data?.copy(twitterprofile = text))
            }

            ProfileSettingEnums.INSTAGRAM -> {
                _userDetailAppResponse.value = _userDetailAppResponse.value.copy(data = _userDetailAppResponse.value.data?.copy(instagramprofile = text))
            }

            null -> {}
        }
    }

    private fun userUpdateDetail(data : UserDetailUpdateRequestModel?){
        settingRepository.updateUserDetail(data).onEach {

            when(it){

                is DataState.Error -> {
                    _updateUserDetail.send(it)
                }

                DataState.Loading -> {
                    _updateUserDetail.send(it)
                }

                is DataState.Success -> {
                    getUserDetail()
                    _updateUserDetail.send(it)
                }

            }

        }.launchIn(viewModelScope)
    }

}
