package cz.kotox.dsp.ui.analyzer.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.Navigation
import cz.kotox.core.arch.BaseFragmentViewModel
import cz.kotox.core.utility.FragmentPermissionManager
import cz.kotox.core.utility.lazyUnsafe
import cz.kotox.dsp.R
import cz.kotox.dsp.databinding.AnalyzerRecordFragmentBinding
import cz.kotox.dsp.ui.analyzer.BaseAnalyzerFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

class AnalyzerRecordFragment : BaseAnalyzerFragment<AnalyzerRecordViewModel, AnalyzerRecordFragmentBinding>() {

	val permissionManager: FragmentPermissionManager by lazyUnsafe {
		FragmentPermissionManager(this)
	}

	override fun inflateBindingLayout(inflater: LayoutInflater) = AnalyzerRecordFragmentBinding.inflate(inflater)

	override fun setupWizardViewModel() = findViewModel<AnalyzerRecordViewModel>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		lifecycle.addObserver(viewModel)
		verifyPermissions()
	}

	override fun onResume() {
		super.onResume()
		verifyPermissions()
	}

	private fun verifyPermissions() {
		if (!permissionManager.checkRecordAudioPermission()) {
			permissionManager.requestRecordAudioPermissions({}, {
				showToast("RecordAudio permissions is required for audio analysis")
				activity?.finish()
			})
		}
	}

	@ExperimentalCoroutinesApi
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.navigateProcessingBt.setOnClickListener {
			Navigation.findNavController(view).navigate(R.id.navigate_to_processing)
		}

		binding.changePitchAlgorithm.setOnClickListener {
			viewModel.changePitchAlgorithm()
		}

		binding.useProbability.setOnCheckedChangeListener { _, isChecked ->
			viewModel.changeProbabilityUsage(isChecked)
		}
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
		permissionManager.onPermissionResult(requestCode, permissions, grantResults)
	}

	private fun checkAudioPermissions() {

	}
}
