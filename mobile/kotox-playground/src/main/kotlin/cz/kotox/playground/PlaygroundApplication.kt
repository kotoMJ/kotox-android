package cz.kotox.playground

import android.app.Application
import cz.kotox.android.playground.BuildConfig
import cz.kotox.common.ui.ThemeUtils
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class PlaygroundApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        ThemeUtils.initialize(resources)
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object {
        lateinit var application: PlaygroundApplication
            private set
    }

    init {
        application = this
    }
}
