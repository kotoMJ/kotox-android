package cz.kotox.dsp.ui.analyzer.record

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import be.tarsos.dsp.AudioDispatcher
import be.tarsos.dsp.AudioEvent
import be.tarsos.dsp.io.android.AudioDispatcherFactory
import be.tarsos.dsp.pitch.PitchDetectionHandler
import be.tarsos.dsp.pitch.PitchProcessor
import be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm
import cz.kotox.core.PreferencesCore
import cz.kotox.core.arch.extension.mutableLiveDataOf
import cz.kotox.core.arch.extension.observeOnce
import cz.kotox.core.entity.AppVersion
import cz.kotox.dsp.ui.analyzer.BaseAnalyzerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

	val min: MutableLiveData<String> = mutableLiveDataOf("0")
	val max: MutableLiveData<String> = mutableLiveDataOf("0")
	val size: MutableLiveData<String> = mutableLiveDataOf("0")

	val pitchAlgorithm: MutableLiveData<PitchEstimationAlgorithm> = mutableLiveDataOf(PitchEstimationAlgorithm.FFT_YIN)

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
	fun changePitchAlgorithm() {
		val currentPitchAlgorithm = requireNotNull(pitchAlgorithm.value)
		val newIndex = if (currentPitchAlgorithm.ordinal == PitchEstimationAlgorithm.values().size - 1) {
			0
		} else {
			currentPitchAlgorithm.ordinal + 1
		}
		val newPitchAlgorithm = PitchEstimationAlgorithm.values()[newIndex]
		pitchAlgorithm.value = newPitchAlgorithm
		mainViewModel.pitchList.clear()
		min.value = "0"
		max.value = "0"
		size.value = "0"
		launch { initRecording(newPitchAlgorithm) }

	}

	@ExperimentalCoroutinesApi
	@OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
	private fun testLifeCycleOnResume() {
		launch {
			pitchAlgorithm.value?.let { initRecording(it) }
		}

	}

	@ExperimentalCoroutinesApi
	private suspend fun initRecording(pitchAlgorithm: PitchEstimationAlgorithm) {
		stopCurrentDispatcher()
		runAudioDispatcher(pitchAlgorithm)
			//.onStart { delay(5000) } //just the test whether recording start when collect is invoked.
			.flowOn(Dispatchers.IO)
			.collect { pitchInHz ->
				Timber.i(">>> PITCH[$pitchInHz], min[${mainViewModel.pitchList.min()}],max[${mainViewModel.pitchList.max()}]")
				if (pitchInHz > minTreshlodPitchInHz && pitchInHz < mainViewModel.pitchList.min() ?: pitchInHz) {
					min.value = String.format("%.1f", pitchInHz)
				}
				if (pitchInHz > mainViewModel.pitchList.max() ?: pitchInHz) {
					max.value = String.format("%.1f", pitchInHz)
				}
				if (pitchInHz > minTreshlodPitchInHz) {
					mainViewModel.pitchList.add(pitchInHz)
					size.value = mainViewModel.pitchList.size.toString()
				}
			}
	}

	@OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
	private fun testLifeCycleOnPause() {
		stopCurrentDispatcher()
	}

	private fun stopCurrentDispatcher() {
		if (!audioDispatcher.isStopped) {
			audioDispatcher.stop()
		}
	}

	@ExperimentalCoroutinesApi
	private fun runAudioDispatcher(pitchAlgorithm: PitchEstimationAlgorithm) = callbackFlow<Float> {
		val pitchHandler = PitchDetectionHandler { pitchDetectionResult, audioEvent ->
			val pitchInHz = pitchDetectionResult.pitch
			Timber.i(">>> IN [$pitchInHz]Hz, [${audioEvent.getdBSPL()}dB] EVENT time[${audioEvent.timeStamp}]")
			sendBlocking(pitchInHz)
		}

		audioDispatcher = AudioDispatcherFactory.fromDefaultMicrophone(sampleRate, audioBufferSize, bufferOverlap)
		audioDispatcher.addAudioProcessor(
			PitchProcessor(
				pitchAlgorithm,
				sampleRate.toFloat(),
				audioBufferSize,
				pitchHandler
			))
		audioDispatcher.run()

	}

	private fun processEvent(audioEvent: AudioEvent) {

	}
}

