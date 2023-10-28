package cz.kotox.common.domain.usecase

import cz.kotox.common.i18n.data.api.repository.CountryRepository
import cz.kotox.common.domain.mapper.CountryModelMapper
import cz.kotox.common.domain.model.CountryUiModel
import javax.inject.Inject

class CountryCodeListUseCase @Inject constructor(
    private val countryRepository: CountryRepository,
    private val countryModelMapper: CountryModelMapper,
) {

    suspend fun get(): List<CountryUiModel> =
        countryRepository.getPhoneNumberPrefixModels().map { countryModelMapper.map(it) }

}