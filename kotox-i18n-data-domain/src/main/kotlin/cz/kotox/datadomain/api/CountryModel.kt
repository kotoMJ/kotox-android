package cz.kotox.datadomain.api

import com.squareup.moshi.Json

internal const val FALLBACK_ISO_CODE = "US"
internal const val FALLBACK_COUNTRY_NAME = "United States"
internal const val FALLBACK_DIAL_CODE = 1
internal const val FALLBACK_COUNTRY_CODE_STRING = "+1"
internal const val FALLBACK_FLAG_EMOJI = "🇺🇸"
internal const val FALLBACK_EXAMPLE_NUMBER = "123 456 7890"


/**
 * https://countrycode.org/
 */

data class CountryModel(
    @Json(name = "code")
    val isoCode: String = FALLBACK_ISO_CODE,
    @Json(name = "name")
    val name: String = FALLBACK_COUNTRY_NAME,
    @Json(name = "dial_code")
    val countryCode: String = FALLBACK_COUNTRY_CODE_STRING,
    @Json(name = "flag")
    val flagEmoji: String = FALLBACK_FLAG_EMOJI
)