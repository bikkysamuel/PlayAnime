package io.github.bikkysamuel.playanime.localstorage.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AnimeDataItem::class],
    version = 1
)
abstract class PlayAnimeDatabase : RoomDatabase() {
    abstract fun getPlayAnimeDao() : PlayAnimeDao
}