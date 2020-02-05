package cz.kotox.dsp.ui.analyzer.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import cz.kotox.core.arch.BaseFragmentViewModel
import cz.kotox.dsp.databinding.AnalyzerResultFragmentBinding
import cz.kotox.dsp.ui.analyzer.BaseAnalyzerFragment
import cz.kotox.dsp.ui.analyzer.record.AnalyzerRecordViewModel

class AnalyzerResultFragment : BaseAnalyzerFragment<AnalyzerResultViewModel, AnalyzerResultFragmentBinding>() {

	override fun inflateBindingLayout(inflater: LayoutInflater) = AnalyzerResultFragmentBinding.inflate(inflater)

	override fun setupWizardViewModel() = findViewModel<AnalyzerResultViewModel>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		lifecycle.addObserver(viewModel)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.navigateFinishAnalyzerBt.setOnClickListener {
			activity?.finish()
		}
	}
}