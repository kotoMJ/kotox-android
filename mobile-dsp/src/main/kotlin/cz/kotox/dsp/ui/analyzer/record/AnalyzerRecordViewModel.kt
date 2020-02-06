package cz.kotox.dsp.ui.analyzer.record

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import be.tarsos.dsp.AudioDispatcher
import be.tarsos.dsp.AudioEvent
import be.tarsos.dsp.EnvelopeFollower
import be.tarsos.dsp.io.android.AudioDispatcherFactory
import be.tarsos.dsp.pitch.PitchDetectionHandler
import be.tarsos.dsp.pitch.PitchDetectionResult
import be.tarsos.dsp.pitch.PitchProcessor
import be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm
import cz.kotox.core.arch.extension.mutableLiveDataOf
import cz.kotox.core.entity.AppVersion
import cz.kotox.dsp.ui.analyzer.BaseAnalyzerViewModel
import cz.kotox.dsp.ui.analyzer.VoiceAnalysisSettings
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

	val min: MutableLiveData<String> = mutableLiveDataOf("0")
	val max: MutableLiveData<String> = mutableLiveDataOf("0")
	val size: MutableLiveData<String> = mutableLiveDataOf("0")

	val pitchAlgorithm: MutableLiveData<PitchEstimationAlgorithm> = mutableLiveDataOf(PitchEstimationAlgorithm.FFT_YIN)

	private var audioDispatcher: AudioDispatcher
	private var currentAudioProcessor: PitchProcessor? = null

	var recordingJob = Job()

	private var voiceAnalysisSettings: VoiceAnalysisSettings = VoiceAnalysisSettings.DEFAULT
	private val sampleRate = voiceAnalysisSettings.sampleRate//22050 //sample rate must be supported by the capture device. Nonstandard sample rates can be problematic!
	private val audioBufferSize = voiceAnalysisSettings.bufferSize //size of the buffer defines how much samples are processed in one step.
	private val bufferOverlap = 0// How much consecutive buffers overlap (in samples). Half of the AudioBufferSize is common.
	private val pitchProbabilityThreshold = 0.8f
	private val envelopeFollower = EnvelopeFollower(sampleRate.toDouble(), voiceAnalysisSettings.envelopeFollowAttackTime, voiceAnalysisSettings.envelopeFollowReleaseTime);//attack, release

	var useProbability: MutableLiveData<Boolean> = mutableLiveDataOf(true)

	init {
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

	@ExperimentalCoroutinesApi
	@OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
	private fun testLifeCycleOnResume() {
		recordingJob = Job()
		launch(recordingJob) {
			pitchAlgorithm.value?.let { initRecording(requireNotNull(useProbability.value), pitchProbabilityThreshold, it) }
		}
	}

	@OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
	private fun testLifeCycleOnPause() {
		stopCurrentDispatcher()
		recordingJob.cancel()
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
	private suspend fun initRecording(useProbability: Boolean, probabilityThreshold: Float, pitchAlgorithm: PitchEstimationAlgorithm) {
		Timber.w(">>> init recording")
		stopCurrentDispatcher()
		runAudioDispatcher(useProbability, probabilityThreshold, pitchAlgorithm)
			//.onStart { delay(5000) } //just the test whether recording start when collect is invoked.
			.flowOn(Dispatchers.IO)
			.collect { pitchInHz ->
				Timber.i(">>> FLOW pitch[$pitchInHz], min[${mainViewModel.pitchList.min()}],max[${mainViewModel.pitchList.max()}]")
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

	private fun stopCurrentDispatcher() {
		currentAudioProcessor?.let { audioDispatcher.removeAudioProcessor(it) }
		if (!audioDispatcher.isStopped) {
			audioDispatcher.stop()
		}
	}

	@ExperimentalCoroutinesApi
	private fun runAudioDispatcher(
		useProbability: Boolean,
		probabilityThreshold: Float,
		pitchAlgorithm: PitchEstimationAlgorithm
	) = callbackFlow<Float> {
		val pitchHandler = PitchDetectionHandler { pitchDetectionResult, audioEvent ->
			val pitchInHz = pitchDetectionResult.pitch
			Timber.i(">>> HANDLER pitch[$pitchInHz]Hz, probability[${pitchDetectionResult.probability}] RMS[${audioEvent.rms}] EVENT time[${audioEvent.timeStamp}], ALGORITHM[$pitchAlgorithm]")
			if (useProbability) {
				if (pitchDetectionResult.probability > probabilityThreshold) {

					val frequency = computeFrequency(pitchDetectionResult, mainViewModel.pitchList)
					val amplitude = computeAmplitude(audioEvent)

					Timber.i(">>> HANDLER2 pitch[$pitchInHz]Hz, RMS[${audioEvent.rms}], dbSPL[${audioEvent.getdBSPL()}]dB")
					Timber.i(">>> HANDLER2b pitch[$pitchInHz]Hz, freq[${frequency}], amplitude[${amplitude}]")
					Timber.i(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
					sendBlocking(pitchInHz)
				}
			} else {
				sendBlocking(pitchInHz)
			}
		}

		stopCurrentDispatcher()
		audioDispatcher = AudioDispatcherFactory.fromDefaultMicrophone(sampleRate, audioBufferSize, bufferOverlap)
		currentAudioProcessor = PitchProcessor(
			pitchAlgorithm,
			sampleRate.toFloat(),
			audioBufferSize,
			pitchHandler
		)
		audioDispatcher.addAudioProcessor(currentAudioProcessor)
		audioDispatcher.run()

	}

	fun computeFrequency(pitchDetectionResult: PitchDetectionResult, previousPitchList: List<Float>): Float? {
		var frequency: Float? = pitchDetectionResult.pitch
		if (frequency == -1.0f || frequency == null) {
			frequency = previousPitchList.lastOrNull()
		} else {
			if (previousPitchList.isNotEmpty()) { // median filter
				// use the median as frequency
				frequency = med(previousPitchList.plus(frequency))
			}
		}
		return frequency
	}

	private fun computeEnvelope(audioBuffer: FloatArray): FloatArray {
		var envelope: FloatArray = floatArrayOf()
		if (voiceAnalysisSettings.envelopeFollow) {
			envelope = audioBuffer.clone()
			envelopeFollower.calculateEnvelope(envelope)
		}
		return envelope
	}

	fun computeAmplitude(audioEvent: AudioEvent): Float? {
		val env: FloatArray = computeEnvelope(audioEvent.floatBuffer)
		return env.lastOrNull()//env[env.size - 1]
	}

	fun med(list: List<Float>) = list.sorted().let {
		(it[it.size / 2] + it[(it.size - 1) / 2]) / 2
	}

}

