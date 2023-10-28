package cz.kotox.common.core.android.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import timber.log.Timber

typealias WebLink = String

fun Context.navigateToWeb(link: WebLink) {
    val fixedLink = if (
        link.startsWith("http://") ||
        link.startsWith("https://")
    ) {
        link
    } else {
        "https://$link"
    }
    Timber.d("requested $link ,navigating to:$fixedLink ")
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(fixedLink))
    if (intent.resolveActivity(this.packageManager) != null) {
        this.startActivity(intent)
    }
}
