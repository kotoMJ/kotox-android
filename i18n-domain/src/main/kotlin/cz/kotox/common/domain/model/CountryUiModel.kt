package cz.kotox.common.domain.model

import cz.kotox.common.i18n.data.api.model.FALLBACK_COUNTRY_CODE
import cz.kotox.common.i18n.data.api.model.FALLBACK_COUNTRY_NAME
import cz.kotox.common.i18n.data.api.model.FALLBACK_EXAMPLE_MAX_NUMBER_LENGTH
import cz.kotox.common.i18n.data.api.model.FALLBACK_EXAMPLE_NUMBER
import cz.kotox.common.i18n.data.api.model.FALLBACK_FLAG_EMOJI
import cz.kotox.common.i18n.data.api.model.FALLBACK_ISO_CODE

interface CountryUiModelValueItem {
    val isoCode: String
    val countryName: String
    val countryCode: Int?
    val numberHintWithoutCountryCode: String
    val expectedLength: Int?
}

sealed class CountryUiModel(
    open val flagEmoji: String
) {

    data class CountryUiModelItem(
        override val isoCode: String,
        override val countryName: String,
        override val countryCode: Int?,
        override val flagEmoji: String,
        override val numberHintWithoutCountryCode: String,
        override val expectedLength: Int?
    ) : CountryUiModelValueItem, CountryUiModel(flagEmoji)

    data class CountryUiModelFallbackItem(
        override val isoCode: String = FALLBACK_ISO_CODE,
        override val countryName: String = FALLBACK_COUNTRY_NAME,
        override val countryCode: Int? = FALLBACK_COUNTRY_CODE,
        override val flagEmoji: String = FALLBACK_FLAG_EMOJI,
        override val numberHintWithoutCountryCode: String = FALLBACK_EXAMPLE_NUMBER,
        override val expectedLength: Int? = FALLBACK_EXAMPLE_MAX_NUMBER_LENGTH
    ) : CountryUiModelValueItem, CountryUiModel(flagEmoji)


    data class CountryUiModelEmptyItem(
        override val flagEmoji: String = "❌"
    ) : CountryUiModel(flagEmoji)
}