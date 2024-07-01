package io.github.bikkysamuel.playanime.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.bikkysamuel.playanime.localstorage.db.AnimeDataItem
import io.github.bikkysamuel.playanime.localstorage.db.DatabaseHandler
import io.github.bikkysamuel.playanime.models.VideoPlayerEpisodeItem
import io.github.bikkysamuel.playanime.network.PlayAnimeApiRepository
import io.github.bikkysamuel.playanime.network.parsers.AnimeDetailPageApiResponseParser
import io.github.bikkysamuel.playanime.utils.Resource
import io.github.bikkysamuel.playanime.utils.ShowToast
import io.github.bikkysamuel.playanime.utils.VideoType
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
    private val playAnimeApiRepository: PlayAnimeApiRepository,
    private val databaseHandler: DatabaseHandler
) : ViewModel() {

    var showProgressBar by mutableStateOf(false)
        private set

    var animeTitle by mutableStateOf("")
        private set
    var animeDescription by mutableStateOf("")
        private set
    var currentVideoTitle by mutableStateOf("")
        private set
    var animeVideoFrameUrl by mutableStateOf("")
        private set
    var posterImageUrl by mutableStateOf("")
        private set

    var videoUrl by mutableStateOf("")
        private set

    var webViewVideoUrl by mutableStateOf("")
        private set
    var videoPlayerEpisodeItems by mutableStateOf(ArrayList<VideoPlayerEpisodeItem>())
        private set
    var isFavourite by mutableStateOf(false)
        private set

    fun setVideoPageUrl(videoUrl: String) {
        val videoUrlPrefix = "/videos/"
        this.videoUrl =
            if (videoUrl.startsWith(videoUrlPrefix))
                videoUrl
            else videoUrlPrefix + videoUrl
    }

    fun getVideoData(videoUrl: String) {
        showProgressBar = true
        val videoUrlPrefix = "/videos/"
        this.videoUrl =
            if (videoUrl.startsWith(videoUrlPrefix))
                videoUrl
            else videoUrlPrefix + videoUrl
        getVideoDataFromUrl(this.videoUrl)
    }

    fun getVideoDataFromUrl(videoUrl: String) {
        callPlayAnimeApi {
            playAnimeApiRepository.getVideoPlayerData(videoUrl)
        }
    }

    fun getLatestEpisodeUploadDateTime(): String = this.videoPlayerEpisodeItems.first().uploadDate

    fun getLatestEpisodeVideoType(): VideoType = this.videoPlayerEpisodeItems.first().videoType

    private fun getLatestAnimeItem(): AnimeDataItem {
        return AnimeDataItem(
            name = animeTitle,
            description = animeDescription,
            uploadedDate = getLatestEpisodeUploadDateTime(),
            videoUrl = videoUrl,
            imgUrl = posterImageUrl,
            videoType = getLatestEpisodeVideoType()
        )
    }

    /**
     * Add/Remove the anime item in local database based on the current value of isFavourite.
     * isFavourite = false => Add anime item from database
     * isFavourite = true => Remove anime item from database
     */
    fun updateFavoriteSelection() {
        databaseHandler.updateFavourite(
            animeDataItem = getLatestAnimeItem(),
            updateDataInDb = true,
            updateFavouriteValue = { currentFavoriteValue ->
                this.isFavourite = currentFavoriteValue
            }
        )
    }

    private fun isAnimeSelectedAsFavourite() {
        databaseHandler.checkIfAnimeIsFavourite(
            animeDataItem = getLatestAnimeItem(),
            updateFavouriteValue = { currentFavoriteValue ->
                this.isFavourite = currentFavoriteValue
            }
        )
    }

    private fun callPlayAnimeApi(suspendFunction: suspend () -> Resource<String>) {
        viewModelScope.launch {
            when (val result = suspendFunction()) {
                is Resource.Success -> {
                    parseHtmlResponse(responseAsHtml = result.data!!)
                    isAnimeSelectedAsFavourite()
                }

                is Resource.Error -> {
                    ShowToast.short(message = result.message!!)
                }
            }
            showProgressBar = false
        }
    }

    private fun parseHtmlResponse(responseAsHtml: String) {
        val videoPlayerPageData = AnimeDetailPageApiResponseParser.parseHtmlResponse(htmlStringResponse = responseAsHtml)
        animeTitle = videoPlayerPageData.animeTitle
        animeDescription = videoPlayerPageData.animeDescription
        currentVideoTitle = videoPlayerPageData.currentVideoTitle
        posterImageUrl = videoPlayerPageData.imgUrl
        webViewVideoUrl = videoPlayerPageData.videoUrl
        animeVideoFrameUrl = videoPlayerPageData.videoUrl
        videoPlayerEpisodeItems = videoPlayerPageData.videoPlayerEpisodeItems
    }
}