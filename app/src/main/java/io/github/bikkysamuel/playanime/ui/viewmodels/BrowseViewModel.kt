package io.github.bikkysamuel.playanime.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.bikkysamuel.playanime.localstorage.db.AnimeDataItem
import io.github.bikkysamuel.playanime.network.PlayAnimeApiRepository
import io.github.bikkysamuel.playanime.network.parsers.BrowseAnimeApiResponseParser
import io.github.bikkysamuel.playanime.utils.Resource
import io.github.bikkysamuel.playanime.utils.ShowToast
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrowseViewModel @Inject constructor(
    val playAnimeApiRepo: PlayAnimeApiRepository
) : ViewModel() {

    var animeDataItems = mutableStateListOf<AnimeDataItem>()
        private set

    private var currentHomePageNumber: Int = 0
    private var currentSearchPageNumber: Int = 0

    private var resetData: Boolean = false
    var showDubVersions: Boolean = false
        private set

    var searchKeyword: String = ""
        private set

    var showSearchBar by mutableStateOf(false)
        private set

    var dubOrSubButtonText by mutableStateOf("Sub")
        private set

    var showProgressBar by mutableStateOf(true)
        private set

    /**
     * Check this value when search bar is active.
     * This will allow to hide the list already present from normal browse.
     * It will be true whenever search bar is view,
     * and become false once search result is shown.
     */
    var showEmptyScreenBeforeSearch by mutableStateOf(true)
        private set

    init {
        loadHome()
    }

    private fun resetSearchKeyword() {
        this.searchKeyword = ""
        this.showEmptyScreenBeforeSearch = true
    }

    fun showDubVersion(dubVersionSelected: Boolean) {
        showDubVersions = dubVersionSelected
        setDubOrSubButtonText()
        loadHomePage(resetData = true)
    }

    private fun setDubOrSubButtonText() {
        dubOrSubButtonText = if (showDubVersions) "Dub" else "Sub"
    }

    fun showSearchButtonClicked() {
        showSearchBar = true
    }

    fun closeSearchButtonClicked() {
        showSearchBar = false
        resetSearchKeyword()
        showDubVersion(false)
    }

    private fun loadHome() {
        loadHomePage(animeDataItems.size == 0)
    }

    private fun loadSearchData(): Boolean {
        return searchKeyword.isNotEmpty()
    }

    fun loadHomePage(resetData: Boolean) {
        currentSearchPageNumber = 0
        if (resetData) {
            showProgressBar = true
            currentHomePageNumber = 0
        }
        loadNextPage()
    }

    fun searchWithKeyword(keyword: String, resetData: Boolean) {
        showProgressBar = true
        this.searchKeyword = keyword
        currentHomePageNumber = 0
        if (resetData)
            currentSearchPageNumber = 0
        loadNextPage()
    }

    fun loadNextPage() {
        if (animeDataItems.size == 0)
            showProgressBar = true

        showEmptyScreenBeforeSearch = !loadSearchData()

        val pageNumberToLoad: Int = if (loadSearchData()) {
            ++currentSearchPageNumber
        } else {
            ++currentHomePageNumber
        }
        loadPageNumber(pageNumber = pageNumberToLoad)
    }

    private fun loadPageNumber(pageNumber: Int) {
        clearDataIfPageNumberIsOne(pageNumber)
        callPlayAnimeApi {
            if (loadSearchData())
                playAnimeApiRepo.searchWithKeyword(searchKeyword, pageNumber)
            else
                playAnimeApiRepo.getHomePageDataWithPageNumber(showDubVersions, pageNumber)
        }
    }

    private fun clearDataIfPageNumberIsOne(pageNumber: Int) {
        if (pageNumber == 1)
            resetData = true
    }

    private fun callPlayAnimeApi(suspendFunction: suspend () -> Resource<String>) {
        viewModelScope.launch {
            when (val result = suspendFunction()) {
                is Resource.Success -> {
                    parseHtmlResponse(responseAsHtml = result.data!!)
                }

                is Resource.Error -> {
                    ShowToast.short(message = result.message!!)
                }
            }
            showProgressBar = false
        }
    }

    private fun parseHtmlResponse(responseAsHtml: String) {
        val newAnimeDataItems: List<AnimeDataItem> =
            BrowseAnimeApiResponseParser.parseResponseData(responseAsHtml = responseAsHtml)
        animeDataItems.apply {
            if (resetData)
                clear()
            addAll(newAnimeDataItems)
        }
        resetData = false
    }
}