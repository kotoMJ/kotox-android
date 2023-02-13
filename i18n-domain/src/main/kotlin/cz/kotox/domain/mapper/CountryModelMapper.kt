package cz.kotox.domain.mapper

import com.google.i18n.phonenumbers.PhoneNumberUtil
import cz.kotox.core.domain.Mapper
import cz.kotox.data.api.model.CountryModel
import cz.kotox.domain.model.CountryUiModel
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject

class CountryModelMapper @Inject constructor(
    private val phoneNumberUtil: PhoneNumberUtil
) : Mapper<CountryModel, CountryUiModel> {
    override fun map(from: CountryModel): CountryUiModel {

        // CZ420, TT1868
        val dialCode = try {
            from.countryCode.filter { it.isDigit() }.toInt()
        } catch (nfe: NumberFormatException) {
            null
        }

        // CZ 601123456, TT 8682911234, AQ null
        val exampleNumber: String? = phoneNumberUtil.getExampleNumberForType(
            from.isoCode,
            PhoneNumberUtil.PhoneNumberType.MOBILE
        )?.nationalNumber?.toString()

        // CZ 420, TT1
        val countryCodeForRegion = phoneNumberUtil.getCountryCodeForRegion(from.isoCode)

        /**
         * Countries like TT have complex national prefix (countryCode+leadingDigits) which is part of exampleNumber.
         * Our aim is to remove this prefix from example number if it is combined.
         */
        val exampleNumberFiltered: String? = if (dialCode == countryCodeForRegion) {
            exampleNumber
        } else {
            val leadingDigits = dialCode.toString().removePrefix(countryCodeForRegion.toString())
            exampleNumber?.removePrefix(leadingDigits)
        }

        return  CountryUiModel.CountryUiModelItem(
            isoCode = from.isoCode,
            countryCode = dialCode,
            name = Locale("", from.isoCode).displayCountry,
            flagEmoji = from.flagEmoji,
            numberHintWithoutCountryCode = exampleNumberFiltered ?: ""
        )
    }
}
