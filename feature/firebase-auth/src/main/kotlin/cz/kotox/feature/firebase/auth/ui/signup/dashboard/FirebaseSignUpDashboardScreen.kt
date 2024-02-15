package cz.kotox.feature.firebase.auth.ui.signup.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cz.kotox.common.designsystem.component.button.FilledTonalButton
import cz.kotox.common.designsystem.extension.basicButton
import cz.kotox.feature.firebase.auth.R

@Composable
fun FirebaseSignUpDashboard(
    modifier: Modifier = Modifier
){

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        FilledTonalButton(
            textRes = R.string.login_screen_login_details,
            modifier = Modifier.basicButton(),
            action = {}
        )

    }
}
