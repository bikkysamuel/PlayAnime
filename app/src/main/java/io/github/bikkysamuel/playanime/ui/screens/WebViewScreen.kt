package io.github.bikkysamuel.playanime.ui.screens

import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import io.github.bikkysamuel.playanime.utils.Constants
import io.github.bikkysamuel.playanime.utils.VideoWebViewClient

@Composable
fun WebViewScreen(
    animeVideoFrameUrl: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1.5108f) //16:10.59
    ) {
        val localContext = LocalContext.current
        AndroidView(factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                webViewClient = VideoWebViewClient(
                    startAppChooserActivity = { intent ->
                        startActivity(localContext, intent, null)
                    },
                    expandWebView = {
                        // TODO
//                        MyUtils.setDimensionRatioOnWebViewVideoPlayer(webViewVideoPlayer, Constants.WEB_VIEW_FULLSCREEN_RATIO)
                    }
                )

                settings.apply {
                    userAgentString = Constants.USER_AGENT
                    loadsImagesAutomatically = true
                    javaScriptEnabled = true
                    allowFileAccess = true
                    javaScriptCanOpenWindowsAutomatically = true
                    pluginState = WebSettings.PluginState.ON
                    mediaPlaybackRequiresUserGesture = false
                    domStorageEnabled = true
//                    setAppCacheMaxSize(1024 * 8);
                    setRenderPriority(WebSettings.RenderPriority.HIGH)
//                    setAppCacheEnabled(true);
                    cacheMode = WebSettings.LOAD_DEFAULT
                }

                scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
                requestFocus(View.FOCUS_DOWN)
                loadUrl("https:${animeVideoFrameUrl}")
            }
        }, update = {
//            val frameString = animeDetailViewModel.animeVideoFrameUrl
//            it.loadDataWithBaseURL(null, frameString, "text/html", "utf-8", null)
        })
    }
}