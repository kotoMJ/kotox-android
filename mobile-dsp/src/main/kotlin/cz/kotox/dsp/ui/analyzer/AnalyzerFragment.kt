package cz.kotox.dsp.ui.analyzer

import android.os.Bundle
import android.view.LayoutInflater
import cz.kotox.core.arch.BaseFragmentViewModel
import cz.kotox.dsp.databinding.AnalyzerFragmentBinding

class AnalyzerFragment : BaseFragmentViewModel<AnalyzerViewModel, AnalyzerFragmentBinding>() {

	override fun inflateBindingLayout(inflater: LayoutInflater) = AnalyzerFragmentBinding.inflate(inflater)

	override fun setupViewModel() = findViewModel<AnalyzerViewModel>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		lifecycle.addObserver(viewModel)
	}

}
