package com.filippo.core.designsystem.components.avatar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.times
import com.filippo.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpStackedAvatars(
    avatars: List<AvatarUiModel>,
    modifier: Modifier = Modifier,
    size: AvatarSize = AvatarSize.SMALL,
    remaining: Int = 2,
    overlapPercentage: Float = 0.4f,
) {
    val overlapOffset = -overlapPercentage * size.dp

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(overlapOffset),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        avatars.forEach {
            ChirpAvatarPhoto(avatar = it, size = size)
        }

        if (remaining > 0) {
            ChirpAvatarPhoto(
                avatar = AvatarUiModel(displayText = "$remaining+"),
                size = size,
                textColor = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ChirpStackedAvatarsPreview() {
    ChirpTheme {
        ChirpStackedAvatars(
            avatars = List(2) {
                AvatarUiModel(displayText = "U$it")
            }
        )
    }
}