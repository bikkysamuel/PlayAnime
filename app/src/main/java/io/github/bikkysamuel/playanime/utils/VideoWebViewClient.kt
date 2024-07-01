package io.github.bikkysamuel.playanime.utils

import android.content.Intent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class VideoWebViewClient(
    val startAppChooserActivity: (intentChooser: Intent) -> Unit,
    val expandWebView: () -> Unit
) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        val redirectUrl: String = request.url.toString()

        if (redirectUrl.startsWith(Constants.WEB_VIEW_REDIRECT_DOWNLOAD_VIDEO_URL_PREFIX)) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("*/*")
            intent.putExtra(Intent.EXTRA_TEXT, redirectUrl)
            // Create intent to show chooser
            val intentChooser = Intent.createChooser(intent, /* title */ "Download file using....")
            startAppChooserActivity(intentChooser)
        }

        if (redirectUrl.startsWith(Constants.WEB_VIEW_REDIRECT_DOWNLOAD_PAGE_URL_PREFIX)) {
            expandWebView()
        }

        return !(redirectUrl.startsWith(Constants.WEB_VIEW_REDIRECT_DOWNLOAD_PAGE_URL_PREFIX))
    }
}