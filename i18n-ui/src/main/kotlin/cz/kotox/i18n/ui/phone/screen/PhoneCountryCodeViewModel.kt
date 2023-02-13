package cz.kotox.i18n.ui.phone.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.kotox.domain.model.CountryUiModel
import cz.kotox.domain.usecase.CountryCodeListUseCase
import cz.kotox.domain.usecase.CountryCodeDetectUseCase
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
    val countryCodeModel: CountryUiModel = CountryUiModel.CountryUiModelEmptyItem()
)


@HiltViewModel
class PhoneCountryCodeViewModel @Inject constructor(
    private val countryListUseCase: CountryCodeListUseCase,
    private val countryCodeDetectUseCase: CountryCodeDetectUseCase
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
        val digitOnlyValue = value.filter { it.isDigit() }
        viewModelScope.launch {
            val countryCodeDetection = countryCodeDetectUseCase.get(digitOnlyValue)

            if (countryCodeDetection == _uiState.value.countryCodeModel) {
                _uiState.update {
                    it.copy(phoneInput = digitOnlyValue)
                }
            } else {
                _uiState.update {
                    it.copy(
                        phoneInput = digitOnlyValue,
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