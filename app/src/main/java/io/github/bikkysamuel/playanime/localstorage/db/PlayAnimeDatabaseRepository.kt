package io.github.bikkysamuel.playanime.localstorage.db

import javax.inject.Inject

class PlayAnimeDatabaseRepository @Inject constructor(
    private val playAnimeDao: PlayAnimeDao
) {
    suspend fun getAllAnimeItems() = playAnimeDao.getAll()

    suspend fun findAllAnimeByName(name: String) = playAnimeDao.findAllByName(name)

    fun findAnimeByName(name: String) = playAnimeDao.findByName(name)

    suspend fun insertAnimeItem(animeDataItem: AnimeDataItem) = playAnimeDao.insert(animeDataItem)

    suspend fun deleteAnimeItem(animeDataItem: AnimeDataItem) = playAnimeDao.delete(animeDataItem)
}