package io.github.bikkysamuel.playanime.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.bikkysamuel.playanime.localstorage.datastore.DataStoreHandler
import io.github.bikkysamuel.playanime.localstorage.db.AnimeDataItem
import io.github.bikkysamuel.playanime.localstorage.db.DatabaseHandler
import io.github.bikkysamuel.playanime.ui.theme.Typography
import io.github.bikkysamuel.playanime.ui.theme.TypographyNotable
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val databaseHandler: DatabaseHandler,
    private val dataStoreHandler: DataStoreHandler
) : ViewModel() {

    var systemStatusBarHeight by mutableFloatStateOf(0f)

    var selectedBottomNavBarItemIndex = mutableIntStateOf(0)

    var showBottomBar by mutableStateOf(true)

    var animeList = mutableStateListOf<AnimeDataItem>()

    var showSearchBar by mutableStateOf(false)
        private set

    var requestFocusOnSearchTextField by mutableStateOf(false)
        private set

    var searchText by mutableStateOf("")
        private set

    var appTheme by mutableStateOf(ThemeSet.SYSTEM_DEFAULT)
        private set

    var appFontStyle by mutableStateOf(FontStyleSet.SYSTEM_DEFAULT)
        private set

    enum class ThemeSet {
        SYSTEM_DEFAULT, DARK, LIGHT, ORANGE_DARK, ORANGE_LIGHT
    }

    enum class FontStyleSet {
        SYSTEM_DEFAULT, NOTABLE
    }

    init {
        loadAppTheme()
        loadFontStyle()
    }

    // START - App Theme and Settings

    // START - Load App Settings

    private fun loadAppTheme() {
        viewModelScope.launch {
            appTheme = when (dataStoreHandler.loadSelectedThemeInDataStore()) {
                ThemeSet.SYSTEM_DEFAULT.name -> ThemeSet.SYSTEM_DEFAULT
                ThemeSet.DARK.name -> ThemeSet.DARK
                ThemeSet.LIGHT.name -> ThemeSet.LIGHT
                ThemeSet.ORANGE_DARK.name -> ThemeSet.ORANGE_DARK
                ThemeSet.ORANGE_LIGHT.name -> ThemeSet.ORANGE_LIGHT
                else -> ThemeSet.SYSTEM_DEFAULT
            }
        }
    }

    private fun loadFontStyle() {
        viewModelScope.launch {
            appFontStyle = when (dataStoreHandler.loadSelectedFontStyleInDataStore()) {
                FontStyleSet.SYSTEM_DEFAULT.name -> FontStyleSet.SYSTEM_DEFAULT
                FontStyleSet.NOTABLE.name -> FontStyleSet.NOTABLE
                else -> FontStyleSet.SYSTEM_DEFAULT
            }
        }
    }

    // END - Load App Settings

    // START - App Theme

    fun compareCurrentAppTheme(themeSet: ThemeSet): Boolean {
        return this.appTheme == themeSet
    }

    private fun updateAppTheme(themeSet: ThemeSet) {
        this.appTheme = themeSet
        viewModelScope.launch {
            dataStoreHandler.saveSelectedThemeInDataStore(themeName = themeSet.name)
        }
    }

    fun setSystemDefaultTheme() {
        updateAppTheme(themeSet = ThemeSet.SYSTEM_DEFAULT)
    }

    fun setDarkTheme() {
        updateAppTheme(themeSet = ThemeSet.DARK)
    }

    fun setLightTheme() {
        updateAppTheme(themeSet = ThemeSet.LIGHT)
    }

    fun setOrangeDarkTheme() {
        updateAppTheme(themeSet = ThemeSet.ORANGE_DARK)
    }

    fun setOrangeLightTheme() {
        updateAppTheme(themeSet = ThemeSet.ORANGE_LIGHT)
    }

    // END - App Theme

    // START - App Font Style

    fun compareCurrentFontStyle(fontStyle: FontStyleSet): Boolean {
        return this.appFontStyle == fontStyle
    }

    private fun updateAppFontStyle(fontStyle: FontStyleSet) {
        this.appFontStyle = fontStyle
        viewModelScope.launch {
            dataStoreHandler.saveSelectedFontStyleInDataStore(fontName = fontStyle.name)
        }
    }

    fun setSystemDefaultFontStyle() {
        updateAppFontStyle(fontStyle = FontStyleSet.SYSTEM_DEFAULT)
    }

    fun setNotableFontStyle() {
        updateAppFontStyle(fontStyle = FontStyleSet.NOTABLE)
    }

    // END - App Font Style

    // END - App Theme and Settings

    // START - UI Actions

    fun loadAllAnimeFromDb() {
        databaseHandler.loadAllDataFromLocalStorage(
            updateAnimeList = { animeItems ->
                this.animeList.clear()
                this.animeList.addAll(animeItems)
            }
        )
    }

    fun filterAnimeUsingSearchText(searchKeyword: String) {
        this.searchText = searchKeyword
        databaseHandler.loadAllDataUsingSearch(
            searchKeyword = this.searchText,
            updateAnimeList = { animeItems ->
                this.animeList.clear()
                this.animeList.addAll(animeItems)
            }
        )
    }

    fun closeSearchMode() {
        showSearchBar = false
        searchText = ""
        requestFocusOnSearchTextField = true
    }

    fun enterSearchMode() {
        showSearchBar = true
    }

    fun doneTyping() {
        requestFocusOnSearchTextField = false
    }

    fun getFontStyleObject(fontStyleSet: FontStyleSet): FontStyleObject {
        return when (fontStyleSet) {
            FontStyleSet.NOTABLE -> FontStyleObject(
                fontStyleSet = fontStyleSet,
                name = "Notable",
                fontFamily = TypographyNotable.bodyLarge.fontFamily!!
            )
            else -> FontStyleObject(
                fontStyleSet = FontStyleSet.SYSTEM_DEFAULT,
                name = "System Default",
                fontFamily = Typography.bodyLarge.fontFamily!!
            )
        }
    }

    data class FontStyleObject(
        var fontStyleSet: FontStyleSet,
        var name: String,
        var fontFamily: FontFamily
    )

    // END - UI Actions
}

