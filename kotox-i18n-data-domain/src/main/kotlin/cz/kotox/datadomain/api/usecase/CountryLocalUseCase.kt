package cz.kotox.datadomain.api.usecase

import android.content.Context
import android.telephony.TelephonyManager
import cz.kotox.datadomain.api.CountryModel
import cz.kotox.datadomain.impl.CountryRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject

class CountryLocalUseCase @Inject constructor(
    private val countryRepository: CountryRepository,
    @ApplicationContext private val context: Context,
) {

    suspend fun get(locale: Locale = Locale.getDefault()): CountryModel {
        val currentCountryCode = locale.country

        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val networkCountryIso = telephonyManager.networkCountryIso.uppercase()

        val localPhoneCountryCode = networkCountryIso.ifBlank { currentCountryCode }
        val allPhonePrefixes = countryRepository.getPhoneNumberPrefixModels()
        return allPhonePrefixes.firstOrNull { it.isoCode == localPhoneCountryCode }
            ?: allPhonePrefixes.firstOrNull()
            ?: CountryModel()
    }
}
