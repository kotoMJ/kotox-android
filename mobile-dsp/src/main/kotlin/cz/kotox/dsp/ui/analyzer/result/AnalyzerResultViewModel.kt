package cz.kotox.dsp.ui.analyzer.result

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import be.tarsos.dsp.AudioDispatcher
import be.tarsos.dsp.io.android.AudioDispatcherFactory
import be.tarsos.dsp.pitch.PitchDetectionHandler
import be.tarsos.dsp.pitch.PitchProcessor
import be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm
import cz.kotox.core.PreferencesCore
import cz.kotox.core.arch.BaseViewModel
import cz.kotox.core.entity.AppVersion
import cz.kotox.dsp.ui.analyzer.BaseAnalyzerFragment
import cz.kotox.dsp.ui.analyzer.BaseAnalyzerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class AnalyzerResultViewModel @Inject constructor(appVersion: AppVersion) : BaseAnalyzerViewModel(), LifecycleObserver {

//	@Inject
//	lateinit var appVersion: AppVersion

	@Inject
	lateinit var preferencesCore: PreferencesCore

	val token: MutableLiveData<String> = MutableLiveData()

	val appVersionString = "${appVersion.versionName} (${appVersion.versionCode})"

//	val appVersionString = preferencesCore.sampleToken

	init {
		Timber.e(">>> new process viewmodel")
	}

}