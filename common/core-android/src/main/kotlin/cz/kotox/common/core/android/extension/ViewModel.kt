package cz.kotox.common.core.android.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.kotox.common.core.android.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import cz.kotox.common.core.android.snackbar.SnackbarMessageHandler
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun ViewModel.launchCatching(
    snackBar: Boolean = true,
    //logService: LogService,
    block: suspend CoroutineScope.() -> Unit
) =
    viewModelScope.launch(
        CoroutineExceptionHandler { _, throwable ->
            if (snackBar) {
                SnackbarMessageHandler.showMessage(throwable.toSnackbarMessage())
            }
            //logService.logNonFatalCrash(throwable)
        },
        block = block
    )