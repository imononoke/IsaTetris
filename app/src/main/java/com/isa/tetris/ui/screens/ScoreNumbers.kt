package com.isa.tetris.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isa.tetris.ui.theme.BrickMatrix
import com.isa.tetris.ui.theme.BrickSpirit
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@Composable
fun ScoreClock(
    modifier: Modifier = Modifier
) {
    val animateValue by rememberInfiniteTransition().animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart,
        ),
    )

    var clock by remember { mutableStateOf(0 to 0) }

    DisposableEffect(key1 = animateValue.roundToInt()) {
        @SuppressLint("SimpleDateFormat")
        val dateFormat: DateFormat = SimpleDateFormat("H,m")
        val (curHou, curMin) = dateFormat.format(Date()).split(",")
        clock = curHou.toInt() to curMin.toInt()
        onDispose {}
    }

    Row(modifier) {
        ScoreNumber(num = clock.first, digits = 2, fillZero = true)

        val LedComma: @Composable (color: Color) -> Unit = remember {
            {
                Text(
                    ":",
                    fontFamily = FontFamily.Cursive,
                    textAlign = TextAlign.End,
                    color = it,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Box(
            modifier = Modifier
                .width(6.dp)
                .padding(end = 1.dp),
        ) {
            LedComma(BrickMatrix)
            if (animateValue.roundToInt() == 1) {
                LedComma(BrickSpirit)
            }
        }

        ScoreNumber(
            num = clock.second,
            digits = 2,
            fillZero = true
        )
    }
}

@Composable
fun ScoreNumber(
    modifier: Modifier = Modifier,
    num: Int,
    digits: Int,
    fillZero: Boolean = false
) {
    val textSize = 16.sp
    val textWidth = 8.dp

    Box(modifier) {
        Row(modifier = Modifier.align(Alignment.CenterEnd)) {
            repeat(digits) {
                Text(
                    "8",
                    fontSize = textSize,
                    color = BrickMatrix,
                    fontFamily = FontFamily.Cursive,
                    modifier = Modifier.width(textWidth),
                    textAlign = TextAlign.End

                )
            }

        }

        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
        ) {
            val str =
                if (fillZero) String.format("%0${digits}d", num)
                else num.toString()

            str.iterator().forEach {
                Text(
                    it.toString(),
                    fontSize = textSize,
                    color = BrickSpirit,
                    fontFamily = FontFamily.Cursive,
                    modifier = Modifier.width(textWidth),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}
