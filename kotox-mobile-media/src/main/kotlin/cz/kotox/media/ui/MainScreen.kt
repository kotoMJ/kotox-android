package cz.kotox.media.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.palette.graphics.Palette
import coil.compose.rememberAsyncImagePainter
import cz.kotox.android.media.R
import cz.kotox.core.android.extension.collectAsStateWithLifecycle
import cz.kotox.core.ui.theme.KotoxBasicTheme
import cz.kotox.core.ui.theme.LocalColors
import timber.log.Timber


@Composable
internal fun MainScreen(
    state: State<MainActivityViewState>,
    listener: MainActivityListener,
    viewModel: MainScreenViewModel = hiltViewModel()
) {


    val context = LocalContext.current

    LaunchedEffect(viewModel, state.value.latestPhotoUri) {
        viewModel.updatePhotoUri(state.value.latestPhotoUri)

        val uri = state.value.latestPhotoUri

        if (uri != null) {

            //ImageDecoder.decodeBitmap read EXIF orientation, Media.getBitmap doesn't
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(
                    ImageDecoder.createSource(
                        context.contentResolver,
                        uri
                    )
                )
                /**
                 * java.lang.IllegalStateException: unable to getPixels(), pixel access is not supported on Config#HARDWARE bitmaps
                 *
                 * By default ImageDecoder.decodeBitmap() returns immutable bitmap.
                 * And default allocation for the pixel memory is HARDWARE but may switch
                 * to software in case there is a small image or when HARDWARE is incompatible.
                 */
                { decoder, info, source ->
                    decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
                    decoder.isMutableRequired = true
                }
            } else {
                MediaStore.Images.Media.getBitmap(
                    context.contentResolver,
                    uri
                )
            }

            val rgbColors = Palette.from(bitmap).generate().let { palette ->

                Timber.d("palette.lightVibrantSwatch: ${palette.lightVibrantSwatch}")
                Timber.d("palette.vibrantSwatch: ${palette.vibrantSwatch}")
                Timber.d("palette.lightMutedSwatch: ${palette.lightMutedSwatch}")
                Timber.d("palette.mutedSwatch: ${palette.mutedSwatch}")
                Timber.d("palette.darkMutedSwatch: ${palette.darkMutedSwatch}")
                Timber.d("palette.darkVibrantSwatch: ${palette.darkVibrantSwatch}")

                //Some swatch might be null. Usually at least 3 swatches per photo are available.
                listOfNotNull(
                    palette.lightVibrantSwatch?.rgb,
                    palette.vibrantSwatch?.rgb,
                    palette.lightMutedSwatch?.rgb,
                    palette.mutedSwatch?.rgb,
                    palette.darkMutedSwatch?.rgb,
                    palette.darkVibrantSwatch?.rgb
                )
            }

            viewModel.updateColors(rgbColors)
        }
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

                    ColorView(
                        colors = state.value.significantPhotoUriRgbColors
                    )
                }

            }
        }
    )
}

@Composable
fun ColorView(colors: List<Int>) {
    Row(
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        colors.forEach { rgbColorInt ->
            Box(

                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color(rgbColorInt))
            )
        }

    }
}

internal val mainActivityViewStatePreview = MainScreenViewState(
    latestPhotoUri = null,
    significantPhotoUriRgbColors = listOf(
        Color.Red.toArgb(),
        Color.Green.toArgb(),
        Color.Blue.toArgb()
    )
)

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
