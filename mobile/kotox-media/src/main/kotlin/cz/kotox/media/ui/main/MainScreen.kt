package cz.kotox.media.ui.main

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import cz.kotox.media.R
import cz.kotox.common.core.android.extension.collectAsStateWithLifecycle
import cz.kotox.common.designsystem.theme.shiraz.KotoxBasicTheme
import cz.kotox.common.designsystem.theme.shiraz.LocalColors
import cz.kotox.media.ui.MainActivityListener
import cz.kotox.media.ui.MainActivityViewState
import getBitmap
import getSignificantColors

@Composable
internal fun MainScreen(
    state: State<MainActivityViewState>,
    listener: MainActivityListener,
    viewModel: MainScreenViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    LaunchedEffect(viewModel, state.value.latestPhotoUri) {
        viewModel.updatePhotoUri(state.value.latestPhotoUri)

        val newColorList = state.value.latestPhotoUri?.getBitmap(context)?.getSignificantColors()
        viewModel.updateColors(newColorList ?: emptyList())

    }

    val viewState = viewModel.state.collectAsStateWithLifecycle()

    MainScreenScaffold(
        viewState,
        listener
    )
}

//Night mode @Preview does not work for scaffold correctly due to current solution in ThemeUtils
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
internal fun MainScreenScaffold(
    state: State<MainScreenViewState>,
    listener: MainActivityListener,
) {

    val isPortrait = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT

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
            Crossfade(targetState = isPortrait) { isPortrait ->
                when (isPortrait) {
                    true -> PortraitMainScreen(state, listener)
                    else -> LandscapeMainScreen(state, listener)
                }
            }
        }
    )
}

@Composable
private fun PortraitMainScreen(
    state: State<MainScreenViewState>,
    listener: MainActivityListener
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.9f)
        ) {
            CapturedImageView(state)
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .weight(0.2f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column() {

                SignificantColorsView(
                    colors = state.value.significantPhotoUriRgbColors,
                    isPortrait = true
                )
                Spacer(modifier = Modifier.size(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    StartCustomCameraButton(
                        onClick = listener::onStartCustomCameraAdaptiveLayout,
                        buttonText = stringResource(id = R.string.main_screen_action_camera_custom_layout_adaptive)
                    )
                    StartCustomCameraButton(
                        onClick = listener::onStartCustomCameraMultiLayout,
                        buttonText = stringResource(id = R.string.main_screen_action_camera_custom_layout_multi)
                    )
                }

            }
        }

    }
}

@Composable
private fun LandscapeMainScreen(
    state: State<MainScreenViewState>,
    listener: MainActivityListener
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.7f)
        ) {
            CapturedImageView(state)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .weight(0.3f),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.5f),
                horizontalArrangement = Arrangement.End
            ) {
                SignificantColorsView(
                    colors = state.value.significantPhotoUriRgbColors,
                    isPortrait = false
                )
            }
            Row(
                modifier = Modifier.fillMaxSize().weight(0.5f),
                horizontalArrangement = Arrangement.End,
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                ) {

                    StartCustomCameraButton(
                        onClick = listener::onStartCustomCameraAdaptiveLayout,
                        buttonText = stringResource(id = R.string.main_screen_action_camera_custom_layout_adaptive)
                    )
                    StartCustomCameraButton(
                        onClick = listener::onStartCustomCameraMultiLayout,
                        buttonText = stringResource(id = R.string.main_screen_action_camera_custom_layout_multi)
                    )
                }
            }
        }
    }
}

@Composable
private fun StartCustomCameraButton(
    buttonText: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        border = BorderStroke(1.dp, LocalColors.current.onControlsPrimary),
        contentPadding = PaddingValues(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = LocalColors.current.onControlsPrimary,
            backgroundColor = LocalColors.current.background
        ),
        onClick = onClick
    ) {

        Icon(
            painter = painterResource(id = R.drawable.ic_camera),
            contentDescription = null,
            //tint = LocalColors.current.divider
        )
        Text(
            text = buttonText,
            color = LocalColors.current.onControlsPrimary
        )
    }
}

@Composable
private fun CapturedImageView(state: State<MainScreenViewState>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (state.value.latestPhotoUri == null) {
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
                painter = rememberAsyncImagePainter(state.value.latestPhotoUri),
                contentDescription = "Captured image"
            )
        }
    }
}

@Composable
fun SignificantColorsView(colors: List<Int>, isPortrait: Boolean) {
    AnimatedVisibility(visible = colors.isNotEmpty()) {
        Crossfade(targetState = isPortrait) { isPortrait ->
            when (isPortrait) {
                true -> {
                    Row(
                    ) {
                        Text(
                            text = stringResource(id = R.string.main_screen_significant_colors_title),
                            color = LocalColors.current.onControlsSecondary
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        colors.forEach { rgbColorInt ->
                            Box(

                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(Color(rgbColorInt))
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                        }

                    }
                }

                false -> {
                    Column(
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                            text = stringResource(id = R.string.main_screen_significant_colors_title),
                            color = LocalColors.current.onControlsSecondary
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            colors.forEachIndexed { index, rgbColorInt ->
                                Box(
                                    modifier = Modifier
                                        .padding(top = 8.dp)
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(Color(rgbColorInt))
                                )

                                if (index != colors.lastIndex) {
                                    Spacer(modifier = Modifier.size(8.dp))
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}

internal val mainActivityViewStatePreview = MainScreenViewState(
    latestPhotoUri = null,
    significantPhotoUriRgbColors = listOf(
        Color.Red.toArgb(),
        Color.Green.toArgb(),
        Color.Blue.toArgb(),
        Color.Yellow.toArgb(),
        Color.Magenta.toArgb(),
        Color.Cyan.toArgb(),
    )
)

private val mainActivityListenerPreview = object : MainActivityListener {

    override fun onStartCustomCameraAdaptiveLayout() {
        /*Do nothing in Preview*/
    }

    override fun onStartCustomCameraMultiLayout() {
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
@Preview(
    showSystemUi = false,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
@Composable
private fun EnterPassContentPreview() {
    KotoxBasicTheme {
        MainScreenScaffold(
            state = remember {
                mutableStateOf(
                    mainActivityViewStatePreview
                )
            },
            listener = mainActivityListenerPreview,
        )
    }
}
