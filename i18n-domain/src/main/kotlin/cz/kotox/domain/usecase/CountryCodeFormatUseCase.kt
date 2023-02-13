package cz.kotox.domain.usecase

import com.google.i18n.phonenumbers.AsYouTypeFormatter
import com.google.i18n.phonenumbers.PhoneNumberUtil
import cz.kotox.domain.model.CountryUiModel
import timber.log.Timber
import javax.inject.Inject


class CountryCodeFormatUseCase @Inject constructor(
    private val phoneNumberUtil: PhoneNumberUtil
) {

    suspend fun get(phoneNumberValue: String, countryModel: CountryUiModel): String {

        if (phoneNumberValue.isBlank()) return phoneNumberValue

        return if (countryModel is CountryUiModel.CountryUiModelItem) {

            try {
                val formatter: AsYouTypeFormatter =
                    phoneNumberUtil.getAsYouTypeFormatter(countryModel.isoCode)

                var formattedNumber = ""
                phoneNumberValue.filter { it.isDigit() }.forEach {
                    formattedNumber = formatter.inputDigit(it)
                }

                Timber.d(">>>_ formatted $phoneNumberValue -> $formattedNumber")
                formattedNumber
            } catch (@Suppress("TooGenericExceptionCaught", "SwallowedException") npe: Exception) {
                phoneNumberValue
            }


        } else {
            phoneNumberValue
        }

    }
}
