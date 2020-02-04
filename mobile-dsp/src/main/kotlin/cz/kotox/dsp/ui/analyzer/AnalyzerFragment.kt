package cz.kotox.dsp.ui.analyzer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.Navigation
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

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.navigateProcessingBt.setOnClickListener {
			Navigation.findNavController(view).navigate(R.id.navigate_to_processing)
		}
	}
}
