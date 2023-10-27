package cz.kotox.strings.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import cz.kotox.android.strings.R
import cz.kotox.core.ui.theme.KotoxBasicTheme
import cz.kotox.strings.ui.components.CustomResourceCard
import cz.kotox.strings.ui.components.DuplicatedResourceCard
import cz.kotox.strings.ui.components.MarkdownResourceCard
import cz.kotox.strings.ui.components.SplitResourceCard

sealed class MainScreenEvent {
    object ClickMe : MainScreenEvent()
}

data class MainScreenInput(
    val latestPhotoUri: Uri?
)


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
@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    KotoxBasicTheme {

        Scaffold(
            backgroundColor = MaterialTheme.colors.surface,
            modifier = Modifier.systemBarsPadding(),
            topBar = {
                Column {
                    MainScreenAppBarView(
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
                        SplitResourceCard()
                    }
                    item {
                        DuplicatedResourceCard()
                    }
                    item {
                        CustomResourceCard()
                    }
                    item {
                        MarkdownResourceCard()
                    }
                }

            }
        )
    }
}

