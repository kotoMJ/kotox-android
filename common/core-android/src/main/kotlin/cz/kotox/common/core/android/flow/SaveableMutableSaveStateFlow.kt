package cz.kotox.common.core.android.flow

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

/**
 * A SavedStateHandle is saved when the onSaveInstanceState is called on the connected activity or fragment.
 * This means that while you can continue to update observable data holders from a SavedStateHandle
 * while in the app is in the background,
 * all state updates might be lost if the app process is killed before becoming foregrounded again.
 */
@Suppress("TooGenericExceptionCaught")
class SaveableMutableSaveStateFlow<T>(
    private val savedStateHandle: SavedStateHandle,
    private val key: String,
    defaultValue: T
) {
    val state: StateFlow<T> =
        savedStateHandle.getStateFlow(key, defaultValue)

    var value: T
        get() = state.value
        set(value) {
            try {
                savedStateHandle[key] = value
            } catch (t: Throwable) {
                Timber.e(t, "SaveableMutableSaveStateFlow failure!")
            }
        }
}
