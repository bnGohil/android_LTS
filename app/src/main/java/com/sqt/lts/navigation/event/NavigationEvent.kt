package com.example.lts.navigation.event

import androidx.navigation.NavHostController

sealed class NavigationEvent {

   data class GoToNextPage(val naviHostController: NavHostController,val name: String) : NavigationEvent()
   data class UpdateDestination(val name: String) : NavigationEvent()

}