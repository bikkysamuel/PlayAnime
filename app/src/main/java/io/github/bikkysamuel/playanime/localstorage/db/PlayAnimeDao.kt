package io.github.bikkysamuel.playanime.localstorage.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlayAnimeDao {

    @Query("SELECT * FROM table_anime ORDER BY name ASC")
    suspend fun getAll(): List<AnimeDataItem>

    @Query("SELECT * FROM table_anime WHERE UPPER(name) LIKE UPPER(:name) ORDER BY name ASC")
    suspend fun findAllByName(name: String): List<AnimeDataItem>

    @Query("SELECT * FROM table_anime WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): LiveData<AnimeDataItem>

    @Insert
    suspend fun insertAll(vararg animeDataItem: AnimeDataItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(browseAnimeDataItem: AnimeDataItem)

    @Delete
    suspend fun delete(animeDataItem: AnimeDataItem)
}