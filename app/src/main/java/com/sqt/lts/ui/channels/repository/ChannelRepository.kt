package com.example.lts.ui.channels.repository

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import com.example.lts.base.BaseCommonResponseModel
import com.sqt.lts.datasource.remote.RestApiService
import com.example.lts.datasource.remote.utils.safeApiCallWithApiStatus
import com.example.lts.utils.network.DataState
import com.sqt.lts.repository.ChannelRepository
import com.sqt.lts.ui.channels.data.request.ChannelRequestModel
import com.sqt.lts.ui.channels.data.request.FollowAndUnFollowRequestModel
import com.sqt.lts.ui.channels.data.response.GetChannelDetailDataModel
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject


class ChannelRepositoryImp @Inject constructor(
   private val restApiService: RestApiService,
   private val context: Context
) : ChannelRepository{

    override fun getChannelData(channelRequestModel: ChannelRequestModel?) = safeApiCallWithApiStatus {
            restApiService.getChannelData(
                        limit=channelRequestModel?.limit,
                        page=channelRequestModel?.page,
                        myFollowingChannel=channelRequestModel?.myFollowingChannel,
                        myCreatedChannel=channelRequestModel?.myCreatedChannel,
                        sortColumn=channelRequestModel?.sortColumn,
                        exceptChannelIds=channelRequestModel?.exceptChannelIds,
                        categoryIds=channelRequestModel?.categoryIds,
                        sortDirection=channelRequestModel?.sortDirection,
            )
        }

    override fun followChannel(channelId: Int) = safeApiCallWithApiStatus { restApiService.followChannel(followAndUnFollowRequestModel = FollowAndUnFollowRequestModel(channelId)) }

    override fun unfollowChannel(channelId: Int) = safeApiCallWithApiStatus { restApiService.unfollowChannel(followAndUnFollowRequestModel = FollowAndUnFollowRequestModel(channelId)) }

    override fun createChannel(
        channelName: String?,
        photo: Uri?,
        banner: Uri?
    ) = safeApiCallWithApiStatus {

        val mediaType = "text/plain".toMediaTypeOrNull()

        var mimePhotoType = ""
        val photoStream = photo?.let { value ->
            mimePhotoType = context.contentResolver.getType(value)?:""
            context.contentResolver.openInputStream(value)
        }

        var bannerMimeType = ""

        val bannerStream = banner?.let {
            value ->
            bannerMimeType = context.contentResolver.getType(value)?:""
            context.contentResolver.openInputStream(value)
        }

        var bannerFileName = "BANNER_${System.currentTimeMillis()}." + MimeTypeMap.getSingleton().getExtensionFromMimeType(bannerMimeType)

        val filePhotoName = "PROFILE_${System.currentTimeMillis()}." + MimeTypeMap.getSingleton().getExtensionFromMimeType(mimePhotoType)

        val profilePic = photoStream?.let {
            MultipartBody.Part.createFormData("photo",filePhotoName,it.readBytes().toRequestBody(mimePhotoType.toMediaTypeOrNull()))
        }?:run {
            val file = "".toRequestBody(MultipartBody.FORM)
            MultipartBody.Part.createFormData("photo","",file)
        }

        val banner = bannerStream?.let {
            MultipartBody.Part.createFormData("banner",bannerFileName,it.readBytes().toRequestBody(bannerMimeType.toMediaTypeOrNull()))
        }?:run {
            val file = "".toRequestBody(MultipartBody.FORM)
            MultipartBody.Part.createFormData("banner","",file)
        }

        restApiService.createChannel(channelName =  (channelName ?: "").toRequestBody(mediaType), banner = banner, photo = profilePic)
    }

    override fun updateChannel(
        channelId: Int,
        channelName: String?,
        photo: Uri?,
        banner: Uri?
    ) = safeApiCallWithApiStatus {

        val mediaType = "text/plain".toMediaTypeOrNull()

        var mimePhotoType = ""

        val photoStream = photo?.let { value ->
            mimePhotoType = context.contentResolver.getType(value)?:""
            context.contentResolver.openInputStream(value)
        }

        var bannerMimeType = ""

        val bannerStream = banner?.let {
                value ->
            bannerMimeType = context.contentResolver.getType(value)?:""
            context.contentResolver.openInputStream(value)
        }

        var bannerFileName = "BANNER_${System.currentTimeMillis()}." + MimeTypeMap.getSingleton().getExtensionFromMimeType(bannerMimeType)

        val filePhotoName = "PROFILE_${System.currentTimeMillis()}." + MimeTypeMap.getSingleton().getExtensionFromMimeType(mimePhotoType)

        val profilePic = photoStream?.let {
            MultipartBody.Part.createFormData("photo",filePhotoName,it.readBytes().toRequestBody(mimePhotoType.toMediaTypeOrNull()))
        }?:run {
            val file = "".toRequestBody(MultipartBody.FORM)
            MultipartBody.Part.createFormData("photo","",file)
        }

        val banner = bannerStream?.let {
            MultipartBody.Part.createFormData("banner",bannerFileName,it.readBytes().toRequestBody(bannerMimeType.toMediaTypeOrNull()))
        }?:run {
            val file = "".toRequestBody(MultipartBody.FORM)
            MultipartBody.Part.createFormData("banner","",file)
        }

        restApiService.updateChannel(id= channelId.toString().toRequestBody(mediaType), photo=profilePic, banner = banner, channelName =  (channelName ?: "").toRequestBody(mediaType))
    }

    override fun getChannelDetail(id: Int) = safeApiCallWithApiStatus { restApiService.viewChannelDetail(id) }


}