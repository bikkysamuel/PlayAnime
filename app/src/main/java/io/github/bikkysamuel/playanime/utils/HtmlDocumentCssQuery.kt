package io.github.bikkysamuel.playanime.utils

object HtmlDocumentCssQuery {

    private const val MAIN_CONTENT = "div.main-content "
    private const val VIDEO_INFO_LEFT = MAIN_CONTENT + "div.video-info div.video-info-left "
    const val VIDEO_FRAME = VIDEO_INFO_LEFT + "div.watch_play div.play-video iframe "
    const val EPISODES = VIDEO_INFO_LEFT + "ul.listing.items.lists li.video-block a "

    var DOC_HOME_ANIME_LIST = "li.video-block"
    var DOC_HOME_ANIME_ITEM_VIDEO_URL = "a"
    var DOC_HOME_ANIME_ITEM_IMAGE_URL = "a div.img div.picture img"
    var DOC_HOME_ANIME_ITEM_NAME = "a div.name"
    var DOC_HOME_ANIME_ITEM_UPLOAD_DATE = "a div.meta span.date"

    private const val VIDEO_PLAYER_PREFIX = "div.main-content div.video-info div.video-info-left "
    const val VIDEO_PLAYER_CURRENT_EPISODE_TITLE = VIDEO_PLAYER_PREFIX + "h1"
    private const val VIDEO_PLAYER_INFO_LEFT_PREFIX = VIDEO_PLAYER_PREFIX + "div.video-details "
    const val VIDEO_PLAYER_ANIME_TITLE = VIDEO_PLAYER_INFO_LEFT_PREFIX + "span.date"
    const val VIDEO_PLAYER_ANIME_DESCRIPTION = VIDEO_PLAYER_INFO_LEFT_PREFIX + "div.post-entry div.content-more-js#rmjs-1"
    const val VIDEO_PLAYER_ANIME_POSTER_URL = "div.img div.picture img"
    const val VIDEO_PLAYER_ANIME_VIDEO_TYPE = "div.img div.type span"
    const val VIDEO_PLAYER_ANIME_FULL_EPISODE_NAME = "div.name"
    const val VIDEO_PLAYER_ANIME_UPLOAD_DATE = "div.meta span.date"
}