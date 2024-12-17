package com.sqt.lts.ui.profile.state

import coil.compose.AsyncImagePainter
import com.sqt.lts.ui.profile.response.UserProfileResponseModel

data class UserDetailGetState(val isLoading: Boolean?=null,
                              val exception: Exception?=null,
                              var data : UserProfileResponseModel.Data?=null)