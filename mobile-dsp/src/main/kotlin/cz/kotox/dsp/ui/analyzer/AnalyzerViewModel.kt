package cz.kotox.dsp.ui.analyzer

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import cz.kotox.core.PreferencesCore
import cz.kotox.core.arch.BaseViewModel
import cz.kotox.core.arch.ObservableViewModel
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

//	val appVersionString = preferencesCore.sampleToken

	init {
		token.value = "testicek"
	}

//	val token : String = "ddd"

//	fun getToken(): LiveData<String> {
//		val ret = MutableLiveData<String>()
//		ret.value = "..."
//		return ret
//	}

	@OnLifecycleEvent(Lifecycle.Event.ON_START)
	fun testLifeCycleOnStart(){
		//Timber.e(">>> AnalyzerViewModel ON_START")
	}

	@OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
	fun testLifeCycleOnResume(){
		//Timber.e(">>> AnalyzerViewModel ON_RESUME")
	}

	@OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
	fun testLifeCycleOnPause(){
		//Timber.e(">>> AnalyzerViewModel ON_PAUSE")
	}
}