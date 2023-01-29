package com.isa.tetris

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.isa.tetris.ui.screens.GameScreen
import com.isa.tetris.ui.screens.TetrisScreen
import com.isa.tetris.ui.theme.IsaTetrisTheme
import com.isa.tetris.utils.SoundUtil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        SoundUtil.init(this)

        setContent {
            IsaTetrisTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    GameScreen {
                        TetrisScreen(
                            modifier = Modifier.fillMaxSize()
                        )
//                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        SoundUtil.release()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    IsaTetrisTheme {
        TetrisScreen()
    }
}