package io.github.bikkysamuel.playanime.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MyCircularProgressIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = modifier
                .fillMaxSize(0.25f)
                .align(Alignment.Center)
        )
    }
}