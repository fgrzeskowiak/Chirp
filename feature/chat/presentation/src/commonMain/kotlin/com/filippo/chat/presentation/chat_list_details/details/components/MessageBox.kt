package com.filippo.chat.presentation.chat_list_details.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.send
import chirp.feature.chat.presentation.generated.resources.send_a_message
import com.filippo.core.designsystem.components.buttons.ChirpButton
import com.filippo.core.designsystem.components.textfields.ChirpMultilineTextField
import com.filippo.core.designsystem.theme.ChirpTheme
import com.filippo.core.designsystem.theme.extended
import com.filippo.core.presentation.util.UiText
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MessageBox(
    state: TextFieldState,
    isInputEnabled: Boolean,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier,
    connectionError: UiText? = null,
) {
    ChirpMultilineTextField(
        modifier = modifier,
        state = state,
        placeholder = stringResource(Res.string.send_a_message),
        enabled = isInputEnabled,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
        onKeyboardAction = onSendClick,
        bottomContent = {
            Spacer(modifier = Modifier.weight(1f))
            connectionError?.let { ConnectionError(it.asString()) }
            ChirpButton(
                text = stringResource(Res.string.send),
                onClick = onSendClick,
                isEnabled = connectionError == null && isInputEnabled
            )
        }
    )
}

@Composable
private fun ConnectionError(message: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = Icons.Default.CloudOff,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.extended.textDisabled
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.extended.textDisabled
        )
    }
}

@Preview
@Composable
private fun MessageBoxPreview() {
    ChirpTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            MessageBox(
                state = rememberTextFieldState(),
                isInputEnabled = true,
                onSendClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun MessageBoxErrorPreview() {
    ChirpTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            MessageBox(
                state = rememberTextFieldState("This is a message"),
                isInputEnabled = true,
                connectionError = UiText.Dynamic("Unknown Error"),
                onSendClick = {}
            )
        }
    }
}