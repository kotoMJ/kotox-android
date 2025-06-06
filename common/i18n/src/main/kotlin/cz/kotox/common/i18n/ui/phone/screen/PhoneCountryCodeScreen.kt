package cz.kotox.common.i18n.ui.phone.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Immutable
interface PhoneCountryCodeListener {
    fun onPhoneValueChange(value: String)
    fun onPhoneCountryCodeSelected(value: cz.kotox.common.i18n.domain.model.CountryUiModel)
}

@Composable
fun PhoneCountryCodeScreen(
    modifier: Modifier = Modifier,
    viewModel: PhoneCountryCodeViewModel = hiltViewModel()
) {

    val state = viewModel.uiState.collectAsStateWithLifecycle()

    val listener = remember(viewModel) {
        object : PhoneCountryCodeListener {
            override fun onPhoneValueChange(value: String) {
                viewModel.onPhoneValueChange(value)
            }

            override fun onPhoneCountryCodeSelected(value: cz.kotox.common.i18n.domain.model.CountryUiModel) {
                viewModel.onPhoneCountryCodeSelected(value)
            }
        }
    }



    Scaffold(
        modifier = modifier,
        topBar = {
            //TODO
        },
    ) { paddingValues ->

        PhoneCountryCodeContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            listener = listener
        )
    }

}