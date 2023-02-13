package cz.kotox.domain.usecase

import cz.kotox.data.api.repository.CountryRepository
import cz.kotox.domain.mapper.CountryModelMapper
import cz.kotox.domain.model.CountryUiModel
import timber.log.Timber
import javax.inject.Inject

class CountryCodeDetectUseCase @Inject constructor(
    private val countryRepository: CountryRepository,
    private val countryModelMapper: CountryModelMapper
) {

    suspend fun get(phoneNumberValue: String): CountryUiModel {

        var countryCode: String? = null
        var phoneCountrCode: Int? = null
        countryRepository.getPhoneNumberPrefixCountryMap().forEach() {
            if (phoneNumberValue.startsWith(it.value.toString())) {
                countryCode = it.key
                phoneCountrCode = it.value
            }
        }

        Timber.d(">>>_ countryCode=${countryCode}, phoneCountryCode=${phoneCountrCode}")
        return if (countryCode == null) {
            CountryUiModel.CountryUiModelEmptyItem()
        } else {
            val phoneNumberPrefixModel = countryRepository.getPhoneNumberPrefixModels()
                .firstOrNull { it.isoCode == countryCode }

            if (phoneNumberPrefixModel == null) {
                CountryUiModel.CountryUiModelEmptyItem()
            } else {
                countryModelMapper.map(phoneNumberPrefixModel)
            }
        }
    }
}
