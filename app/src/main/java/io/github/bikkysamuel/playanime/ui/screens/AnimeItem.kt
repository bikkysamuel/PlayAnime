package io.github.bikkysamuel.playanime.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import io.github.bikkysamuel.playanime.localstorage.db.AnimeDataItem
import io.github.bikkysamuel.playanime.ui.nav.BottomBarScreen

@Composable
fun AnimeItem(
    animeDataItem: AnimeDataItem,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var imageBoxHeight = 0f
    Column(
        modifier = modifier
            .padding(5.dp)
    ) {
        Box(
            modifier = modifier
                .shadow(5.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .aspectRatio(0.5625f)
                .onGloballyPositioned { coordinates ->
                    imageBoxHeight = coordinates.size.height.toFloat()
                }
                .clickable {
                    navController.navigate(
                        route = BottomBarScreen.AnimeDetails.withArgs(
                            animeDataItem.videoUrl
                        )
                    )
                }
        ) {
            AsyncImage(
                model = animeDataItem.imgUrl,
                contentDescription = animeDataItem.name,
                modifier = modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = imageBoxHeight
                        )
                    )
            )
            Text(
                text = animeDataItem.name,
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd),
                maxLines = 2
            )
        }
        Text(
            text = animeDataItem.uploadedDate,
            modifier = modifier.align(Alignment.End)
        )
    }
}