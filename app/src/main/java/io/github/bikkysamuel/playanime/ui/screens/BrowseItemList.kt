package io.github.bikkysamuel.playanime.ui.screens

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import io.github.bikkysamuel.playanime.localstorage.db.AnimeDataItem

@Composable
fun BrowseItemList(
    lazyGridState: LazyGridState,
    animeDataItems: MutableList<AnimeDataItem>,
    navController: NavHostController,
    loadNextPage: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2),
        state = lazyGridState,
        modifier = modifier
    ) {
        itemsIndexed(items = animeDataItems) { index, item ->
            if (index >= animeDataItems.size - 1)
                loadNextPage()
            AnimeItem(
                animeDataItem = item,
                navController = navController
            )
        }
    }
}