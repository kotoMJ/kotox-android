package cz.kotox.core.crypto

import android.os.Build
import android.util.Base64
import timber.log.Timber

object CryptoHelper {

    fun encrypt(value: String, buildVersionSdk: Int? = null): String =
        encodeBas64(value, buildVersionSdk ?: Build.VERSION.SDK_INT)

    fun decrypt(value: String, buildVersionSdk: Int? = null): String =
        decodeBase64(value, buildVersionSdk ?: Build.VERSION.SDK_INT)

    /**
     * Intentionally NOT using android implementation if possible to be able run unit tests.
     * This can be used since Build.VERSION_CODES.O only. Older Android versions needs to use Android Base64
     */
    private fun useAndroidBase64(buildVersionSdk: Int): Boolean =
        buildVersionSdk < Build.VERSION_CODES.O

    private fun encodeBas64(value: String, buildVersionSdk: Int = Build.VERSION.SDK_INT): String {
        return try {
            if (useAndroidBase64(buildVersionSdk)) {
                android.util.Base64.encodeToString(value.encodeToByteArray(), Base64.DEFAULT)
            } else {
                // Intentionally NOT using android implementation to be able run it in unit tests.
                java.util.Base64.getUrlEncoder()
                    .encodeToString(value.encodeToByteArray())
            }
        } catch (e: Exception) {
            Timber.e(e, "Unable to encrypt value $value")
            value
        }
    }

    private fun decodeBase64(value: String, buildVersionSdk: Int = Build.VERSION.SDK_INT): String {

        return try {
            val decodedByteArray: ByteArray =
                if (useAndroidBase64(buildVersionSdk)) {
                    android.util.Base64.decode(value, Base64.DEFAULT)
                } else {
                    // Intentionally NOT using android implementation to be able run it in unit tests.
                    java.util.Base64.getDecoder().decode(value)
                }

            String(decodedByteArray)
        } catch (e: Exception) {
            Timber.e(e, "Unable to decrypt value $value")
            value
        }
    }


}

