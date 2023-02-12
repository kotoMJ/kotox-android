package cz.kotox.data.impl.repository

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import cz.kotox.data.api.model.CountryModel
import cz.kotox.data.api.repository.CountryRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import timber.log.Timber

private const val ASSET_COUNTRIES_JSON = "countries.json"

@Singleton
class CountryRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val moshi: Moshi
) : CountryRepository {

    private var phoneNumberPrefixesCache: List<CountryModel> = emptyList()

    private var phoneNumberPrefixCountryCache: Map<String, Int> = emptyMap()

    override suspend fun getPhoneNumberPrefixModels(): List<CountryModel> {

        val listType = Types.newParameterizedType(List::class.java, CountryModel::class.java)
        val adapter: JsonAdapter<List<CountryModel>> = moshi.adapter(listType)

        val srcFile = ASSET_COUNTRIES_JSON

        return phoneNumberPrefixesCache.ifEmpty {
            withContext(IO) {
                try {
                    val countriesJson = context.assets.open(srcFile).bufferedReader().use {
                        it.readText()
                    }
                    val countriesList = adapter.fromJson(countriesJson) ?: emptyList()
                    phoneNumberPrefixesCache = countriesList
                    countriesList
                } catch (@SuppressWarnings("TooGenericExceptionCaught") e: Exception) {
                    Timber.e(e, "Unable to read countries from assets")
                    emptyList()
                }
            }
        }
    }

    override suspend fun getPhoneNumberPrefixCountryMap(): Map<String, Int> {

        return phoneNumberPrefixCountryCache.ifEmpty {

            val phoneNumberPrefixCountryMap =
                getPhoneNumberPrefixModels().associate { phoneNumberPrefixModel ->
                    phoneNumberPrefixModel.isoCode to phoneNumberPrefixModel.countryCode.filter { it.isDigit() }
                        .toInt()
                }

            phoneNumberPrefixCountryCache = phoneNumberPrefixCountryMap
            phoneNumberPrefixCountryMap
        }
    }
}
