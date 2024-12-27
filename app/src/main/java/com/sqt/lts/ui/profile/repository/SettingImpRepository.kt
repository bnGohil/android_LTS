package com.sqt.lts.ui.profile.repository

import android.content.Context
import android.webkit.MimeTypeMap
import com.sqt.lts.datasource.remote.RestApiService
import com.sqt.lts.datasource.remote.utils.safeApiCallWithApiStatus
import com.sqt.lts.repository.SettingRepository
import com.sqt.lts.ui.profile.request.UserDetailUpdateRequestModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class SettingImpRepository @Inject constructor(val restApiService: RestApiService,val context: Context) : SettingRepository{

    override fun getUserDetail() = safeApiCallWithApiStatus { restApiService.getUserDetail() }

    override fun updateUserDetail(userDetailUpdateRequestModel: UserDetailUpdateRequestModel?) = safeApiCallWithApiStatus {

        val mediaType = "text/plain".toMediaTypeOrNull()

        var mimePhotoType = ""
        val openPhotoInputStream = userDetailUpdateRequestModel?.photoUri?.let { value ->
                mimePhotoType = context.contentResolver.getType(value) ?: ""
                context.contentResolver.openInputStream(value) }

        var mimeBannerType = ""
        val openBannerInputStream = userDetailUpdateRequestModel?.bannerUri?.let { value ->
            mimeBannerType = context.contentResolver.getType(value) ?: ""
            context.contentResolver.openInputStream(value) }


        val filePhotoName = "PROFILE_${System.currentTimeMillis()}." + MimeTypeMap.getSingleton().getExtensionFromMimeType(mimePhotoType)

        val fileBannerName = "BANNER_${System.currentTimeMillis()}." + MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeBannerType)

        val profilePic = openPhotoInputStream?.let {
            MultipartBody.Part.createFormData("photo", filePhotoName, it.readBytes().toRequestBody(mimePhotoType.toMediaTypeOrNull()))
        } ?: run {
            val file = "".toRequestBody(MultipartBody.FORM)
            MultipartBody.Part.createFormData("photo", "", file)
        }
        val bannerPic = openBannerInputStream?.let {
            MultipartBody.Part.createFormData("banner", fileBannerName, it.readBytes().toRequestBody(mimeBannerType.toMediaTypeOrNull()))
        } ?: run {
            val file = "".toRequestBody(MultipartBody.FORM)
            MultipartBody.Part.createFormData("banner", "", file)
        }

        restApiService.updateUserDetail(
                    fName = (userDetailUpdateRequestModel?.fname ?: "").toRequestBody(mediaType),
                    lName = (userDetailUpdateRequestModel?.lname ?: "").toRequestBody(mediaType),
                    mobileNO = (userDetailUpdateRequestModel?.phoneno ?: "").toRequestBody(mediaType),
                    add1 = (userDetailUpdateRequestModel?.add1 ?: "").toRequestBody(mediaType),
                    add2 = (userDetailUpdateRequestModel?.add2 ?: "").toRequestBody(mediaType),
                    country = (userDetailUpdateRequestModel?.country ?: "").toRequestBody(mediaType),
                    state = (userDetailUpdateRequestModel?.state ?: "").toRequestBody(mediaType),
                    city = (userDetailUpdateRequestModel?.city ?: "").toRequestBody(mediaType),
                    zipcode = (userDetailUpdateRequestModel?.zipcode ?: "").toRequestBody(mediaType),
                    facebookProfile = (userDetailUpdateRequestModel?.facebookprofile ?: "").toRequestBody(mediaType),
                    instagramProfile = (userDetailUpdateRequestModel?.instagramprofile ?: "").toRequestBody(mediaType),
                    twitterProfile = (userDetailUpdateRequestModel?.twitterprofile ?: "").toRequestBody(mediaType),
                    banner = bannerPic,
                    photo = profilePic,

            )
    }

}
