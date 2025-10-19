package com.filippo.chirp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.filippo.auth.presentation.navigation.authGraph
import com.filippo.chat.presentation.navigation.chatGraph

@Composable
fun NavigationRoot(
    navController: NavHostController,
    startDestination: Any,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        authGraph(navController)
        chatGraph(navController)
    }

}
