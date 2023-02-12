package cz.kotox.domain.usecase

import cz.kotox.data.api.repository.CountryRepository
import cz.kotox.domain.mapper.CountryModelMapper
import cz.kotox.domain.model.CountryUiModel
import javax.inject.Inject

class CountryCodeListUseCase @Inject constructor(
    private val countryRepository: CountryRepository,
    private val countryModelMapper: CountryModelMapper,
) {

    suspend fun get(): List<CountryUiModel> =
        countryRepository.getPhoneNumberPrefixModels().map { countryModelMapper.map(it) }

}