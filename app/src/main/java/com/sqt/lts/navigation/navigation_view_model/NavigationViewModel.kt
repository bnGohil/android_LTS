package com.example.lts.navigation.navigation_view_model
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.lts.navigation.event.NavigationEvent
import com.example.lts.navigation.repository.NavigationRepository
//import com.example.lts.navigation.route.AppRoutesName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(private val navigationRepository: NavigationRepository): ViewModel(){



    private var _startDestination = mutableStateOf<String>("")
    val startDestination : MutableState<String> = _startDestination


    init {
//        onEvent(NavigationEvent.UpdateDestination(TabRoute))
    }


    private fun updateState(destination: String){
        _startDestination.value = destination
    }

    fun onEvent(navigationEvent: NavigationEvent){
        when(navigationEvent){
            is NavigationEvent.GoToNextPage -> {
                changePage(navigationEvent.naviHostController,navigationEvent.name)

            }

            is NavigationEvent.UpdateDestination -> {
                updateState(navigationEvent.name)
            }
        }
    }


    private fun changePage(naviController: NavHostController, pageName: String){
        viewModelScope.launch {
            navigationRepository.updateNavigation(naviController,pageName)
        }
    }

}