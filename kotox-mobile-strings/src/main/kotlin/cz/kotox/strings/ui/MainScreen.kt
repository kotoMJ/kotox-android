package cz.kotox.strings.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import cz.kotox.android.strings.R
import cz.kotox.core.ui.theme.KotoxBasicTheme
import cz.kotox.core.ui.theme.LocalColors
import cz.kotox.core.ui.theme.LocalTypography

sealed class MainScreenEvent {
    object ClickMe : MainScreenEvent()
}

data class MainScreenInput(
    val latestPhotoUri: Uri?
)


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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
//Night mode @Preview does not work for scaffold correctly due to current solution in ThemeUtils
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    //@PreviewParameter(MainScreenPreviewProvider::class) input: MainScreenInput,
) {
    KotoxBasicTheme {

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

                LazyVerticalStaggeredGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = StaggeredGridCells.Fixed(1)
                ) {
                    item {

                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .defaultMinSize(minHeight = 72.dp),
                            shape = RoundedCornerShape(16.dp),

                            elevation = 2.dp,
                            backgroundColor = LocalColors.current.background,
                        ) {

                            Column {
                                Row(
                                    modifier = Modifier
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = "SIMPLE RESOURCE",
                                        style = LocalTypography.current.body1Medium,
                                        color = LocalColors.current.textNorm
                                    )
                                }

                                Row(
                                    modifier = Modifier
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(text = "Some text here and there. Maybe there will be a bit more of the text. Let's see how about alignment.")
                                }
                            }
                        }
                    }
                }

            }
        )
    }
}

//Night mode @Preview does not work for scaffold correctly due to current solution in ThemeUtils
class MainScreenPreviewProvider : PreviewParameterProvider<MainScreenInput> {
    override val values: Sequence<MainScreenInput> = sequenceOf(
        MainScreenInput(null)
    )
}

