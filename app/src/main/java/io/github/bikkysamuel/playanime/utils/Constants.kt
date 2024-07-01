package io.github.bikkysamuel.playanime.utils

object Constants {

    const val PLAY_ANIME_DATABASE = "db_play_anime"
    const val ACTION_OVERLAY_CREATE_IDLE = "my.service.overlay.create.IDLE"
    const val ACTION_OVERLAY_CREATE_CALL = "my.service.overlay.create.CALL"

    const val USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:109.0) Gecko/20100101 Firefox/115.0"
    const val BASE_URL = "https://s3taku.com/"
    const val BASE_URL_DUB_POSTFIX = "recently-added-dub"
    const val VIDEO_END_POINT_URL_PREFIX = "/videos/"

    const val ERROR_MESSAGE_ON_API_FAILURE = "Could not load data due to an error. Please try later."
    const val ERROR_MESSAGE_ON_API_FAILURE_PAGE_NUMBER = "Error loading data using page number...."
    const val ERROR_MESSAGE_ON_API_FAILURE_VIDEO_DATA = "Error loading video data...."
    const val ERROR_MESSAGE_ON_API_FAILURE_SEARCH = "Error loading search with keyword data...."

    const val SEARCH_WITH_KEYWORD = "search.html"

    const val WEB_VIEW_REDIRECT_DOWNLOAD_PAGE_URL_PREFIX = "${BASE_URL}download?id="
    const val WEB_VIEW_REDIRECT_DOWNLOAD_VIDEO_URL_PREFIX = "https://gredirect.info/download.php?url="

    const val WEB_VIEW_FULLSCREEN_RATIO = "1:0" // 1:0 is for Full Screen
    const val WEB_VIEW_NORMAL_WINDOW_RATIO = "16:10.59" //correct ratio for the video frame used - to avoid scroll in view and show all controls

    // DataStore Handler
    const val DATASTORE_SETTINGS = "settings"
    const val DATASTORE_SELECTED_THEME = "selected_theme"
    const val DATASTORE_SELECTED_FONT_STYLE = "selected_font_style"
}