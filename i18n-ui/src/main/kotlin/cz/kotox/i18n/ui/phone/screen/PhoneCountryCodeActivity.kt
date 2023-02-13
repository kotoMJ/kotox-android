package cz.kotox.i18n.ui.phone.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import cz.kotox.core.ui.theme.KotoxBasicTheme
import dagger.hilt.android.AndroidEntryPoint

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