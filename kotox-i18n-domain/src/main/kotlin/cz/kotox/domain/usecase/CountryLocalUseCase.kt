package cz.kotox.domain.usecase

import android.content.Context
import android.telephony.TelephonyManager
import cz.kotox.data.api.repository.CountryRepository
import cz.kotox.domain.mapper.CountryModelMapper
import cz.kotox.domain.model.CountryUiModel
import cz.kotox.domain.model.CountryUiModelValueItem
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject

class CountryLocalUseCase @Inject constructor(
    private val countryRepository: CountryRepository,
    private val countryModelMapper: CountryModelMapper,
    @ApplicationContext private val context: Context,
) {

    suspend fun get(locale: Locale = Locale.getDefault()): CountryUiModel {
        val currentCountryCode = locale.country

        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val networkCountryIso = telephonyManager.networkCountryIso.uppercase()

        val localPhoneCountryCode = networkCountryIso.ifBlank { currentCountryCode }
        val allPhonePrefixes =
            countryRepository.getPhoneNumberPrefixModels().map { countryModelMapper.map(it) }


        return allPhonePrefixes.firstOrNull { it is CountryUiModelValueItem && it.isoCode == localPhoneCountryCode }
            ?: allPhonePrefixes.firstOrNull()
            ?: CountryUiModel.CountryUiModelFallbackItem()
    }
}
