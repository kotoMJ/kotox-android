package cz.kotox.core.crypto

import android.os.Build
import org.junit.Assert
import org.junit.Test

class CryptoHelperTest {


    val cryptoPairs: List<Pair<String, String>> = listOf(
        "VGhlIGZ1bm55IG5hbWUgZm9yIHN1Y2ggYSBlbGVnYW50IGNhci4=" to "The funny name for such a elegant car.",
        "UGFpcnM=" to "Pairs",
        "VGhlIGNsYXNzaWNhbCBvbmU=" to "The classical one",
        "U2VjdXJpdHkgYXQgZmlyc3QgcGxhY2U=" to "Security at first place",
    )

    @Test
    fun decryptTest() {
        cryptoPairs.forEach { cryptoPair ->
            Assert.assertEquals(
                cryptoPair.second,
                CryptoHelper.decrypt(cryptoPair.first, Build.VERSION_CODES.O)
            )
        }
    }

    @Test
    fun decryptEmptyTest() {
        Assert.assertEquals(
            "",
            CryptoHelper.decrypt("", Build.VERSION_CODES.O)
        )
    }


    @Test
    fun encryptTest() {
        cryptoPairs.forEach { cryptoPair ->
            Assert.assertEquals(
                cryptoPair.first,
                CryptoHelper.encrypt(cryptoPair.second, Build.VERSION_CODES.O)
            )
        }
    }
}