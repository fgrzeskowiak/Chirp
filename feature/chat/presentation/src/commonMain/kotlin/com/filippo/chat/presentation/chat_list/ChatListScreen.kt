package com.filippo.chat.presentation.chat_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.filippo.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ChatListRoot(
    viewModel: ChatListViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ChatListScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
internal fun ChatListScreen(
    state: ChatListState,
    onAction: (ChatListAction) -> Unit,
) {

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Chat List")
    }

}

@Preview
@Composable
private fun Preview() {
    ChirpTheme {
        ChatListScreen(
            state = ChatListState(),
            onAction = {}
        )
    }
}
