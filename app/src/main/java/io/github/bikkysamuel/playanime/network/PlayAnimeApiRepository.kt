package io.github.bikkysamuel.playanime.network

import dagger.hilt.android.scopes.ActivityScoped
import io.github.bikkysamuel.playanime.utils.Constants
import io.github.bikkysamuel.playanime.utils.Resource
import javax.inject.Inject

@ActivityScoped
class PlayAnimeApiRepository @Inject constructor(
    private val api: PlayAnimeApi
) {
    suspend fun getHomePageDataWithPageNumber(
        showDubVersion: Boolean,
        pageNumber: Int
    ): Resource<String> {
        val response = try {
            api.getAnimeHomeDataWithPageNumber(
                if (showDubVersion) Constants.BASE_URL_DUB_POSTFIX else "",
                pageNumber.toString()
            )
        } catch (e: Exception) {
            return Resource.Error(Constants.ERROR_MESSAGE_ON_API_FAILURE_PAGE_NUMBER)
        }
        return Resource.Success(response)
    }

    suspend fun getVideoPlayerData(
        videoUrl: String
    ): Resource<String> {
        val response = try {
            api.getVideoSelectedData(videoUrl = videoUrl)
        } catch (e: Exception) {
            return Resource.Error(Constants.ERROR_MESSAGE_ON_API_FAILURE_VIDEO_DATA)
        }
        return Resource.Success(response)
    }

    suspend fun searchWithKeyword(
        keyword: String,
        pageNumber: Int
    ): Resource<String> {
        val response = try {
            api.getSearchWithKeywords(keyword = keyword, pageNumber = pageNumber)
        } catch (e: Exception) {
            return Resource.Error(Constants.ERROR_MESSAGE_ON_API_FAILURE_SEARCH)
        }
        return Resource.Success(response)
    }
}