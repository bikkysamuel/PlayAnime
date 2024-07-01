package io.github.bikkysamuel.playanime.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import io.github.bikkysamuel.playanime.R
import io.github.bikkysamuel.playanime.models.VideoPlayerEpisodeItem
import io.github.bikkysamuel.playanime.ui.viewmodels.AnimeDetailViewModel

@Composable
fun AnimeDetailScreen(
    viewModel: AnimeDetailViewModel,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        viewModel.getVideoData(viewModel.videoUrl)
    }
    if (viewModel.showProgressBar)
        MyCircularProgressIndicator()
    else {
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = modifier
        ) {
            WebViewScreen(viewModel.animeVideoFrameUrl)
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )

            // Anime Episodes
            LazyColumn(
                modifier = Modifier
            ) {
                item {
                    AnimeInfo(viewModel)
                    Spacer(modifier = Modifier.height(10.dp))
                }

                itemsIndexed(viewModel.videoPlayerEpisodeItems) { index, item ->
                    AnimeEpisode(
                        videoPlayerEpisodeItem = item,
                        onClickAction = {
                            viewModel.getVideoData(item.videoUrl)
                        }
                    )

                }
            }
        }
    }
}

@Composable
fun AnimeInfo(
    viewModel: AnimeDetailViewModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            AnimeTitle(animeTitle = viewModel.animeTitle)
            Row {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1.5f)
                ) {
                    AnimePoster(viewModel.posterImageUrl)
                    Spacer(modifier = Modifier.height(10.dp))
                    AddAsFavoriteButton(
                        isFavourite = viewModel.isFavourite,
                        updateFavoriteSelectionAction = {
                            viewModel.updateFavoriteSelection()
                        }
                    )
                }
                AnimeDescriptionText(
                    animeDescription = viewModel.animeDescription,
                    modifier = Modifier.weight(2f)
                )
            }
            CurrentVideoTitle(videoTitle = viewModel.currentVideoTitle)
        }
    }
}

@Composable
fun AnimeTitle(
    animeTitle: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = animeTitle,
        modifier = modifier.fillMaxWidth(),
        fontSize = 20.sp
    )
}

@Composable
fun AnimePoster(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .padding(10.dp)
            .aspectRatio(0.5625f)
    )
}

@Composable
fun AddAsFavoriteButton(
    isFavourite: Boolean,
    updateFavoriteSelectionAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick = {
            updateFavoriteSelectionAction()
        }) {
        val favoriteIconId: Int
        val contentDescription: String
        if (isFavourite) {
            favoriteIconId = R.drawable.baseline_favorite_24
            contentDescription = "Anime is in favorite selection. Click to un-favorite it."
        } else {
            favoriteIconId = R.drawable.baseline_favorite_border_24
            contentDescription = "Anime is not a favorite selection. Click to favorite it."
        }
        Image(
            painter = painterResource(id = favoriteIconId),
            contentDescription = contentDescription
        )
        Text(text = "Favorite")
    }
}

@Composable
fun AnimeDescriptionText(
    animeDescription: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = animeDescription,
        fontSize = 16.sp,
        textAlign = TextAlign.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
    )
}

@Composable
fun CurrentVideoTitle(
    videoTitle: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = videoTitle,
        fontSize = 18.sp,
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
fun AnimeEpisode(
    videoPlayerEpisodeItem: VideoPlayerEpisodeItem,
    onClickAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                onClickAction()
            }
    ) {
        Text(
            text = videoPlayerEpisodeItem.videoType.name,
            fontSize = 16.sp,
            modifier = Modifier
                .weight(1f)
        )
        Text(
//            text = "S${videoPlayerEpisodeItem.season} E${videoPlayerEpisodeItem.episode}",
            text = "E:${videoPlayerEpisodeItem.episode}",
            fontSize = 16.sp,
            modifier = Modifier
                .weight(1f)
        )
        Text(
            text = videoPlayerEpisodeItem.uploadDate,
            fontSize = 16.sp,
            modifier = Modifier
                .weight(1f)
        )
    }
//    Spacer(modifier = Modifier.height(10.dp))
}