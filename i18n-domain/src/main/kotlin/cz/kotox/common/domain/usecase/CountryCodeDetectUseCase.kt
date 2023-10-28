package cz.kotox.common.domain.usecase

import cz.kotox.common.i18n.data.api.repository.CountryRepository
import cz.kotox.common.domain.mapper.CountryModelMapper
import cz.kotox.common.domain.model.CountryUiModel
import timber.log.Timber
import javax.inject.Inject

class CountryCodeDetectUseCase @Inject constructor(
    private val countryRepository: CountryRepository,
    private val countryModelMapper: CountryModelMapper
) {

    suspend fun get(phoneNumberValue: String): CountryUiModel {

        var isoCode: String? = null
        var countryCode: Int? = null
        countryRepository.getPhoneNumberPrefixCountryMap().forEach() {
            if (phoneNumberValue.startsWith(it.value.toString())) {
                isoCode = it.key
                countryCode = it.value
            }
        }

        Timber.d(">>>_ countryCode=${isoCode}, phoneCountryCode=${countryCode}")
        return if (isoCode == null) {
            CountryUiModel.CountryUiModelEmptyItem()
        } else {
            val phoneNumberPrefixModel = countryRepository.getPhoneNumberPrefixModels()
                .firstOrNull { it.isoCode == isoCode }

            if (phoneNumberPrefixModel == null) {
                CountryUiModel.CountryUiModelEmptyItem()
            } else {
                countryModelMapper.map(phoneNumberPrefixModel)
            }
        }
    }
}
