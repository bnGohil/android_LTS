package com.sqt.lts.datasource.remote

import com.example.lts.base.BaseCommonResponseModel
import com.example.lts.base.BaseResponse
import com.example.lts.ui.auth.data.request.CreateUserRequestModel
import com.example.lts.ui.auth.data.request.ForgotPasswordRequestModel
import com.example.lts.ui.auth.data.request.LoginUserRequestModel
import com.example.lts.ui.auth.data.response.CountryData
import com.example.lts.ui.auth.data.response.LoginUserResponseModel
import com.example.lts.ui.categories.data.response.CategoryResponseModel
import com.google.gson.annotations.SerializedName
import com.sqt.lts.base.BaseTermDataModel
import com.sqt.lts.ui.channels.data.request.FollowAndUnFollowRequestModel
import com.sqt.lts.ui.channels.data.response.ChannelDataResponseModel
import com.sqt.lts.ui.channels.data.response.GetChannelDetailDataModel
import com.sqt.lts.ui.history.request.AddAndRemoveRequestModel
import com.sqt.lts.ui.history.request.HistoryAndWatchListRequestModel
import com.sqt.lts.ui.history.response.HistoryResponseModel
import com.sqt.lts.ui.history.response.WatchListResponseModel
import com.sqt.lts.ui.post_video.data.request.TermCategoryRequestModel
import com.sqt.lts.ui.profile.response.UserProfileResponseModel
import com.sqt.lts.ui.trending.data.response.TrendingResponseModel
import com.sqt.lts.ui.trending.data.response.TrendingVideoDetailModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface RestApiService {

    @POST("user/login")
    suspend fun login(@Body loginUserRequestModel : LoginUserRequestModel) : LoginUserResponseModel

    @POST("user/forgot-password")
    suspend fun forgotPassword(@Body forgotPasswordRequestModel: ForgotPasswordRequestModel) : BaseCommonResponseModel

    @GET("admin/auth/countrylist")
    suspend fun getCountryList(): CountryResponseModel

    @POST("user/register")
    suspend fun createUser(@Body createUserRequestModel: CreateUserRequestModel) : BaseCommonResponseModel

    @POST("resource/list")
    @FormUrlEncoded
    suspend fun getResourceData(
        @Field ("mediatype") mediatype: String?,
        @Field ("categoryids") categoryIds: String?,
        @Field ("exceptresourceids") exceptResourceIds: String?,
        @Field ("displayloginuseruploaded") displayLoginUserUploaded: Int?,
        @Field ("channelid") channelId: Int?,
        @Field ("limit") limit: Int?,
        @Field ("page") page: Int?,
        @Field ("sortcolumn") sortColumn: String?,
        @Field ("sortdirection") sortDirection: String?,
    ):TrendingResponseModel

    @POST("category/list")
    @FormUrlEncoded
    suspend fun getCategoryData(
        @Field("limit") limit : Int?,
        @Field("page") page : Int?,
        @Field("displayloginusercategory") displayLoginUserCategory : Int?,
        @Field("sortcolumn") sortColumn : String?,
        @Field("sortdirection") sortDirection : String?,
    ): CategoryResponseModel

    @POST("channel/list")
    @FormUrlEncoded
    suspend fun getChannelData(
        @Field("limit") limit : Int?,
        @Field("page") page : Int?,
        @Field("myfollowingchannel") myFollowingChannel : Int?,
        @Field("mycreatedchannel") myCreatedChannel : Int?,
        @Field("sortcolumn") sortColumn : String?,
        @Field("exceptchannelids") exceptChannelIds : String?,
        @Field("categoryids") categoryIds : String?,
        @Field("sortdirection") sortDirection : String?,
    ): GetChannelDetailDataModel

    @GET("resource/view")
    suspend fun getTrendingVideoDetail(@Query("id") id: Int?):TrendingVideoDetailModel

    @POST("channel/follow-channel")
    suspend fun followChannel(@Body followAndUnFollowRequestModel: FollowAndUnFollowRequestModel): BaseCommonResponseModel

    @POST("channel/unfollow-channel")
    suspend fun unfollowChannel(@Body followAndUnFollowRequestModel: FollowAndUnFollowRequestModel): BaseCommonResponseModel

    @GET("user/view-profile")
    suspend fun getUserDetail() : UserProfileResponseModel

    @Multipart
    @POST("user/update-profile")
    suspend fun updateUserDetail(
    @Part banner : MultipartBody.Part?,
    @Part photo : MultipartBody.Part?,
    @Part("fname") fName : RequestBody,
    @Part("lname") lName : RequestBody,
    @Part("phoneno") mobileNO : RequestBody,
    @Part("add1") add1 : RequestBody,
    @Part("add2") add2 : RequestBody,
    @Part("country") country : RequestBody,
    @Part("state") state : RequestBody,
    @Part("city") city : RequestBody,
    @Part("zipcode") zipcode : RequestBody,
    @Part("facebookprofile") facebookProfile : RequestBody,
    @Part("instagramprofile") instagramProfile : RequestBody,
    @Part("twitterprofile") twitterProfile : RequestBody,
    ): BaseCommonResponseModel

    @Multipart
    @POST("channel/add")
    suspend fun createChannel(@Part photo: MultipartBody.Part?,@Part banner: MultipartBody.Part?,@Part("channelname") channelName : RequestBody): BaseCommonResponseModel

    @Multipart
    @POST("channel/update")
    suspend fun updateChannel(
        @Part photo: MultipartBody.Part?,
        @Part banner: MultipartBody.Part?,
        @Part("channelname") channelName : RequestBody,
        @Part("id") id : RequestBody?,
        ): BaseCommonResponseModel

    @GET("channel/view")
    suspend fun viewChannelDetail(@Query("id") id: Int?):ChannelDataResponseModel

    @POST("watchlist/list")
    suspend fun watchListData(@Body historyAndWatchListRequestModel:HistoryAndWatchListRequestModel?):WatchListResponseModel

    @POST("history/list")
    suspend fun historyListData(@Body historyAndWatchListRequestModel:HistoryAndWatchListRequestModel?):HistoryResponseModel

    @POST("watchlist/add")
    suspend fun addWatchList(@Body addAndRemoveRequestModel:AddAndRemoveRequestModel?) : BaseCommonResponseModel

    @POST("watchlist/remove")
    suspend fun removeWatchList(@Body addAndRemoveRequestModel:AddAndRemoveRequestModel?) : BaseCommonResponseModel

    @POST("projectterm/projecttermbytermcategory")
    suspend fun projectTermByTermCategory(@Body termCategoryRequestModel:TermCategoryRequestModel) : BaseTermDataModel
}

data class CountryResponseModel(
    @SerializedName("data")
    override val data: ArrayList<CountryData>?= arrayListOf()
):BaseResponse<ArrayList<CountryData>>()