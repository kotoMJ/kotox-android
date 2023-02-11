package cz.kotox.datadomain.api.usecase

import cz.kotox.datadomain.api.CountryModel
import cz.kotox.datadomain.impl.CountryRepository
import javax.inject.Inject

data class PhoneNumberPrefixDetection(
    val phoneNumberPrefixModel: CountryModel?,
    val filteredPhoneNumberValue: String
)

class PhoneNumberPrefixDetectUseCase @Inject constructor(
    private val countryRepository: CountryRepository
) {

    suspend fun get(phoneNumberValue: String): PhoneNumberPrefixDetection {

        var countryCode: String? = null
        var phoneNumberPrefix: Int? = null
        countryRepository.getPhoneNumberPrefixCountryMap().forEach() {
            if (phoneNumberValue.startsWith(it.value.toString())) {
                countryCode = it.key
                phoneNumberPrefix = it.value
            }
        }

        return if (countryCode == null) {
            PhoneNumberPrefixDetection(
                phoneNumberPrefixModel = null,
                filteredPhoneNumberValue = phoneNumberValue
            )
        } else {

            val phoneNumberPrefixModel = countryRepository.getPhoneNumberPrefixModels()
                .firstOrNull { it.isoCode == countryCode }

            if (phoneNumberPrefixModel == null) {
                PhoneNumberPrefixDetection(
                    phoneNumberPrefixModel = null,
                    filteredPhoneNumberValue = phoneNumberValue
                )
            } else {
                PhoneNumberPrefixDetection(
                    phoneNumberPrefixModel = phoneNumberPrefixModel,
                    filteredPhoneNumberValue = phoneNumberValue.removePrefix(phoneNumberPrefix.toString())
                )
            }
        }
    }
}
