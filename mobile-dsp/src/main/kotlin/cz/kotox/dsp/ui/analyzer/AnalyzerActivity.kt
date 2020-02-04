package cz.kotox.dsp.ui.analyzer

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import cz.kotox.core.arch.BaseActivity
import cz.kotox.core.database.PreferencesCommon
import cz.kotox.dsp.R
import javax.inject.Inject

class AnalyzerActivity : BaseActivity() {

	@Inject
	lateinit var preferencesCommon: PreferencesCommon

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.analyzer_activity)

		val host: NavHostFragment = supportFragmentManager
			.findFragmentById(R.id.analyzer_nav_host_fragment) as NavHostFragment? ?: return

	}

}