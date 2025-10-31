package com.levelup.levelupgamer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.levelup.levelupgamer.navigation.AppNavegacion
import com.levelup.levelupgamer.ui.theme.LEVELUPGAMERTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LEVELUPGAMERTheme {
                AppNavegacion()
            }
        }
    }
}