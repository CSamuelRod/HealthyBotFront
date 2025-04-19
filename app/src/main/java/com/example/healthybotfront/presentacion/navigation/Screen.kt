package com.example.healthybotfront.presentacion.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home/{userId}") {
        fun createRoute(userId: Long) = "home/$userId"
    }
    object Login : Screen("login")
    object Register : Screen("register")
}
