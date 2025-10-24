package com.filippo.core.designsystem.components.chat

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class ChatBubbleShape(
    private val trianglePosition: TrianglePosition,
    private val triangleSize: Dp = 16.dp,
    private val cornerRadius: Dp = 8.dp,
) : Shape {
    enum class TrianglePosition {
        LEFT, RIGHT
    }

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val triangleSizePx = with(density) { triangleSize.toPx() }
        val cornerRadiusPx = with(density) { cornerRadius.toPx() }
        val cornerRadius = CornerRadius(x = cornerRadiusPx, y = cornerRadiusPx)

        val path = when (trianglePosition) {
            TrianglePosition.LEFT -> {
                val bodyPath = Path().apply {
                    addRoundRect(
                        RoundRect(
                            left = triangleSizePx,
                            top = 0f,
                            right = size.width,
                            bottom = size.height,
                            cornerRadius = cornerRadius
                        )
                    )
                }
                val trianglePath = Path().apply {
                    moveTo(0f, size.height)
                    lineTo(triangleSizePx, size.height - triangleSizePx)
                    lineTo(triangleSizePx + cornerRadiusPx, size.height)
                    close()
                }

                Path.combine(PathOperation.Union, bodyPath, trianglePath)
            }

            TrianglePosition.RIGHT -> {
                val bodyPath = Path().apply {
                    addRoundRect(
                        RoundRect(
                            left = 0f,
                            top = 0f,
                            right = size.width - triangleSizePx,
                            bottom = size.height,
                            cornerRadius = cornerRadius
                        )
                    )
                }
                val trianglePath = Path().apply {
                    moveTo(size.width, size.height)
                    lineTo(size.width - triangleSizePx, size.height - triangleSizePx)
                    lineTo(size.width - triangleSizePx - cornerRadiusPx, size.height)
                    close()
                }

                Path.combine(PathOperation.Union, bodyPath, trianglePath)
            }
        }

        return Outline.Generic(path)
    }
}