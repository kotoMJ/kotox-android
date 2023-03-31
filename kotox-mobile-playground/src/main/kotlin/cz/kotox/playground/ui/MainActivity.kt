package cz.kotox.playground.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import cz.kotox.core.ui.theme.KotoxBasicTheme
import cz.kotox.i18n.ui.phone.screen.PhoneCountryCodeActivityIntentUtil
import cz.kotox.playground.ui.bouncingbox.BouncingBoxActivityIntentUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            KotoxBasicTheme {
                MainScreen(
                    onEventHandler = { event ->
                        when (event) {
                            MainScreenEvent.OpenCountriesOverview -> {
                                startActivity(
//                                    PhoneCountryCodeActivityIntentUtil.getPhoneCountryCodeStartIntent(
//                                        context = this,
//                                    )
                                    BouncingBoxActivityIntentUtil.getPhoneCountryCodeStartIntent(
                                        context = this,
                                    )
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}
