package com.example.boardlog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.boardlog.ui.Splash
import com.example.boardlog.ui.theme.BoardLogTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            BoardLogTheme {

                Splash()

            }
        }
    }
}