package com.example.lts.ui.trending.data

import com.sqt.lts.ui.channels.data.response.ChannelData
import java.time.LocalDateTime
import java.time.LocalTime

data class TrendingItemResponseData(val dateTime: LocalTime?=null,
                                    val name:String?=null,
                                    val type:AppPagesType?=null,
                                    val listCategory:List<String>?= arrayListOf(),
                                    val channelList: List<ChannelData> = arrayListOf(),
                                    val view : Number?=null,
                                    val days:Number?=null,
                                    var isWatch:Boolean?=false)


enum class AppPagesType{
    CATEGORIES,TRENDING,CHANNEL
}
