package cz.kotox.data.api.model

import com.squareup.moshi.Json

const val FALLBACK_ISO_CODE = "US"
const val FALLBACK_COUNTRY_NAME = "United States"
const val FALLBACK_COUNTRY_CODE = 1
const val FALLBACK_COUNTRY_CODE_PLUS_STRING = "+1"
const val FALLBACK_FLAG_EMOJI = "🇺🇸"
const val FALLBACK_EXAMPLE_NUMBER = "123 456 7890"
const val FALLBACK_EXAMPLE_MAX_NUMBER_LENGTH = 10


/**
 * https://countrycode.org/
 */

data class CountryModel(
    @Json(name = "code")
    val isoCode: String = FALLBACK_ISO_CODE,
    @Json(name = "name")
    val name: String = FALLBACK_COUNTRY_NAME,
    @Json(name = "dial_code")
    val countryCode: String = FALLBACK_COUNTRY_CODE_PLUS_STRING,
    @Json(name = "flag")
    val flagEmoji: String = FALLBACK_FLAG_EMOJI
)