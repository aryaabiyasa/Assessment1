package com.aryama0073.tentangdirikita.navigation

sealed class Screen(val route: String) {
    data object Home: Screen("MainScreen")
}