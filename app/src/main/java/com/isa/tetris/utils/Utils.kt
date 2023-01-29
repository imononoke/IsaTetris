package com.isa.tetris.utils

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import androidx.compose.ui.geometry.Offset
import com.isa.tetris.R

val NextMatrix = 4 to 2
const val ScoreEverySpirit = 12

fun Offset(x: Int, y: Int) =
    Offset(
        x.toFloat(),
        y.toFloat()
    )

enum class Direction {
    Left, Up, Right, Down
}

fun Direction.toOffset() = when (this) {
    Direction.Left -> -1 to 0
    Direction.Up -> 0 to -1
    Direction.Right -> 1 to 0
    Direction.Down -> 0 to 1
}

fun calculateScore(lines: Int) = when (lines) {
    1 -> 100
    2 -> 300
    3 -> 700
    4 -> 1500
    else -> 0
}

@SuppressLint("StaticFieldLeak")
object SoundUtil {
    private var _context: Context? = null

    private val sp: SoundPool by lazy {
        SoundPool.Builder()
            .setMaxStreams(4)
            .setMaxStreams(AudioManager.STREAM_MUSIC)
            .build()
    }
    private val _map = mutableMapOf<SoundType, Int>()

    fun init(context: Context) {
        _context = context
        Sounds.forEach {
            _map[it] = sp.load(_context, it.res, 1)
        }
    }

    fun release() {
        _context = null
        sp.release()
    }


    fun play(isMute: Boolean, sound: SoundType) {
        if (!isMute) {
            sp.play(requireNotNull(_map[sound]), 1f, 1f, 0, 0, 1f)
        }
    }
}

sealed class SoundType(
    val res: Int
    ) {
    object Move : SoundType(
        0
    )
    object Rotate : SoundType(
//        R.raw.rotate
    1
    )
    object Start : SoundType(
//        R.raw.start
    2
    )
    object Drop : SoundType(
//        R.raw.drop
    3
    )
    object Clean : SoundType(
//        R.raw.clean
    4
    )
}

val Sounds =
    listOf(
        SoundType.Move,
        SoundType.Rotate,
        SoundType.Start,
        SoundType.Drop,
        SoundType.Clean
    )

