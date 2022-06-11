package com.hzw.wan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hzw.wan.ui.main.NavGraph
import com.hzw.wan.ui.theme.Purple40
import com.hzw.wan.ui.theme.WanComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            setNavigationBarAndSystemBar()
            WanComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph()
                }
            }
        }
    }
}

@Composable
fun setNavigationBarAndSystemBar(
    navigationColor: Color = MaterialTheme.colorScheme.primary,
    systemBarColor: Color = MaterialTheme.colorScheme.primary,
    darkIcons: Boolean = isSystemInDarkTheme()
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(systemBarColor, darkIcons = darkIcons)
        systemUiController.setNavigationBarColor(color = navigationColor)
    }
}