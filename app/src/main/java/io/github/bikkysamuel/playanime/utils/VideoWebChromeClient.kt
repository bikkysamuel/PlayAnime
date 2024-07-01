package io.github.bikkysamuel.playanime.utils

// TODO
//import android.app.Activity
//import android.content.pm.ActivityInfo
//import android.os.Build
//import android.view.View
//import android.webkit.WebChromeClient
//import android.widget.FrameLayout
//import io.github.bikkysamuel.playanime.R
//
//class VideoWebChromeClient(private val activity: Activity) : WebChromeClient() {
//    private var customView: View? = null
//    private var customViewCallback: CustomViewCallback? = null
//    private var originalOrientation = 0
//    private val fullscreenContainer: FrameLayout = activity.findViewById<FrameLayout>(R.id.frmLyt_fullScreenContainer)
//
//    override fun onHideCustomView() {
//        if (customView == null) {
//            return
//        }
//        customView = null
//        fullscreenContainer.visibility = View.GONE
//        fullscreenContainer.removeView(customView)
//        customViewCallback!!.onCustomViewHidden()
//        activity.requestedOrientation = originalOrientation
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            activity.window.setDecorFitsSystemWindows(false)
//        } else {
//            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
//        }
//    }
//
//    override fun onShowCustomView(view: View, callback: CustomViewCallback) {
//        if (customView != null) {
//            onHideCustomView()
//            return
//        }
//        customView = view
//        customViewCallback = callback
//        originalOrientation = activity.requestedOrientation
//        fullscreenContainer.addView(
//            customView,
//            FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.MATCH_PARENT,
//                FrameLayout.LayoutParams.MATCH_PARENT
//            )
//        )
//        fullscreenContainer.visibility = View.VISIBLE
//        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            activity.window.setDecorFitsSystemWindows(false)
//        } else {
//            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
//        }
//    }
//}