package cz.kotox.common.i18n.domain.model

import com.google.i18n.phonenumbers.PhoneNumberUtil
import java.util.Locale

/**
 * TODO Provide countries in better way to project. This is just fast typed howto.
 */

data class Country(
    val code: String,
    val name: String,
    val phonePrefixCode: Int,
    val flagEmoji: String
)

fun countries(): List<Country> {
    val phoneUtil = PhoneNumberUtil.getInstance()

    return Locale.getISOCountries().map {
        Country(
            code = it,
            name = Locale("", it).displayCountry,
            phonePrefixCode = phoneUtil.getCountryCodeForRegion(it),
            flagEmoji = flagEmoji(it)
        )
    }
}

@Suppress("MagicNumber")
fun flagEmoji(countryCode: String = Locale.CANADA.country): String {
    val firstLetter = Character.codePointAt(countryCode, 0) - 0x41 + 0x1F1E6
    val secondLetter = Character.codePointAt(countryCode, 1) - 0x41 + 0x1F1E6
    return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
}
