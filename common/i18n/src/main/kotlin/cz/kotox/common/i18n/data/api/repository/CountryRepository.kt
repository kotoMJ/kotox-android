package cz.kotox.common.i18n.data.api.repository

import cz.kotox.common.i18n.data.api.model.CountryModel


interface CountryRepository {
    suspend fun getPhoneNumberPrefixModels(): List<CountryModel>
    suspend fun getPhoneNumberPrefixCountryMap(): Map<String, Int>
}
