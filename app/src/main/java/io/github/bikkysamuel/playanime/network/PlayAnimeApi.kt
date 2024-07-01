package io.github.bikkysamuel.playanime.network

import io.github.bikkysamuel.playanime.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PlayAnimeApi {

    @GET
    suspend fun getAnimeHomeDataWithPageNumber(
        @Url dubIfRequired: String,
        @Query("page") pageNumber: String
    ): String

    @GET
    suspend fun getVideoSelectedData(
        @Url videoUrl: String
    ): String

    @GET(Constants.SEARCH_WITH_KEYWORD)
    suspend fun getSearchWithKeywords(
        @Query("keyword") keyword: String,
        @Query("page") pageNumber: Int
    ): String
}