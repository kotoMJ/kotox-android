package cz.kotox.i18n.ui.country

import android.content.Context
import android.content.Intent

object PhoneCountryCodeActivityIntentUtil {

    fun getPhoneCountryCodeStartIntent(
        context: Context,
    ): Intent {
        val clazz = PhoneCountryCodeActivity::class.java
        return Intent(context.applicationContext, clazz)
    }

}