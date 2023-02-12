package cz.kotox.domain.model

import cz.kotox.data.api.model.FALLBACK_COUNTRY_CODE
import cz.kotox.data.api.model.FALLBACK_COUNTRY_NAME
import cz.kotox.data.api.model.FALLBACK_EXAMPLE_NUMBER
import cz.kotox.data.api.model.FALLBACK_FLAG_EMOJI
import cz.kotox.data.api.model.FALLBACK_ISO_CODE

interface CountryUiModelValueItem {
    val isoCode: String
    val name: String
    val countryCode: Int?
    val numberHint: String
}

sealed class CountryUiModel(
    open val flagEmoji: String
) {

    data class CountryUiModelItem(
        override val isoCode: String,
        override val name: String,
        override val countryCode: Int?,
        override val flagEmoji: String,
        override val numberHint: String,
    ) : CountryUiModelValueItem, CountryUiModel(flagEmoji)

    data class CountryUiModelFallbackItem(
        override val isoCode: String = FALLBACK_ISO_CODE,
        override val name: String = FALLBACK_COUNTRY_NAME,
        override val countryCode: Int? = FALLBACK_COUNTRY_CODE,
        override val flagEmoji: String = FALLBACK_FLAG_EMOJI,
        override val numberHint: String = FALLBACK_EXAMPLE_NUMBER,
        ) : CountryUiModelValueItem, CountryUiModel(flagEmoji)


    data class CountryUiModelEmptyItem(
        override val flagEmoji: String
    ) : CountryUiModel(flagEmoji)
}