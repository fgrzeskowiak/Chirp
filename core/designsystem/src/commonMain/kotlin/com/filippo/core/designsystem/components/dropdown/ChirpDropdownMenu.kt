package com.filippo.core.designsystem.components.dropdown

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.logout_icon
import chirp.core.designsystem.generated.resources.users_icon
import com.filippo.core.designsystem.theme.ChirpTheme
import com.filippo.core.designsystem.theme.extended
import com.filippo.core.presentation.util.drawDivider
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpDropdownMenu(
    isOpen: Boolean,
    items: List<DropdownMenuItem>,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownMenu(
        modifier = modifier,
        expanded = isOpen,
        shape = RoundedCornerShape(16.dp),
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.extended.surfaceOutline
        )
    ) {
        items.forEachIndexed { index, menuItem ->
            DropdownMenuItem(
                modifier = Modifier
                    .then(
                        if (index != items.lastIndex) {
                            Modifier.drawDivider(MaterialTheme.colorScheme.outline)
                        } else {
                            Modifier
                        }
                    ),
                text = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = menuItem.icon,
                            contentDescription = null,
                            tint = menuItem.contentColor,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = menuItem.text,
                            color = menuItem.contentColor,
                            fontWeight = FontWeight.Medium
                        )
                    }
                },
                onClick = {
                    onDismiss()
                    menuItem.onClick()
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ChirpDropdownMenuPreview() {
    ChirpTheme {
        ChirpDropdownMenu(
            modifier = Modifier.height(300.dp),
            isOpen = true,
            items = listOf(
                DropdownMenuItem(
                    icon  = vectorResource(Res.drawable.users_icon),
                    text = "Profile",
                    contentColor = MaterialTheme.colorScheme.extended.textSecondary,
                    onClick = {}
                ),
                DropdownMenuItem(
                    icon  = vectorResource(Res.drawable.logout_icon),
                    text = "Log Out",
                    contentColor = MaterialTheme.colorScheme.extended.destructiveHover,
                    onClick = {}
                ),
            ),
            onDismiss = {},
        )
    }
}

data class DropdownMenuItem(
    val icon: ImageVector,
    val text: String,
    val contentColor: Color,
    val onClick: () -> Unit,
)