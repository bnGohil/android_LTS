package com.sqt.lts.ui.profile.event

import com.sqt.lts.ui.profile.request.UserDetailUpdateRequestModel
import com.sqt.lts.utils.enums.ProfileSettingEnums

sealed class ProfileEvent {
    data object UserDetailGetEvent : ProfileEvent()
    data class UpdateTextDataEvent(val text: String?=null, val type:ProfileSettingEnums?=null) : ProfileEvent()
    data class UpdateUserDetailEvent(val data : UserDetailUpdateRequestModel?) : ProfileEvent()
}