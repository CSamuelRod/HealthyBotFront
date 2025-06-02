package com.example.healthybotfront.presentacion.navigation

/**
 * Representa las rutas de navegación disponibles en la aplicación.
 * Cada objeto dentro de esta sealed class define una ruta con su patrón y,
 * si aplica, un método para crear la ruta con argumentos concretos.
 */
sealed class Screen(val route: String) {

    /**
     * Ruta para la pantalla principal (Home), que requiere un userId.
     * @param userId Id del usuario para personalizar la ruta.
     * @return Ruta completa con el userId insertado.
     */
    object Home : Screen("home/{userId}") {
        fun createRoute(userId: Long) = "home/$userId"
    }

    /**
     * Ruta para la pantalla de perfil, que requiere un userId.
     * @param userId Id del usuario cuyo perfil se mostrará.
     * @return Ruta completa con el userId insertado.
     */
    object Profile : Screen("profile/{userId}") {
        fun createRoute(userId: Long) = "profile/$userId"
    }

    /** Ruta para la pantalla de Login, sin argumentos. */
    object Login : Screen("login")

    /** Ruta para la pantalla de Registro, sin argumentos. */
    object Register : Screen("register")

    /**
     * Ruta para la creación de un hábito, que requiere un userId.
     * @param userId Id del usuario que crea el hábito.
     * @return Ruta completa con el userId insertado.
     */
    object CreateHabit : Screen("createHabit/{userId}") {
        fun createRoute(userId: Long) = "createHabit/$userId"
    }

    /**
     * Ruta para la pantalla de progreso, que requiere un userId.
     * @param userId Id del usuario para mostrar su progreso.
     * @return Ruta completa con el userId insertado.
     */
    object Progress : Screen("progress/{userId}") {
        fun createRoute(userId: Long) = "progress/$userId"
    }

    /**
     * Ruta para actualizar perfil, que requiere un userId.
     * @param userId Id del usuario cuyo perfil será actualizado.
     * @return Ruta completa con el userId insertado.
     */
    object UpdateUser : Screen("update/profile/{userId}") {
        fun createRoute(userId: Long) = "update/profile/$userId"
    }

    /**
     * Ruta para actualizar un hábito, que requiere un habitId.
     * @param habitId Id del hábito a actualizar.
     * @return Ruta completa con el habitId insertado.
     */
    object UpdateHabit : Screen("update/habit/{habitId}") {
        fun createRoute(habitId: Long) = "update/habit/$habitId"
    }

    /** Ruta para la pantalla de recuperación de contraseña. */
    object ForgotPassword : Screen("forgot_password")

    /** Ruta para el selector de hora de notificaciones. */
    object NotificationsPicker : Screen("notif-picker")
}
