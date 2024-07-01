package io.github.bikkysamuel.playanime.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.bikkysamuel.playanime.utils.Constants

sealed class BottomBarScreen(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    var hasNews: Boolean = false,
    var badgeCount: Int? = null
) {
    data object Dashboard : BottomBarScreen(
        title = "Home",
        route = "home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )

    data object Browse : BottomBarScreen(
        title = "Browse",
        route = "browse",
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search
    )

    data object Settings : BottomBarScreen(
        title = "Settings",
        route = "settings",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings
    )

    data object AnimeDetails : BottomBarScreen(
        title = "AnimeDetails",
        route = "animeDetails",
        selectedIcon = Icons.Filled.Info,
        unselectedIcon = Icons.Outlined.Info
    )

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/${arg.replace(oldValue = Constants.VIDEO_END_POINT_URL_PREFIX, "")}")
            }
        }
    }
}
