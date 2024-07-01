package io.github.bikkysamuel.playanime.localstorage.db

import io.github.bikkysamuel.playanime.utils.MyUtils
import javax.inject.Inject

class DatabaseHandler @Inject constructor(
    private val playAnimeDatabaseRepository: PlayAnimeDatabaseRepository
) {
    private fun checkAndUpdateFavouriteValue(
        animeDataItem: AnimeDataItem,
        updateDbOnResponse: Boolean,
        updateFavouriteValue: (currentFavoriteValue: Boolean) -> Unit
    ) {
        MyUtils.runSuspendFunction {
            animeDataItem.name = MyUtils.getAnimeTitle(animeDataItem.name)
            val animeList: List<AnimeDataItem> =
                playAnimeDatabaseRepository.findAllAnimeByName(
                    animeDataItem.name
                )
            var isFavouriteFromDb: Boolean = false
            animeList.let { list ->
                for (anime in list) {
                    if (anime.name == animeDataItem.name) {
                        isFavouriteFromDb = true
                        break
                    }
                }
            }

            if (updateDbOnResponse) {
                updateFavouriteInDb(animeDataItem, !isFavouriteFromDb)
            } else if (isFavouriteFromDb) { // Update anime data in DB
                updateFavouriteInDb(animeDataItem, true)
            }

            updateFavouriteValue(updateDbOnResponse xor isFavouriteFromDb)
        }
    }

    private suspend fun updateFavouriteInDb(
        animeDataItem: AnimeDataItem,
        addAsFavourite: Boolean
    ) {
        animeDataItem.name = MyUtils.getAnimeTitle(animeDataItem.name)
        if (addAsFavourite) {
            addToFavourite(animeDataItem)
        } else {
            removeFromFavourite(animeDataItem)
        }
    }

    private suspend fun addToFavourite(animeDataItem: AnimeDataItem) {
        playAnimeDatabaseRepository.insertAnimeItem(animeDataItem)
    }

    private suspend fun removeFromFavourite(animeDataItem: AnimeDataItem) {
        playAnimeDatabaseRepository.deleteAnimeItem(animeDataItem)
    }

    fun checkIfAnimeIsFavourite(
        animeDataItem: AnimeDataItem,
        updateFavouriteValue: (currentFavoriteValue: Boolean) -> Unit
    ) {
        checkAndUpdateFavouriteValue(
            animeDataItem = animeDataItem,
            updateDbOnResponse = false,
            updateFavouriteValue = updateFavouriteValue
        )
    }

    /**
     * Check if Anime item is added as a favourite in local database,
     * and based on updateInDb value either
     * ADD the anime item in local database if it is not already present as favourite
     * and return true as currentFavouriteValue in updateUiAction.
     * Else REMOVE the anime item from the local database
     * and return false for currentFavouriteValue in updateUiAction.
     */
    fun updateFavourite(
        animeDataItem: AnimeDataItem,
        updateDataInDb: Boolean,
        updateFavouriteValue: (currentFavoriteValue: Boolean) -> Unit
    ) {
        checkAndUpdateFavouriteValue(
            animeDataItem = animeDataItem,
            updateDbOnResponse = updateDataInDb,
            updateFavouriteValue = updateFavouriteValue
        )
    }

    fun loadAllDataFromLocalStorage(
        updateAnimeList: (List<AnimeDataItem>) -> Unit
    ) {
        MyUtils.runSuspendFunction {
            val animeList = playAnimeDatabaseRepository.getAllAnimeItems()
            updateAnimeList(animeList)
        }
    }

    fun loadAllDataUsingSearch(
        searchKeyword: String,
        updateAnimeList: (animeList: List<AnimeDataItem>) -> Unit
    ) {
        MyUtils.runSuspendFunction {
            val regexSearchKeyword = "%${searchKeyword.uppercase()}%"
            val animeList =
                playAnimeDatabaseRepository.findAllAnimeByName(name = regexSearchKeyword)
            updateAnimeList(animeList)
        }
    }
}