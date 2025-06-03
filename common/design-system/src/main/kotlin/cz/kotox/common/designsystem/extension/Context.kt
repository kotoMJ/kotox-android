package cz.kotox.common.designsystem.extension

import android.content.Context
import android.content.res.Configuration

fun Context.isDarkMode() = resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES