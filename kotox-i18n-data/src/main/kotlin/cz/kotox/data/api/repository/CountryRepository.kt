package cz.kotox.data.api.repository

import cz.kotox.data.api.model.CountryModel


interface CountryRepository {
    suspend fun getPhoneNumberPrefixModels(): List<CountryModel>
    suspend fun getPhoneNumberPrefixCountryMap(): Map<String, Int>
}
