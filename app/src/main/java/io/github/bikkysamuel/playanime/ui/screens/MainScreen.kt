package io.github.bikkysamuel.playanime.ui.screens

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import io.github.bikkysamuel.playanime.ui.nav.BottomBarScreen
import io.github.bikkysamuel.playanime.ui.nav.BottomNavGraph
import io.github.bikkysamuel.playanime.ui.viewmodels.MainViewModel

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val bottomBarScreens = listOf(
        BottomBarScreen.Dashboard,
        BottomBarScreen.Browse,
        BottomBarScreen.Settings
    )

    val viewModel = hiltViewModel<MainViewModel>()

    Scaffold(
        bottomBar = {
            BottomBar(
                navController = navController,
                bottomBarScreens = bottomBarScreens,
                selectedBottomNavBarItemIndex = viewModel.selectedBottomNavBarItemIndex
            )
        },
        modifier = modifier
            .fillMaxSize()
            .padding(
                top = WindowInsets.statusBars
                    .asPaddingValues()
                    .calculateTopPadding()
            )
    ) { innerPadding ->
        BottomNavGraph(
            navController = navController,
            bottomPadding = innerPadding.calculateBottomPadding(),
            mainViewModel = viewModel
        )
    }
}