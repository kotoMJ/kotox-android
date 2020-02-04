package cz.kotox.dsp.ui.analyzer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.kotox.core.arch.BaseFragmentViewModel
import cz.kotox.dsp.R
import cz.kotox.dsp.databinding.AnalyzerFragmentBinding

class AnalyzerFragment : BaseFragmentViewModel<AnalyzerViewModel, AnalyzerFragmentBinding>() {

	override fun inflateBindingLayout(inflater: LayoutInflater) = AnalyzerFragmentBinding.inflate(inflater)

	override fun setupViewModel() = findViewModel<AnalyzerViewModel>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		lifecycle.addObserver(viewModel)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?): View? {
		setHasOptionsMenu(true)
		return inflater.inflate(R.layout.analyzer_fragment, container, false)
	}

}
