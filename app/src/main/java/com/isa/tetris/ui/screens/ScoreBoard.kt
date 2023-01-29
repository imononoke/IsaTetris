package com.isa.tetris.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isa.tetris.ui.entity.Spirit
import com.isa.tetris.ui.theme.BrickMatrix
import com.isa.tetris.ui.theme.BrickSpirit
import com.isa.tetris.utils.NextMatrix

/**
 * 显示游戏得分、时钟等信息
 */
@Composable
fun ScoreBoard(
    modifier: Modifier = Modifier,
    brickSize: Float = 35f,
    spirit: Spirit,
    score: Int = 0,
    line: Int = 0,
    level: Int = 1,
    isMute: Boolean = false,
    isPaused: Boolean = false
) {
    Row(
        modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.weight(0.65f))
        val textSize = 12.sp
        val margin = 12.dp
        Column(
            Modifier
                .fillMaxHeight()
                .weight(0.35f)
        ) {
            Text("Score", fontSize = textSize)
            ScoreNumber(Modifier.fillMaxWidth(), score, 6)

            Spacer(modifier = Modifier.height(margin))

            Text("Lines", fontSize = textSize)
            ScoreNumber(Modifier.fillMaxWidth(), line, 6)

            Spacer(modifier = Modifier.height(margin))

            Text("Level", fontSize = textSize)
            ScoreNumber(Modifier.fillMaxWidth(), level, 1)

            Spacer(modifier = Modifier.height(margin))

            Text("Next", fontSize = textSize)

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)
            ) {
                drawMatrix(
                    brickSize,
                    NextMatrix
                )

                drawSpirit(
                    spirit.adjustOffset(NextMatrix),
                    brickSize = brickSize, NextMatrix
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // todo
//            Row {
//                Image(
//                    modifier = Modifier.width(15.dp),
//                    imageVector = ImageVector.vectorResource(id = android.R.drawable.ic_media_ff),
//                    colorFilter = ColorFilter.tint(
//                        if (isMute) BrickSpirit else BrickMatrix
//                    ),
//                    contentDescription = null
//                )
//                Image(
//                    modifier = Modifier.width(16.dp),
//                    imageVector = ImageVector.vectorResource(android.R.drawable.ic_media_pause),
//                    colorFilter = ColorFilter.tint(
//                        if (isPaused) BrickSpirit else BrickMatrix
//                    ),
//                    contentDescription = null
//                )
//
//                Spacer(modifier = Modifier.weight(1f))
//
//                ScoreClock()
//            }
        }
    }
}