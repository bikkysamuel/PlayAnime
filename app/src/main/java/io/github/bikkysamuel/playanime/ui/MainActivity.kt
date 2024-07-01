package io.github.bikkysamuel.playanime.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import io.github.bikkysamuel.playanime.ui.screens.MainScreen
import io.github.bikkysamuel.playanime.ui.theme.PlayAnimeTheme
import io.github.bikkysamuel.playanime.ui.viewmodels.MainViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel by viewModels<MainViewModel>()
            viewModel.systemStatusBarHeight =
                WindowInsets.statusBars.asPaddingValues().calculateTopPadding().value
            PlayAnimeTheme(
                appTheme = viewModel.appTheme,
                appFontStyle = viewModel.appFontStyle
            ) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainScreen()
                }
            }
        }
    }
}