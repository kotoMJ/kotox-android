package cz.kotox.common.i18n.domain.usecase

import cz.kotox.common.i18n.data.api.repository.CountryRepository
import cz.kotox.common.i18n.domain.mapper.CountryModelMapper
import cz.kotox.common.i18n.domain.model.CountryUiModel
import javax.inject.Inject

class CountryCodeListUseCase @Inject constructor(
    private val countryModelMapper: CountryModelMapper,
    private val countryRepository: CountryRepository,
) {

    suspend fun get(): List<CountryUiModel> =
        countryRepository.getPhoneNumberPrefixModels().map { countryModelMapper.map(it) }

}