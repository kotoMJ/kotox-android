package cz.kotox.dsp.ui.analyzer

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import cz.kotox.core.PreferencesCore
import cz.kotox.core.arch.BaseViewModel
import cz.kotox.core.entity.AppVersion
import timber.log.Timber
import javax.inject.Inject

class AnalyzerViewModel @Inject constructor(appVersion: AppVersion) : BaseViewModel(), LifecycleObserver {

//	@Inject
//	lateinit var appVersion: AppVersion

	@Inject
	lateinit var preferencesCore: PreferencesCore

	val token: MutableLiveData<String> = MutableLiveData()

	val appVersionString = "${appVersion.versionName} (${appVersion.versionCode})"

	val pitchList = mutableListOf<Float>()

//	val appVersionString = preferencesCore.sampleToken

	init {
		Timber.e(">>> new viewmodel")
		token.value = "testicek"
	}
}