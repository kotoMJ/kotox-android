package cz.kotox.core.android.extension

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect


@Composable
private fun <T> Flow<T>.rememberFlow(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
): Flow<T> = remember(
    key1 = this,
    key2 = lifecycleOwner
) { flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState) }

@Composable
@SuppressLint("FlowOperatorInvokedInComposition", "ComposableNaming")
fun <T : R, R> Flow<T>.collectWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    block: suspend CoroutineScope.(T) -> Unit,
) {
    val rememberedFlow = this.rememberFlow(
        lifecycleOwner = lifecycleOwner,
        minActiveState = minActiveState
    )

    LaunchedEffect(key1 = rememberedFlow, block = {
        rememberedFlow.collect {
            block(it)
        }
    })
}

@Composable
@SuppressLint("FlowOperatorInvokedInComposition")
fun <T : R, R> Flow<T>.collectAsStateWithLifecycle(
    initial: R,
    context: CoroutineContext = EmptyCoroutineContext,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
): State<R> = rememberFlow(minActiveState = minActiveState)
    .collectAsState(initial = initial, context = context)

@Composable
@SuppressLint("StateFlowValueCalledInComposition", "FlowOperatorInvokedInComposition")
fun <T> StateFlow<T>.collectAsStateWithLifecycle(
    context: CoroutineContext = EmptyCoroutineContext,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
): State<T> = rememberFlow(minActiveState = minActiveState)
    .collectAsState(initial = value, context = context)
