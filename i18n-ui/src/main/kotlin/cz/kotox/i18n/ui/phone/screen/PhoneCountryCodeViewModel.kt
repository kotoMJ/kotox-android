package cz.kotox.i18n.ui.phone.screen

import android.telephony.PhoneNumberUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.kotox.common.domain.model.CountryUiModel
import cz.kotox.common.domain.usecase.CountryCodeListUseCase
import cz.kotox.common.domain.usecase.CountryCodeDetectUseCase
import cz.kotox.common.domain.usecase.CountryCodeFormatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PhoneCountryCodeFeedState(
    val isInitialLoading: Boolean = true,
    val countries: List<CountryUiModel> = emptyList(),
    val phoneInput: String = "",
    val countryCodeModel: CountryUiModel = CountryUiModel.CountryUiModelEmptyItem(),
)


@HiltViewModel
class PhoneCountryCodeViewModel @Inject constructor(
    private val countryListUseCase: CountryCodeListUseCase,
    private val countryCodeDetectUseCase: CountryCodeDetectUseCase,
    private val countryCodeFormatUseCase: CountryCodeFormatUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PhoneCountryCodeFeedState>(PhoneCountryCodeFeedState())
    val uiState: StateFlow<PhoneCountryCodeFeedState> = _uiState

    init {
        viewModelScope.launch {
            loadData()
        }
    }

    suspend fun loadData() {
        val countryCodePhoneList = countryListUseCase.get()
        _uiState.value = PhoneCountryCodeFeedState(false, countryCodePhoneList)
    }

    fun onPhoneValueChange(value: String) {
        viewModelScope.launch {
            val countryCodeDetection = countryCodeDetectUseCase.get(value)


            val digitOnlyFormattedValue =
                if (countryCodeDetection is CountryUiModel.CountryUiModelItem) {
                    countryCodeFormatUseCase.get(
                        value,
                        countryCodeDetection
                    )
                } else {
                    value
                }
            if (countryCodeDetection == _uiState.value.countryCodeModel) {
                _uiState.update {
                    it.copy(phoneInput = digitOnlyFormattedValue)
                }
            } else {
                _uiState.update {
                    it.copy(
                        phoneInput = digitOnlyFormattedValue,
                        countryCodeModel = countryCodeDetection
                    )
                }
            }
        }
    }

    fun onPhoneCountryCodeSelected(value: CountryUiModel) {
        _uiState.update {
            it.copy(
                phoneInput = if (value is CountryUiModel.CountryUiModelItem) value.countryCode?.toString()
                    ?: "" else "",
                countryCodeModel = value
            )
        }
    }

}