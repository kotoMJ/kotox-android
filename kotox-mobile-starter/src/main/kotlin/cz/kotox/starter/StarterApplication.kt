package cz.kotox.starter

import android.app.Application
import cz.kotox.android.media.BuildConfig
import cz.kotox.core.ui.ThemeUtils
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class StarterApplication : Application() {

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
        lateinit var application: StarterApplication
            private set
    }

    init {
        application = this
    }
}
