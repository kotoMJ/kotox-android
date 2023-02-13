package cz.kotox.i18n.ui.country

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.kotox.domain.model.CountryUiModel
import cz.kotox.domain.usecase.CountryCodeListUseCase
import cz.kotox.domain.usecase.CountryCodeLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PhoneCountryCodeFeedState(
    val isInitialLoading: Boolean = true,
    val countries: List<CountryUiModel> = emptyList(),
)


@HiltViewModel
class PhoneCountryCodeViewModel @Inject constructor(
    private val countryListUseCase: CountryCodeListUseCase
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

}