package com.filippo.chat.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.filippo.chat.presentation.chat_list_details.ChatListDetailsScreenRoot

fun NavGraphBuilder.chatGraph(
    navController: NavController,
) {
    navigation<ChatGraphRoutes.Graph>(
        startDestination = ChatGraphRoutes.ChatList,
    ) {
        composable<ChatGraphRoutes.ChatList> {
            ChatListDetailsScreenRoot(
                onLogout = {

                }
            )
        }
    }
}
