package com.example.healthybotfront.presentacion.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")

    /*
    object Principal : Screen("principal/{userId}") {
        fun createRoute(userId: String) = "principal/$userId" // Crea la ruta con el userId
    }*/

    object Home:Screen("home")
    object Register : Screen("register")/*
    object Operations : Screen("operations")
    object Bills : Screen("bills")
    object Income : Screen("income")
    object CreateAccount : Screen("createAccount/{userId}") {
        fun createRoute(userId: String) = "createAccount/$userId"
    }
    object UpdateUser : Screen("principal/update/{id}") {
        fun createRoute(userId: String): String = "principal/update/$userId"
    }
    object DetailProfile : Screen("detailProfile/{userId}") {
        fun createRoute(userId: String) = "detailProfile/$userId"
    }
    object DetailAccount : Screen("detailAccount/{accountId}") {
        fun createRoute(accountId: String) = "detailAccount/$accountId"
    }
    // Nueva ruta para editar cuenta
    object EditAccount : Screen("editAccount/{accountId}") {
        fun createRoute(accountId: String) = "editAccount/$accountId"
    }*/
}