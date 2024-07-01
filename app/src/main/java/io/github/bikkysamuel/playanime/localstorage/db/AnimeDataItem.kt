package io.github.bikkysamuel.playanime.localstorage.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.bikkysamuel.playanime.utils.VideoType
import java.io.Serializable

@Entity(tableName = "table_anime")
data class AnimeDataItem(
    val id: Int = 0,
    @PrimaryKey var name: String,
    var description: String,
    var uploadedDate: String,
    var videoUrl: String,
    var imgUrl: String,
    var videoType: VideoType = VideoType.SUB
): Serializable
