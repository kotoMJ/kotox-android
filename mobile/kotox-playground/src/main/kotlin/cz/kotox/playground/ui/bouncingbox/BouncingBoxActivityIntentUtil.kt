package cz.kotox.playground.ui.bouncingbox

import android.content.Context
import android.content.Intent

object BouncingBoxActivityIntentUtil {

    fun getPhoneCountryCodeStartIntent(
        context: Context,
    ): Intent {
        val clazz = BouncingBoxActivity::class.java
        return Intent(context.applicationContext, clazz)
    }

}