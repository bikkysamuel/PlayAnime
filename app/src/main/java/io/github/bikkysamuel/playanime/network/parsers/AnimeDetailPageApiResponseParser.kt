package io.github.bikkysamuel.playanime.network.parsers

import io.github.bikkysamuel.playanime.models.VideoPlayerEpisodeItem
import io.github.bikkysamuel.playanime.models.VideoPlayerPageData
import io.github.bikkysamuel.playanime.utils.HtmlDocumentCssQuery
import io.github.bikkysamuel.playanime.utils.VideoType
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

object AnimeDetailPageApiResponseParser {

    private lateinit var htmlDocument: Document

    fun parseHtmlResponse(htmlStringResponse: String): VideoPlayerPageData {
        htmlDocument = Jsoup.parse(htmlStringResponse)
        return detailPageParser()
    }

    private fun detailPageParser(): VideoPlayerPageData {
        val animeTitle = animeTitleParser()
        val animeDescription = animeDescriptionParser()
        var animeVideoFrameUrl: String = ""
        var currentVideoTitle: String = ""
        animeCurrentVideoDataParser { videoFrameUrl, videoTitle ->
            animeVideoFrameUrl = videoFrameUrl
            currentVideoTitle = videoTitle
        }
        println("BKS - parsed animeVideoFrameUrl: $animeVideoFrameUrl")
        return animeEpisodesListParser(
            animeTitle = animeTitle,
            animeDescription = animeDescription,
            animeVideoFrameUrl = animeVideoFrameUrl,
            currentVideoTitle = currentVideoTitle
        )
    }

    private fun animeTitleParser() =
        htmlDocument.select(HtmlDocumentCssQuery.VIDEO_PLAYER_ANIME_TITLE).text()

    private fun animeDescriptionParser() =
        htmlDocument.select(HtmlDocumentCssQuery.VIDEO_PLAYER_ANIME_DESCRIPTION).text()

    private fun animeCurrentVideoDataParser(updateVideoData: (String, String) -> Unit) {
        val animeVideoFrameUrl = htmlDocument.select(HtmlDocumentCssQuery.VIDEO_FRAME).attr("src")
        val currentVideoTitle =
            htmlDocument.select(HtmlDocumentCssQuery.VIDEO_PLAYER_CURRENT_EPISODE_TITLE).text()
        updateVideoData(animeVideoFrameUrl, currentVideoTitle)
    }

    private fun animeEpisodesListParser(
        animeTitle: String,
        animeDescription: String,
        animeVideoFrameUrl: String,
        currentVideoTitle: String
    ): VideoPlayerPageData {
        val elementsEpisodes = Elements()
        elementsEpisodes.addAll(htmlDocument.select(HtmlDocumentCssQuery.EPISODES))
        val videoPageEpisodeItems: ArrayList<VideoPlayerEpisodeItem> =
            ArrayList<VideoPlayerEpisodeItem>()
        for (element in elementsEpisodes) {
            val posterUrl =
                element.select(HtmlDocumentCssQuery.VIDEO_PLAYER_ANIME_POSTER_URL).attr("src")
            val videoUrl = element.attr("href")
            val videoType =
                element.select(HtmlDocumentCssQuery.VIDEO_PLAYER_ANIME_VIDEO_TYPE).text()
            val fullEpisodeName =
                element.select(HtmlDocumentCssQuery.VIDEO_PLAYER_ANIME_FULL_EPISODE_NAME).text()
            val uploadDate =
                element.select(HtmlDocumentCssQuery.VIDEO_PLAYER_ANIME_UPLOAD_DATE).text()
            val firstIndexOfEpisodeStr = fullEpisodeName.indexOf("Episode ")
            val lenOfEpisodeStr = "Episode ".length
            val spaceIndexAfterEpisodeNumber =
                fullEpisodeName.indexOf(" ", firstIndexOfEpisodeStr + lenOfEpisodeStr + 1)
            val episode = fullEpisodeName.substring(
                firstIndexOfEpisodeStr + lenOfEpisodeStr,
                if (spaceIndexAfterEpisodeNumber != -1) spaceIndexAfterEpisodeNumber else fullEpisodeName.length
            )

            val videoPlayerEpisodeItem = VideoPlayerEpisodeItem(
                name = animeTitle,
                imgUrl = posterUrl,
                videoUrl = videoUrl,
                videoType = VideoType.parseVideoType(videoType),
                uploadDate = uploadDate,
                episode = episode,
                season = "" //TODO Season string parse from HTML
            )

            videoPageEpisodeItems.add(videoPlayerEpisodeItem)
        }
        return VideoPlayerPageData(
            animeTitle = animeTitle,
            animeDescription = animeDescription,
            imgUrl = videoPageEpisodeItems[0].imgUrl,
            videoUrl = animeVideoFrameUrl,
            currentVideoTitle = currentVideoTitle,
            videoPlayerEpisodeItems = videoPageEpisodeItems
        )

    }
}