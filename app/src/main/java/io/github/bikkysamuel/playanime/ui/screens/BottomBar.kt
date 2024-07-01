package io.github.bikkysamuel.playanime.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import io.github.bikkysamuel.playanime.ui.nav.BottomBarScreen
import io.github.bikkysamuel.playanime.ui.viewmodels.MainViewModel

@Composable
fun BottomBar(
    navController: NavHostController,
    bottomBarScreens: List<BottomBarScreen>,
    selectedBottomNavBarItemIndex: MutableIntState,
    modifier: Modifier = Modifier
) {
    val viewModel = hiltViewModel<MainViewModel>()
    AnimatedVisibility(
        visible = viewModel.showBottomBar,
        enter = EnterTransition.None,
        exit = ExitTransition.None,
        modifier = modifier
    ) {
        NavigationBar {
            bottomBarScreens.forEachIndexed { index, bottomBarScreen ->
                NavigationBarItem(
                    selected = selectedBottomNavBarItemIndex.intValue == index,
                    label = {
                        Text(text = bottomBarScreen.title)
                    },
                    onClick = {
                        selectedBottomNavBarItemIndex.intValue = index
                        navController.popBackStack()
                        navController.navigate(bottomBarScreen.title)
                        bottomBarScreen.badgeCount = null
                        bottomBarScreen.hasNews = false
                    },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (bottomBarScreen.badgeCount != null) {
                                    Badge {
                                        Text(text = bottomBarScreen.badgeCount.toString())
                                    }
                                } else if (bottomBarScreen.hasNews) {
                                    Badge()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (selectedBottomNavBarItemIndex.intValue == index)
                                    bottomBarScreen.selectedIcon
                                else
                                    bottomBarScreen.unselectedIcon,
                                contentDescription = bottomBarScreen.title
                            )
                        }
                    })
            }
        }
    }
}