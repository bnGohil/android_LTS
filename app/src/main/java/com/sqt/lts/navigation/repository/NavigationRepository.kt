package com.example.lts.navigation.repository

import androidx.navigation.NavHostController

class NavigationRepository {

    suspend  fun updateNavigation(naviController: NavHostController,pageName: String){ naviController.navigate(pageName) }

}