package io.github.bikkysamuel.playanime.ui.screens

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import io.github.bikkysamuel.playanime.ui.nav.BottomBarScreen
import io.github.bikkysamuel.playanime.ui.nav.BottomNavGraph
import io.github.bikkysamuel.playanime.ui.viewmodels.MainViewModel
import io.github.bikkysamuel.playanime.utils.Constants

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val bottomBarScreens = listOf(
        BottomBarScreen.Dashboard,
        BottomBarScreen.Browse,
        BottomBarScreen.Settings
    )

    val viewModel = hiltViewModel<MainViewModel>()

    if (!viewModel.appDisclaimerAccepted) {
        DisclaimerDialog(
            onConfirmAction = { viewModel.updateDisclaimerAcceptedValue() }
        )
    }

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

@Composable
fun DisclaimerDialog(
    onConfirmAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    val openAlertDialog = remember {
        mutableStateOf(true)
    }
    when {
        openAlertDialog.value -> {
            AlertDialog(
                modifier = modifier,
                onDismissRequest = {
                    openAlertDialog.value = false
                }, confirmButton = {
                    Button(onClick = {
                        openAlertDialog.value = false
                        onConfirmAction()
                    }) {
                        Text(text = "Ok")
                    }
                },
                title = {
                    Text(text = Constants.DISCLAIMER)
                },
                text = {
                    Text(text = Constants.DISCLAIMER_MESSAGE)
                }
            )
        }
    }
}