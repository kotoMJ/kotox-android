package cz.kotox.media.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import cz.kotox.android.media.R
import cz.kotox.core.android.extension.collectAsStateWithLifecycle
import cz.kotox.core.ui.theme.KotoxBasicTheme
import cz.kotox.core.ui.theme.LocalColors


//Night mode @Preview does not work for scaffold correctly due to current solution in ThemeUtils
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")

@Composable
internal fun MainScreen(
    state: State<MainActivityViewState>,
    listener: MainActivityListener,
    viewModel: MainScreenViewModel = hiltViewModel()
) {

    LaunchedEffect(viewModel, state.value.latestPhotoUri) {
        viewModel.updatePhotoUri(state.value.latestPhotoUri)
    }


    val viewState = viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            Column {
                MainScreenAppBarView(
                    onEventHandler = { event ->
                    },
                    input = MainScreenAppBarInput(stringResource(id = R.string.main_screen_title))
                )
            }
        },
        content = { _ ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.9f)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        if (viewState.value.latestPhotoUri == null) {
                            Box(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .align(Alignment.Center)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_image),
                                        contentDescription = null,
                                        tint = LocalColors.current.onControlsSecondary
                                    )
                                    Text(
                                        text = stringResource(id = R.string.main_screen_image_placeholder),
                                        color = LocalColors.current.onControlsSecondary
                                    )
                                }
                            }
                        } else {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize(),
                                painter = rememberAsyncImagePainter(viewState.value.latestPhotoUri),
                                contentDescription = "Captured image"
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .weight(0.1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    OutlinedButton(
                        border = BorderStroke(1.dp, LocalColors.current.onControlsPrimary),
                        contentPadding = PaddingValues(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = LocalColors.current.onControlsPrimary,
                            backgroundColor = LocalColors.current.background
                        ),
                        onClick = {
                            listener.onStartCustomCamera()
                        }) {

                        Icon(
                            painter = painterResource(id = R.drawable.ic_camera),
                            contentDescription = null,
                            //tint = LocalColors.current.divider
                        )
                        Text(
                            text = stringResource(id = R.string.main_screen_action_camera_custom),
                            color = LocalColors.current.onControlsPrimary
                        )
                    }
                }

            }
        }
    )
}

internal val mainActivityViewStatePreview = MainActivityViewState(null)

private val mainActivityListenerPreview = object : MainActivityListener {
    override fun onStartCustomCamera() {
        /*Do nothing in Preview*/
    }
}

@Preview(
    device = Devices.PIXEL,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Light Mode"
)

@Preview(
    device = Devices.PIXEL,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
private fun EnterPassContentPreview() {
    KotoxBasicTheme {
        MainScreen(
            state = remember {
                mutableStateOf(
                    mainActivityViewStatePreview
                )
            },
            listener = mainActivityListenerPreview,
        )
    }
}
