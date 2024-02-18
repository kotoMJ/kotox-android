
package cz.kotox.feature.firebase.auth.di

import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import cz.kotox.common.core.config.AppProperties
import cz.kotox.common.core.logging.LogService
import timber.log.Timber
import javax.inject.Inject

class LogServiceModule @Inject constructor(
    private val appProperties: AppProperties
) : LogService {
    override fun logNonFatalCrash(throwable: Throwable) {
        if (appProperties.isDebugBuildType) {
            Timber.e(throwable)
        } else {
            Firebase.crashlytics.recordException(throwable)
        }
    }
}
