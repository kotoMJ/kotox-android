package cz.kotox.dsp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.ActivityNavigator
import androidx.navigation.Navigation
import cz.kotox.dsp.R
import cz.kotox.core.arch.BaseFragment
import cz.kotox.core.arch.BaseFragmentViewModel
import cz.kotox.dsp.databinding.MainFragmentBinding
import cz.kotox.dsp.ui.analyzer.AnalyzerActivity

/**
 * Fragment used to show how to navigate to another destination
 */
class MainFragment : BaseFragmentViewModel<MainViewModel, MainFragmentBinding>() {

	override fun inflateBindingLayout(inflater: LayoutInflater) = MainFragmentBinding.inflate(inflater)

	override fun setupViewModel() = findViewModel<MainViewModel>()

//	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//		savedInstanceState: Bundle?): View? {
//		setHasOptionsMenu(true)
//		return inflater.inflate(R.layout.main_fragment, container, false)
//	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		lifecycle.addObserver(viewModel)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

//		view.findViewById<Button>(R.id.navigate_dest_bt)?.setOnClickListener(
//			Navigation.createNavigateOnClickListener(R.id.flow_step_one, null)
//		)

//		val options = NavOptions.Builder()
//			.setEnterAnim(R.anim.slide_in_right)
//			.setExitAnim(R.anim.slide_out_left)
//			.setPopEnterAnim(R.anim.slide_in_left)
//			.setPopExitAnim(R.anim.slide_out_right)
//			.build()

//		view.findViewById<Button>(R.id.navigate_dest_bt)?.setOnClickListener {
//			findNavController(it).navigate(R.id.text_recognition_navigation, null, options)
//		}
//		view.findViewById<Button>(R.id.navigate_action_bt)?.setOnClickListener(
//			Navigation.createNavigateOnClickListener(R.id.next_action, null)
//		)

		binding.navigateAnalyzeBt.setOnClickListener {
			//Navigation.createNavigateOnClickListener()
//			ActivityNavigator(context)
//				.createDestination()
//				.setIntent(Intent(context, AnalyzerActivity::class.java))
			Navigation.createNavigateOnClickListener(R.id.analyzer_home, null)
		}

	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		inflater.inflate(R.menu.main_menu, menu)
	}
}
