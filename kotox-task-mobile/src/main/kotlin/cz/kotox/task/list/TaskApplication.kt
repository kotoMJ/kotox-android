package cz.kotox.task.list

import android.app.Application
import cz.kotox.core.ui.ThemeUtils
import cz.kotox.android.task.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class TaskApplication : Application() {

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
        lateinit var application: TaskApplication
            private set
    }

    init {
        application = this
    }
}