package io.github.bikkysamuel.playanime.models

import androidx.room.Entity
import io.github.bikkysamuel.playanime.utils.VideoType

@Entity
data class VideoPlayerEpisodeItem(
    val name: String,
    val season: String,
    val episode: String,
    val imgUrl: String,
    val videoUrl: String,
    val videoType: VideoType,
    val uploadDate: String
)
