package io.github.bikkysamuel.playanime.ui.nav

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.github.bikkysamuel.playanime.ui.screens.AnimeDetailScreen
import io.github.bikkysamuel.playanime.ui.screens.BrowseScreen
import io.github.bikkysamuel.playanime.ui.screens.DashboardScreen
import io.github.bikkysamuel.playanime.ui.screens.SettingsScreen
import io.github.bikkysamuel.playanime.ui.viewmodels.AnimeDetailViewModel
import io.github.bikkysamuel.playanime.ui.viewmodels.BrowseViewModel
import io.github.bikkysamuel.playanime.ui.viewmodels.MainViewModel
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    bottomPadding: Dp,
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val animeDetailViewModel = hiltViewModel<AnimeDetailViewModel>()
    val browseViewModel = hiltViewModel<BrowseViewModel>()
    val browseLazyGridState = rememberLazyGridState()

    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Dashboard.route,
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = bottomPadding)
    ) {
        composable(route = BottomBarScreen.Dashboard.route) {
            DashboardScreen(
                viewModel = mainViewModel,
                navController = navController
            )
        }
        composable(route = BottomBarScreen.Browse.route) {
            BrowseScreen(
                navController = navController,
                viewModel = browseViewModel,
                lazyGridState = browseLazyGridState
            )
        }
        composable(route = BottomBarScreen.Settings.route) {
            Surface(modifier = Modifier.fillMaxSize()) {
                SettingsScreen(viewModel = mainViewModel)
            }
        }
        composable(
//            route = BottomBarScreen.AnimeDetails.route + "?name=/{name}/{url}", //Optional name and Mandatory url
            route = BottomBarScreen.AnimeDetails.route + "/{videoUrl}",
            arguments = listOf(
                navArgument("videoUrl") {
                    nullable = false
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            mainViewModel.showBottomBar =
                !navController.currentDestination!!.route!!.contains(BottomBarScreen.AnimeDetails.route)

            val onBackPressedDispatcher =
                LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

            var backPressHandled by remember { mutableStateOf(false) }
            val coroutineScope = rememberCoroutineScope()

            BackHandler(enabled = !backPressHandled) {
                println("BKS - back pressed")
                mainViewModel.showBottomBar = true
                backPressHandled = true
                coroutineScope.launch {
                    awaitFrame()
                    onBackPressedDispatcher?.onBackPressed()
                    backPressHandled = false
                }
            }

            val videoUrl = navBackStackEntry.arguments!!.getString("videoUrl")!!
            animeDetailViewModel.setVideoPageUrl(videoUrl = videoUrl)
            AnimeDetailScreen(viewModel = animeDetailViewModel)
        }
    }
}