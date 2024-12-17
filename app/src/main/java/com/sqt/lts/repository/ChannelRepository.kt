package com.sqt.lts.repository
import android.net.Uri
import com.example.lts.base.BaseCommonResponseModel
import com.example.lts.utils.network.DataState
import com.sqt.lts.ui.channels.data.request.ChannelRequestModel
import com.sqt.lts.ui.channels.data.response.ChannelDataResponseModel
import com.sqt.lts.ui.channels.data.response.GetChannelDetailDataModel
import kotlinx.coroutines.flow.Flow

interface ChannelRepository {
     fun getChannelData(channelRequestModel: ChannelRequestModel?): Flow<DataState<GetChannelDetailDataModel.Data?>>
     fun followChannel(channelId: Int): Flow<DataState<BaseCommonResponseModel.Data?>>
     fun unfollowChannel(channelId: Int): Flow<DataState<BaseCommonResponseModel.Data?>>
     fun createChannel(channelName: String?,photo:Uri?,banner: Uri?) : Flow<DataState<BaseCommonResponseModel.Data?>>
     fun updateChannel(channelId: Int,channelName: String?,photo:Uri?,banner: Uri?) : Flow<DataState<BaseCommonResponseModel.Data?>>
     fun getChannelDetail(id: Int) : Flow<DataState<ChannelDataResponseModel.ChannelData?>>
}

