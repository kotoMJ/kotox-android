package cz.kotox.i18n.ui.country

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import cz.kotox.core.android.extension.collectAsStateWithLifecycle
import cz.kotox.core.ui.theme.KotoxBasicTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhoneCountryCodeActivity : ComponentActivity() {

    private val viewModel: PhoneCountryCodeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KotoxBasicTheme {
                PhoneCountryCodeScreen(viewModel = viewModel)
            }
        }

    }

}