package io.github.bikkysamuel.playanime.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import io.github.bikkysamuel.playanime.R
import io.github.bikkysamuel.playanime.localstorage.db.AnimeDataItem
import io.github.bikkysamuel.playanime.ui.nav.BottomBarScreen
import io.github.bikkysamuel.playanime.ui.viewmodels.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(
    viewModel: MainViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    if (!viewModel.showSearchBar)
        viewModel.loadAllAnimeFromDb()

    val lazyGridState = rememberLazyGridState()

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            if (viewModel.showSearchBar) {
                MySearchBar(
                    initialSearchText = viewModel.searchText,
                    requestFocus = viewModel.requestFocusOnSearchTextField,
                    onKeyPress = { searchText ->
                        viewModel.filterAnimeUsingSearchText(searchKeyword = searchText)
                    },
                    onSearch = {
                        viewModel.doneTyping()
                    },
                    modifier = Modifier.weight(1f)
                )

                // Close button for Search
                Button(onClick = {
                    viewModel.closeSearchMode()
                    if (viewModel.searchText.isNotEmpty()) {
                        coroutineScope.launch {
                            lazyGridState.scrollToItem(0)
                        }
                        viewModel.loadAllAnimeFromDb()
                    }
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_close_24),
                        contentDescription = "Close Search Button"
                    )
                }
            } else {
                DashboardTitle(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                )

                Button(onClick = {
                    viewModel.enterSearchMode()
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = "Sow Search Screen Button to filter using anime name."
                    )
                }
            }
        }

        DashboardItemList(
            lazyGridState = lazyGridState,
            animeList = viewModel.animeList,
            navController = navController,
            modifier = modifier
        )
    }
}

@Composable
fun DashboardTitle(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(10.dp)
    ) {
        Text(
            text = BottomBarScreen.Dashboard.title,
            fontSize = 18.sp
        )
    }
}

@Composable
fun DashboardItemList(
    lazyGridState: LazyGridState,
    animeList: MutableList<AnimeDataItem>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2),
        state = lazyGridState,
        modifier = modifier
    ) {
        itemsIndexed(items = animeList) { index, item ->
            AnimeItem(animeDataItem = item, navController = navController)
        }
    }
}