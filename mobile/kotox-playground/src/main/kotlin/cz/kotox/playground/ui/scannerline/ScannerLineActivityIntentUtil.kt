package cz.kotox.playground.ui.scannerline

import android.content.Context
import android.content.Intent

object ScannerLineActivityIntentUtil {

    fun getScannerLineStartIntent(
        context: Context,
    ): Intent {
        val clazz = ScannerLineActivity::class.java
        return Intent(context.applicationContext, clazz)
    }

}