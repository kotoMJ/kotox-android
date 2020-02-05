package cz.kotox.dsp.ui.analyzer.record

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

class AnalyzerRecordViewModel @Inject constructor(appVersion: AppVersion) : BaseAnalyzerViewModel(), LifecycleObserver {

//	@Inject
//	lateinit var appVersion: AppVersion

	@Inject
	lateinit var preferencesCore: PreferencesCore

	val token: MutableLiveData<String> = MutableLiveData()

	val appVersionString = "${appVersion.versionName} (${appVersion.versionCode})"

//	val appVersionString = preferencesCore.sampleToken

	val min: MutableLiveData<String> = MutableLiveData("0")
	val max: MutableLiveData<String> = MutableLiveData("0")
	val size: MutableLiveData<String> = MutableLiveData("0")

	private var audioDispatcher: AudioDispatcher

	private val sampleRate = 22050 //sample rate must be supported by the capture device. Nonstandard sample rates can be problematic!
	private val audioBufferSize = 1024 //size of the buffer defines how much samples are processed in one step.
	private val bufferOverlap = 0// How much consecutive buffers overlap (in samples). Half of the AudioBufferSize is common.

	private val minTreshlodPitchInHz = 20

	init {
		Timber.e(">>> new viewmodel")
		token.value = "testicek"
		audioDispatcher = AudioDispatcherFactory.fromDefaultMicrophone(sampleRate, audioBufferSize, bufferOverlap)
	}

	@ExperimentalCoroutinesApi
	@OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
	private fun testLifeCycleOnResume() {
		launch {
			runRecording().flowOn(Dispatchers.IO)
				.collect { pitchInHz ->
					Timber.i(">>> PITCH[$pitchInHz], min[${mainViewModel.pitchList.min()}],max[${mainViewModel.pitchList.max()}]")
					if (pitchInHz > minTreshlodPitchInHz && pitchInHz < mainViewModel.pitchList.min() ?: pitchInHz) {
						min.value = pitchInHz.toString()
					}
					if (pitchInHz > mainViewModel.pitchList.max() ?: pitchInHz) {
						max.value = pitchInHz.toString()
					}
					if (pitchInHz > minTreshlodPitchInHz) {
						mainViewModel.pitchList.add(pitchInHz)
						size.value = mainViewModel.pitchList.size.toString()
					}
				}
		}

	}

	@OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
	private fun testLifeCycleOnPause() {
		if (!audioDispatcher.isStopped) {
			audioDispatcher.stop()
		}
	}

	@ExperimentalCoroutinesApi
	private fun runRecording() = callbackFlow<Float> {
		val pitchHandler = PitchDetectionHandler { pitchDetectionResult, audioEvent ->
			val pitchInHz = pitchDetectionResult.pitch
			Timber.i(">>> IN $pitchInHz")
			sendBlocking(pitchInHz)
		}

		audioDispatcher = AudioDispatcherFactory.fromDefaultMicrophone(sampleRate, audioBufferSize, bufferOverlap)
		audioDispatcher.addAudioProcessor(
			PitchProcessor(
				PitchEstimationAlgorithm.FFT_YIN,
				sampleRate.toFloat(),
				audioBufferSize,
				pitchHandler
			))

		audioDispatcher.run()

	}
}

