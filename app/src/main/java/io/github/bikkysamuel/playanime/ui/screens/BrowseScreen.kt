package io.github.bikkysamuel.playanime.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import io.github.bikkysamuel.playanime.R
import io.github.bikkysamuel.playanime.ui.nav.BottomBarScreen
import io.github.bikkysamuel.playanime.ui.viewmodels.BrowseViewModel
import io.github.bikkysamuel.playanime.utils.ShowToast
import kotlinx.coroutines.launch

@Composable
fun BrowseScreen(
    navController: NavHostController,
    viewModel: BrowseViewModel,
    lazyGridState: LazyGridState,
    modifier: Modifier = Modifier
) {
    var searchText by remember {
        mutableStateOf(viewModel.searchKeyword)
    }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = modifier.fillMaxSize()) {
        if (viewModel.showProgressBar) {
            MyCircularProgressIndicator()
        } else {
            if (viewModel.animeDataItems.isEmpty()) {
                Button(
                    onClick = {
                        viewModel.loadHomePage(resetData = true)
                    },
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(text = "Retry")
                }
            } else {
                Column {
                    if (viewModel.showSearchBar) {
                        Row {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .align(Alignment.CenterVertically)
                            ) {
                                MySearchBar(
                                    initialSearchText = viewModel.searchKeyword,
                                    requestFocus = viewModel.showEmptyScreenBeforeSearch,
                                    onSearch = {
                                        searchText = it
                                        if (searchText.length >= 2) {
                                            coroutineScope.launch {
                                                lazyGridState.scrollToItem(0)
                                            }
                                            viewModel.searchWithKeyword(searchText, true)
                                        } else
                                            ShowToast.short("Enter 2 or more characters")
                                    }
                                )
                            }

                            // Close button for Search
                            Button(onClick = {
                                viewModel.showSearchBar = false
                                if (searchText.isNotEmpty()) {
                                    coroutineScope.launch {
                                        lazyGridState.scrollToItem(0)
                                    }
                                    viewModel.resetSearchKeyword()
                                    viewModel.showDubVersion(false)
                                    viewModel.loadHomePage(resetData = true)
                                }
                            }) {
                                Image(
                                    painter = painterResource(id = R.drawable.baseline_close_24),
                                    contentDescription = "Close Search Button"
                                )
                            }
                        }
                    } else {
                        Row {
                            BrowseTitle(
                                modifier = Modifier
                                    .weight(1f)
                                    .align(Alignment.CenterVertically)
                            )

                            Button(onClick = {
                                viewModel.showSearchBar = true
                            }) {
                                Image(
                                    painter = painterResource(id = R.drawable.baseline_search_24),
                                    contentDescription = "Sow Search Screen Button"
                                )
                            }

                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        lazyGridState.scrollToItem(0)
                                    }
                                    viewModel.showDubVersion(!viewModel.showDubVersions)
                                    viewModel.loadHomePage(resetData = true)
                                }) {
                                Text(text = viewModel.dubOrSubButtonText)
                            }
                        }
                    }

                    AnimatedVisibility(
                        visible = !(viewModel.showSearchBar
                                && viewModel.showEmptyScreenBeforeSearch)
                    ) {
                        //List based on above search and button selection
                        BrowseItemList(
                            lazyGridState = lazyGridState,
                            animeDataItems = viewModel.animeDataItems,
                            navController = navController,
                            loadNextPage = {
                                viewModel.loadNextPage()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BrowseTitle(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(10.dp)
    ) {
        Text(
            text = BottomBarScreen.Browse.title,
            fontSize = 18.sp
        )
    }
}