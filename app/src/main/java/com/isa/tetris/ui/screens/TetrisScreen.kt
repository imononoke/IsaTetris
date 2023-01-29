package com.isa.tetris.ui.screens

import android.graphics.Paint
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.lifecycle.viewmodel.compose.viewModel
import com.isa.tetris.ui.entity.Brick
import com.isa.tetris.ui.entity.Spirit
import com.isa.tetris.ui.entity.SpiritType
import com.isa.tetris.ui.theme.BrickMatrix
import com.isa.tetris.ui.theme.BrickSpirit
import com.isa.tetris.ui.theme.ScreenBackground
import kotlin.math.min

@Composable
fun TetrisScreen(
    modifier: Modifier = Modifier
) {
    val viewModel = viewModel<TetrisViewModel>()
    val viewState = viewModel.viewState.value

    Box(
        modifier
            .background(Color.Black)
            .padding(1.dp)
            .background(ScreenBackground)
            .padding(10.dp)
    ) {

        val animateValue by rememberInfiniteTransition().animateFloat(
            initialValue = 0f, targetValue = 0.7f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1500),
                repeatMode = RepeatMode.Reverse,
            ),
        )

        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val brickSize = min(
                size.width / viewState.matrix.first,
                size.height / viewState.matrix.second
            )

            drawMatrix(brickSize, viewState.matrix)
            drawMatrixBorder(brickSize, viewState.matrix)
            drawBricks(viewState.bricks, brickSize, viewState.matrix)
            drawSpirit(viewState.spirit, brickSize, viewState.matrix)
            drawText(viewState.gameStatus, brickSize, viewState.matrix, animateValue)

        }

        ScoreBoard(
            spirit = run {
                if (viewState.spirit == Spirit.Empty) Spirit.Empty
                else viewState.spiritNext.rotate()
            },
            score = viewState.score,
            line = viewState.line,
            level = viewState.level,
            isMute = viewState.isMute,
            isPaused = viewState.isPaused
        )
    }
}

internal fun DrawScope.drawSpirit(
    spirit: Spirit,
    brickSize: Float,
    matrix: Pair<Int, Int>
) {
    clipRect(
        0f,
        0f,
        matrix.first * brickSize,
        matrix.second * brickSize
    ) {
        spirit.location.forEach {
            drawBrick(
                brickSize,
                Offset(it.x, it.y),
                BrickSpirit
            )
        }
    }
}

/**
 * draw matrix
 */
internal fun DrawScope.drawMatrix(
    brickSize: Float,
    matrix: Pair<Int, Int>
) {
    (0 until matrix.first).forEach { x ->
        (0 until matrix.second).forEach { y ->
            drawBrick(
                brickSize,
                Offset(x.toFloat(), y.toFloat()),
                BrickMatrix
            )
        }
    }
}

internal fun DrawScope.drawMatrixBorder(
    brickSize: Float,
    matrix: Pair<Int, Int>
) {
    val gap = matrix.first * brickSize * 0.05f

    drawRect(
        Color.Black,
        size = Size(
            matrix.first * brickSize + gap,
            matrix.second * brickSize + gap
        ),
        topLeft = Offset(
            -gap / 2,
            -gap / 2
        ),
        style = Stroke(1.dp.toPx())
    )

}

private fun DrawScope.drawBricks(
    brick: List<Brick>,
    brickSize: Float,
    matrix: Pair<Int, Int>
) {
    clipRect(
        0F,
        0F,
        matrix.first * brickSize,
        matrix.second * brickSize
    ) {
        brick.forEach {
            drawBrick(
                brickSize,
                it.location,
                BrickSpirit
            )
        }
    }
}

private fun DrawScope.drawBrick(
    brickSize: Float,
    offset: Offset,
    color: Color
) {
    val actualLocation = Offset(
        offset.x * brickSize,
        offset.y * brickSize
    )

    val outerSize = brickSize * 0.8f
    val outerOffset = (brickSize - outerSize) / 2

    drawRect(
        color,
        topLeft = actualLocation + Offset(outerOffset, outerOffset),
        size = Size(outerSize, outerSize),
        style = Stroke(outerSize / 10)
    )

    val innerSize = brickSize * 0.5f
    val innerOffset = (brickSize - innerSize) / 2

    drawRect(
        color,
        actualLocation + Offset(innerOffset, innerOffset),
        size = Size(innerSize, innerSize)
    )
}

private fun DrawScope.drawText(
    gameStatus: GameStatus,
    brickSize: Float,
    matrix: Pair<Int, Int>,
    alpha: Float,
) {

    val center = Offset(
        brickSize * matrix.first / 2,
        brickSize * matrix.second / 2
    )

    val drawText = { text: String, size: Float ->
        drawIntoCanvas {
            it.nativeCanvas.drawText(
                text,
                center.x,
                center.y,
                Paint().apply {
                    color = Color.Black.copy(alpha = alpha).toArgb()
                    textSize = size
                    textAlign = Paint.Align.CENTER
                    style = Paint.Style.FILL_AND_STROKE
                    strokeWidth = size / 12
                }
            )
        }
    }

    if (gameStatus == GameStatus.Onboard) {
        drawText("TETRIS", 80f)
    } else if (gameStatus == GameStatus.GameOver) {
        drawText("GAME OVER", 60f)
    }
}

@Preview
@Composable
fun PreviewGamescreen(
    modifier: Modifier = Modifier
        .width(260.dp)
        .height(300.dp)
) {

    Box(
        modifier
            .background(Color.Black)
            .padding(1.dp)
            .background(ScreenBackground)
            .padding(10.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(ScreenBackground)
        ) {

            val brickSize = min(
                size.width / 12,
                size.height / 24
            )

            drawMatrix(brickSize = brickSize, 12 to 24)
            drawMatrixBorder(brickSize = brickSize, 12 to 24)
        }

        val type = SpiritType[6]
        ScoreBoard(
            spirit = Spirit(type, Offset(0f, 0f)).rotate(),
            score = 1204,
            line = 12
        )
    }
}

@Preview
@Composable
fun PreviewSpiritType() {
    Row(
        Modifier
            .size(300.dp, 50.dp)
            .background(ScreenBackground)
    ) {
        val matrix = 2 to 4
        SpiritType.forEach {
            Canvas(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(5.dp)

            ) {
                drawBricks(
                    Brick.of(
                        Spirit(it).adjustOffset(matrix)
                    ), min(
                        size.width / matrix.first,
                        size.height / matrix.second
                    ), matrix
                )
            }
        }
    }
}
