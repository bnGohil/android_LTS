package com.sqt.lts.ui.channels.state

data class SelectedChannelState(val selectedChannel:SelectedChannel?=null)

enum class SelectedChannel {POPULAR_CHANNEL,OTHER_CHANNEL}

data class SelectedChannelModel(val selectedChannel:SelectedChannel,val name: String?=null)

