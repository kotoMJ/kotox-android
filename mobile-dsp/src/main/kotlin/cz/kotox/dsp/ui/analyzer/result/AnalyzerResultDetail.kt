package cz.kotox.dsp.ui.analyzer.result

import android.os.Bundle
import android.view.View
import cz.kotox.core.arch.BaseFragment

class AnalyzerResultListFragment : BaseFragment() {

	companion object {
		fun newInstance() = AnalyzerResultListFragment().apply {
			val bundle = Bundle()
			arguments = bundle
		}
	}

//	override fun inflateBindingLayout(inflater: LayoutInflater) = AnalyzerResultFragmentBinding.inflate(inflater)
//
//	override fun setupWizardViewModel() = findViewModel<AnalyzerResultViewModel>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
//		lifecycle.addObserver(viewModel)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
//		binding.navigateFinishAnalyzerBt.setOnClickListener {
//			activity?.finish()
//		}
	}
}