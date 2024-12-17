package com.example.lts.dummy.models

import androidx.navigation.NavType
import com.example.lts.ui.tab.data.NavigationDrawer
import kotlinx.serialization.Serializable

@Serializable
data class DummyData(
    val type: NavigationDrawer?= null
)