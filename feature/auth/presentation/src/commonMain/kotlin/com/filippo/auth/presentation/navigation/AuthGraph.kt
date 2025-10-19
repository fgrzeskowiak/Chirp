package com.filippo.auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.filippo.auth.presentation.email_verification.EmailVerificationRoot
import com.filippo.auth.presentation.forgot_password.ForgotPasswordRoot
import com.filippo.auth.presentation.login.LoginRoot
import com.filippo.auth.presentation.register.RegisterRoot
import com.filippo.auth.presentation.register.success.RegisterSuccessRoot

fun NavGraphBuilder.authGraph(
    navController: NavController,
) {
    navigation<AuthGraphRoutes.Graph>(
        startDestination = AuthGraphRoutes.Login,
    ) {
        composable<AuthGraphRoutes.Login>{
            LoginRoot(
                onForgotPasswordClick = {
                    navController.navigate(AuthGraphRoutes.ForgotPassword)
                },
                onCreateAccountClick = {
                    navController.navigate(AuthGraphRoutes.Register) {
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<AuthGraphRoutes.Register> {
            RegisterRoot(
                onRegisterSuccess = {
                    navController.navigate(AuthGraphRoutes.RegisterSuccess(it))
                },
                onLoginClick = {
                    navController.navigate(AuthGraphRoutes.Login) {
                        popUpTo<AuthGraphRoutes.Register> {
                            inclusive = true
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable<AuthGraphRoutes.RegisterSuccess> {
            RegisterSuccessRoot(
                onLoginClick = {
                    navController.navigate(AuthGraphRoutes.Login) {
                        popUpTo<AuthGraphRoutes.RegisterSuccess> {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<AuthGraphRoutes.EmailVerification>(
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "https://chirp.pl-coding.com/api/auth/verify?token={token}"
                },
                navDeepLink {
                    uriPattern = "chirp://chirp.pl-coding.com/api/auth/verify?token={token}"
                }
            )
        ) {
            EmailVerificationRoot(
                onLoginClick = {
                    navController.navigate(AuthGraphRoutes.Login) {
                        popUpTo<AuthGraphRoutes.EmailVerification> {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<AuthGraphRoutes.ForgotPassword> {
            ForgotPasswordRoot()
        }
    }
}
