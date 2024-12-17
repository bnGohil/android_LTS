package com.example.lts.ui.auth.state

import com.example.lts.enums.Status
import com.example.lts.ui.auth.data.response.GetCountryDataModel

data class CountryDataState(var status: Status?=null,val getCountryDataModel: GetCountryDataModel?=null)