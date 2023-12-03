package cz.kotox.playground.ui.scannerline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import cz.kotox.common.designsystem.theme.KotoxBasicTheme
import timber.log.Timber

class ScannerLineActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            cz.kotox.common.designsystem.theme.KotoxBasicTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var size by remember { mutableStateOf(IntSize.Zero) }

                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(40.dp)
                                .align(Alignment.Center)
                                .background(color = Color.Yellow)
                                .onSizeChanged {
                                    size = it
                                }
                        ) {
                            Timber.d(
                                ">>>_ size: ${size.component1().pxToDp()} Dp"
                            )
                            ScannerAnimatedLine(
                                modifier = Modifier,
                                squareContentBoxSize = size.component1().pxToDp().minus(104.dp),
                                horizontalOverflow = 80.dp,
                                horizontalOverflowPeak = 104.dp,
                                //showDebugFrame = true,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color = Color.LightGray),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}