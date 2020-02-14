package cz.kotox.template.ui.wizard.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LifecycleObserver
import be.tarsos.dsp.AudioGenerator
import cz.kotox.core.arch.ShowToastEvent
import cz.kotox.core.arch.liveevent.Event
import cz.kotox.core.arch.observeEvent
import cz.kotox.core.dsp.DspPlayerProvider
import cz.kotox.core.dsp.DspPlayerResult
import cz.kotox.template.databinding.WizardSecondPlayerFragmentBinding
import cz.kotox.template.ui.wizard.BaseWizardFragment
import cz.kotox.template.ui.wizard.BaseWizardViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class WizardSecondPlayerFragment : BaseWizardFragment<WizardSecondPlayerViewModel, WizardSecondPlayerFragmentBinding>() {

	override fun inflateBindingLayout(inflater: LayoutInflater) = WizardSecondPlayerFragmentBinding.inflate(inflater)

	override fun setupWizardViewModel() = findViewModel<WizardSecondPlayerViewModel>()

	@ExperimentalCoroutinesApi
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.playBt.setOnClickListener {
			startPlayer()
		}

		binding.stopBt.isEnabled = false
		binding.stopBt.setOnClickListener {
			stopPlayer()
		}

		observeEvent<StopPlayerEvent> { stopPlayer() }
	}

	@ExperimentalCoroutinesApi
	private fun startPlayer() {
		binding.playBt.isEnabled = false
		viewModel.play()
		binding.stopBt.isEnabled = true
	}

	private fun stopPlayer() {
		binding.stopBt.isEnabled = false
		viewModel.stop()
		binding.playBt.isEnabled = true
	}

	override fun onPause() {
		stopPlayer()
		super.onPause()
	}

	companion object {
		fun newInstance() = WizardSecondPlayerFragment().apply {
			val bundle = Bundle()
			arguments = bundle
		}
	}
}

class WizardSecondPlayerViewModel @Inject constructor(private val dspPlayer: DspPlayerProvider) : BaseWizardViewModel(), LifecycleObserver {
	private var audioGenerator: AudioGenerator? = null

	@ExperimentalCoroutinesApi
	fun play() {
		launch() {
			dspPlayer.playFrequency().flowOn(Dispatchers.IO).collect {
				run {
					when (it) {
						is DspPlayerResult.Error -> {
							Timber.e(it.exception, ">>> PLAYER PLAY ERROR:")
							launch(Dispatchers.Main) {
								sendEvent(StopPlayerEvent)
								sendEvent(ShowToastEvent(it.exception.message
									?: "Unexpected issue!"))
							}
						}
						is DspPlayerResult.Success -> {
							audioGenerator = it.audioGenerator
						}
					}
					Timber.d(">>> PLAYER PLAY generator[${audioGenerator}]")
				}

			}
		}
	}

	fun stop() {
		Timber.d(">>> PLAYER STOP generator[${audioGenerator}]")
		try {
			audioGenerator?.stop()
			dspPlayer.finished()
		} catch (ise: IllegalStateException) {
			Timber.w(ise, "Non fatal illegal state when stopping generator")
		}
	}
}

object StopPlayerEvent : Event()