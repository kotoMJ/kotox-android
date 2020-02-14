package cz.kotox.template.ui.wizard.second

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.DiffUtil
import cz.kotox.core.arch.ktools.DataBoundAdapter
import cz.kotox.core.dsp.model.VoiceSample
import cz.kotox.template.BR
import cz.kotox.template.R
import cz.kotox.template.databinding.AnalyzerResultListFragmentBinding
import cz.kotox.template.ui.wizard.BaseWizardFragment
import cz.kotox.template.ui.wizard.BaseWizardViewModel
import javax.inject.Inject

interface AnalyzerResultDetailView {
	val resultListAdapter: DataBoundAdapter<VoiceSample>
}

class AnalyzerResultListFragment : BaseWizardFragment<AnalyzerResultListViewModel, AnalyzerResultListFragmentBinding>(), AnalyzerResultDetailView {

	override val resultListAdapter: DataBoundAdapter<VoiceSample> = DataBoundAdapter(this, R.layout.item_audio_sample, BR.item, object : DiffUtil.ItemCallback<VoiceSample>() {
		override fun areItemsTheSame(oldItem: VoiceSample, newItem: VoiceSample): Boolean = oldItem.isItemTheSame(newItem)
		override fun areContentsTheSame(oldItem: VoiceSample, newItem: VoiceSample): Boolean = oldItem == newItem
	})

	override fun inflateBindingLayout(inflater: LayoutInflater) = AnalyzerResultListFragmentBinding.inflate(inflater)
	//
	override fun setupWizardViewModel() = findViewModel<AnalyzerResultListViewModel>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		lifecycle.addObserver(viewModel)
	}

	companion object {
		fun newInstance() = AnalyzerResultListFragment().apply {
			val bundle = Bundle()
			arguments = bundle
		}
	}

}

class AnalyzerResultListViewModel @Inject constructor() : BaseWizardViewModel(), LifecycleObserver {

}
