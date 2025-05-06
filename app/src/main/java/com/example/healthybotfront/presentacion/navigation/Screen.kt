package com.example.healthybotfront.presentacion.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home/{userId}") {
        fun createRoute(userId: Long) = "home/$userId"
    }
    object Profile : Screen("profile/{userId}") {
        fun createRoute(userId: Long) = "profile/$userId"
    }
    object Login : Screen("login")
    object Register : Screen("register")

    object CreateHabit: Screen("createHabit/{userId}") {
        fun createRoute(userId: Long) = "createHabit/$userId"
    }

    object Progress : Screen("progress/{userId}") {
        fun createRoute(userId: Long) = "progress/$userId"
    }

}

