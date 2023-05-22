package cz.kotox.starter.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import cz.kotox.android.starter.R
import cz.kotox.core.ui.theme.KotoxBasicTheme
import cz.kotox.core.ui.theme.LocalColors

sealed class MainScreenEvent {
    object ClickMe : MainScreenEvent()
}

data class MainScreenInput(
    val latestPhotoUri: Uri?
)


//Night mode @Preview does not work for scaffold correctly due to current solution in ThemeUtils
@OptIn(ExperimentalFoundationApi::class)
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
@Composable
fun MainScreen(
    //@PreviewParameter(MainScreenPreviewProvider::class) input: MainScreenInput,
    onEventHandler: (MainScreenEvent) -> Unit = {}
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
                                .defaultMinSize(minHeight = 72.dp)
                                .clickable {
                                    onEventHandler.invoke(MainScreenEvent.ClickMe)
                                },
                            shape = RoundedCornerShape(16.dp),

                            elevation = 2.dp,
                            backgroundColor = LocalColors.current.background,
                        ) {

                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier.padding(32.dp),
                                    painter = painterResource(id = R.drawable.ic_click),
                                    contentDescription = null,
                                    //tint = LocalColors.current.divider
                                )
                                Text(
                                    text = stringResource(id = R.string.main_screen_action_click_me),
                                    textAlign = TextAlign.Center,

                                    color = LocalColors.current.onControlsPrimary
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}

class MainScreenPreviewProvider : PreviewParameterProvider<MainScreenInput> {
    override val values: Sequence<MainScreenInput> = sequenceOf(
        MainScreenInput(null)
    )
}