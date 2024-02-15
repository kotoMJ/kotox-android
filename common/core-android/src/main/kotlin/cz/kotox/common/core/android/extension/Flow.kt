package cz.kotox.common.core.android.extension

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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

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

//@Composable
//@SuppressLint("FlowOperatorInvokedInComposition")
//fun <T : R, R> Flow<T>.collectAsStateWithLifecycle(
//    initial: R,
//    context: CoroutineContext = EmptyCoroutineContext,
//    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
//): State<R> = rememberFlow(minActiveState = minActiveState)
//    .collectAsState(initial = initial, context = context)
//
//@Composable
//@SuppressLint("StateFlowValueCalledInComposition", "FlowOperatorInvokedInComposition")
//fun <T> StateFlow<T>.collectAsStateWithLifecycle(
//    context: CoroutineContext = EmptyCoroutineContext,
//    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
//): State<T> = rememberFlow(minActiveState = minActiveState)
//    .collectAsState(initial = value, context = context)


/**
 * Use WhileSubscribed(5000) to keep the upstream flow active for 5 seconds more after
 * the disappearance of the last collector.
 * That avoids restarting the upstream flow in certain situations such as configuration changes.
 * This is especially helpful when upstream flows are expensive to create and when these
 * operators are used in ViewModels.
 *
 * Based on https://medium.com/androiddevelopers/things-to-know-about-flows-sharein-and-statein-operators-20e6ccb2bc74
 */
fun <T> Flow<T>.stateInForScope(
    scope: CoroutineScope,
    initialValue: T
) = onEach { state ->
    Timber.v("State: $state")
}.stateIn(
    scope = scope,
    started = SharingStarted.WhileSubscribed(5_000),
    initialValue = initialValue
)