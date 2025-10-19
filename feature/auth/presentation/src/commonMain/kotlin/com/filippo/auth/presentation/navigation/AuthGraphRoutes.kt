package com.filippo.auth.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface AuthGraphRoutes {
    @Serializable
    data object Graph : AuthGraphRoutes

    @Serializable
    data object Login : AuthGraphRoutes

    @Serializable
    data object Register : AuthGraphRoutes

    @Serializable
    data class RegisterSuccess(val email: String) : AuthGraphRoutes

    @Serializable
    data object ForgotPassword : AuthGraphRoutes

    @Serializable
    data object ResetPassword : AuthGraphRoutes

    @Serializable
    data class EmailVerification(val token: String?) : AuthGraphRoutes
}
