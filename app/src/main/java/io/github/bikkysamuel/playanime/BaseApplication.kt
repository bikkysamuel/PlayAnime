package io.github.bikkysamuel.playanime

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BaseApplication : Application() {

    companion object {
        var instance: BaseApplication? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()

        if (instance == null)
            instance = this

        Timber.plant(Timber.DebugTree())
    }
}