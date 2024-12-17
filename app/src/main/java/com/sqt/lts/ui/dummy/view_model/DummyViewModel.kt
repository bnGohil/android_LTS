package com.example.lts.ui.dummy.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lts.ui.dummy.event.DummyEvent
import com.example.lts.ui.dummy.repository.DummyRepository
import com.sqt.lts.ui.dummy.state.DummyVideoState
import com.example.lts.utils.ResponseData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class DummyViewModel @Inject constructor(private val dummyRepository: DummyRepository) : ViewModel() {


    private val _videoState = MutableStateFlow(DummyVideoState())
    val videoState : StateFlow<DummyVideoState> = _videoState


    init {
        onEvent(DummyEvent.GetDummyVideoListData)
    }



    fun onEvent(event: DummyEvent){
        when(event){
            DummyEvent.GetDummyVideoListData -> {
                getDummyVideoListData()
            }
        }
    }


    private fun getDummyVideoListData(){
        _videoState.value = _videoState.value.copy(ResponseData.LOADING)
        try {
            viewModelScope.launch {
                val response = dummyRepository.loadJsonFromAssets()
                _videoState.value = _videoState.value.copy(ResponseData.SUCCESS, videoList = response)
            }
        }catch (e:Exception){
            _videoState.value = _videoState.value.copy(ResponseData.ERROR, videoList = arrayListOf())
        }
    }

}