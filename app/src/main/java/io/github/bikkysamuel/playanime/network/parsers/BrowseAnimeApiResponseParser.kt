package io.github.bikkysamuel.playanime.network.parsers

import io.github.bikkysamuel.playanime.localstorage.db.AnimeDataItem
import io.github.bikkysamuel.playanime.utils.HtmlDocumentCssQuery
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

object BrowseAnimeApiResponseParser {

    fun parseResponseData(responseAsHtml: String) : List<AnimeDataItem> {
        val animeDataItems: ArrayList<AnimeDataItem> = ArrayList()
        val elements: Elements = Elements()

        val document: Document = Jsoup.parse(responseAsHtml)
        elements.addAll(document.select(HtmlDocumentCssQuery.DOC_HOME_ANIME_LIST))

        for (i in 0..<elements.size) {
            val element: Element = elements[i]
            val videoUrl = element.select(HtmlDocumentCssQuery.DOC_HOME_ANIME_ITEM_VIDEO_URL).attr("href")
            var imgUrl = element.select(HtmlDocumentCssQuery.DOC_HOME_ANIME_ITEM_IMAGE_URL).attr("src")
            val name = element.select(HtmlDocumentCssQuery.DOC_HOME_ANIME_ITEM_NAME).text()
            val date = element.select(HtmlDocumentCssQuery.DOC_HOME_ANIME_ITEM_UPLOAD_DATE).text()

            val imgUrlPrefixWithLoadError = "https://cdnimg.xyz"
            if (imgUrl.startsWith(imgUrlPrefixWithLoadError)) {
                val replacementForImgUrlPrefix = "https://gogocdn.net"
                imgUrl = imgUrl.replace(imgUrlPrefixWithLoadError, replacementForImgUrlPrefix)
            }

            val animeDataItem: AnimeDataItem =
                AnimeDataItem(id = i, name = name, description = "", uploadedDate = date,
                    videoUrl = videoUrl, imgUrl = imgUrl)
            animeDataItems.add(animeDataItem)
        }

        return animeDataItems
    }
}