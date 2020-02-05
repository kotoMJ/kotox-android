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
import cz.kotox.core.arch.extension.mutableLiveDataOf
import cz.kotox.core.entity.AppVersion
import cz.kotox.core.utility.logWithTag
import cz.kotox.dsp.ui.analyzer.BaseAnalyzerViewModel
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

	var recordingJob = Job()

//	val appVersionString = preferencesCore.sampleToken

	val min: MutableLiveData<String> = mutableLiveDataOf("0")
	val max: MutableLiveData<String> = mutableLiveDataOf("0")
	val size: MutableLiveData<String> = mutableLiveDataOf("0")

	val pitchAlgorithm: MutableLiveData<PitchEstimationAlgorithm> = mutableLiveDataOf(PitchEstimationAlgorithm.FFT_YIN)

	private var audioDispatcher: AudioDispatcher

	private val sampleRate = 22050 //sample rate must be supported by the capture device. Nonstandard sample rates can be problematic!
	private val audioBufferSize = 1024 //size of the buffer defines how much samples are processed in one step.
	private val bufferOverlap = 0// How much consecutive buffers overlap (in samples). Half of the AudioBufferSize is common.

	private val pitchProbabilityThreshold = 0.8f

	var useProbability: MutableLiveData<Boolean> = mutableLiveDataOf(true)

	init {
		Timber.e(">>> new viewmodel")
		token.value = "testicek"
		audioDispatcher = AudioDispatcherFactory.fromDefaultMicrophone(sampleRate, audioBufferSize, bufferOverlap)
	}

	override fun onCleared() {
		recordingJob.cancel()
		super.onCleared()
	}

	@ExperimentalCoroutinesApi
	fun changePitchAlgorithm() {
		resetRecording()
		val newPitchAlgorithm = getNextPitchAlgorithm()
		pitchAlgorithm.value = newPitchAlgorithm
		cleanUpMeasurement()
		launch(recordingJob) { initRecording(requireNotNull(useProbability.value), pitchProbabilityThreshold, newPitchAlgorithm) }

	}

	@ExperimentalCoroutinesApi
	fun changeProbabilityUsage(useProbability: Boolean) {
		resetRecording()
		this.useProbability.value = useProbability
		cleanUpMeasurement()
		launch(recordingJob) { initRecording(useProbability, pitchProbabilityThreshold, requireNotNull(pitchAlgorithm.value)) }
	}

	private fun resetRecording() {
		recordingJob.cancel()
		recordingJob = Job()
	}

	private fun cleanUpMeasurement() {
		mainViewModel.pitchList.clear()
		min.value = "0"
		max.value = "0"
		size.value = "0"
	}

	private fun getNextPitchAlgorithm(): PitchEstimationAlgorithm {
		val currentPitchAlgorithm = requireNotNull(pitchAlgorithm.value)
		val newIndex = if (currentPitchAlgorithm.ordinal == PitchEstimationAlgorithm.values().size - 1) {
			0
		} else {
			currentPitchAlgorithm.ordinal + 1
		}
		val newPitchAlgorithm = PitchEstimationAlgorithm.values()[newIndex]
		return newPitchAlgorithm
	}

	@ExperimentalCoroutinesApi
	@OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
	private fun testLifeCycleOnResume() {
		launch(recordingJob) {
			pitchAlgorithm.value?.let { initRecording(requireNotNull(useProbability.value), pitchProbabilityThreshold, it) }
		}

	}

	@ExperimentalCoroutinesApi
	private suspend fun initRecording(useProbability: Boolean, probabilityThreshold: Float, pitchAlgorithm: PitchEstimationAlgorithm) {
		Timber.w(">>> init recording")
		stopCurrentDispatcher()
		runAudioDispatcher(useProbability, probabilityThreshold, pitchAlgorithm)
			//.onStart { delay(5000) } //just the test whether recording start when collect is invoked.
			.flowOn(Dispatchers.IO)
			.collect { pitchInHz ->
				Timber.i(">>> PITCH[$pitchInHz], min[${mainViewModel.pitchList.min()}],max[${mainViewModel.pitchList.max()}]")
				if (pitchInHz > 0) {

					if (pitchInHz < mainViewModel.pitchList.min() ?: pitchInHz) {
						min.value = String.format("%.1f", pitchInHz)
					}
					if (pitchInHz > mainViewModel.pitchList.max() ?: pitchInHz) {
						max.value = String.format("%.1f", pitchInHz)
					}

					mainViewModel.pitchList.add(pitchInHz)
					size.value = mainViewModel.pitchList.size.toString()
				}
			}
	}

	@OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
	private fun testLifeCycleOnPause() {
		stopCurrentDispatcher()
		recordingJob.cancel()
	}

	private fun stopCurrentDispatcher() {
		if (!audioDispatcher.isStopped) {
			audioDispatcher.stop()
		}
	}

	@ExperimentalCoroutinesApi
	private fun runAudioDispatcher(useProbability: Boolean, probabilityThreshold: Float, pitchAlgorithm: PitchEstimationAlgorithm) = callbackFlow<Float> {
		val pitchHandler = PitchDetectionHandler { pitchDetectionResult, audioEvent ->
			val pitchInHz = pitchDetectionResult.pitch
			Timber.i(">>> IN [$pitchInHz]Hz, probability[${pitchDetectionResult.probability}] RMS[${audioEvent.rms}] EVENT time[${audioEvent.timeStamp}], ALGORITHM[$pitchAlgorithm]")
			if (useProbability) {
				if (pitchDetectionResult.probability > probabilityThreshold) {
					sendBlocking(pitchInHz)
				}
			} else {
				sendBlocking(pitchInHz)
			}
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

}

