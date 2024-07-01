package io.github.bikkysamuel.playanime.models

data class VideoPlayerPageData(
    var animeTitle: String,
    val animeDescription: String,
    val imgUrl: String,
    val videoUrl: String,
    val currentVideoTitle:String,
    var videoPlayerEpisodeItems: ArrayList<VideoPlayerEpisodeItem>
)
