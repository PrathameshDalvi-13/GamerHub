package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.MainHubScreen
import com.example.ui.theme.SquadForgeTheme
import com.example.ui.viewmodel.GamerViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SquadForgeTheme {
                val gamerViewModel: GamerViewModel = viewModel()
                MainHubScreen(viewModel = gamerViewModel)
            }
        }
    }
}


