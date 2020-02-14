package cz.kotox.template.di

import androidx.lifecycle.ViewModel
import cz.kotox.core.di.ViewModelKey
import cz.kotox.template.ui.MainActivity
import cz.kotox.template.ui.MainFragment
import cz.kotox.template.ui.MainViewModel
import cz.kotox.template.ui.analyzer.AnalyzerActivity
import cz.kotox.template.ui.analyzer.AnalyzerViewModel
import cz.kotox.template.ui.analyzer.record.AnalyzerRecordFragment
import cz.kotox.template.ui.analyzer.record.AnalyzerRecordViewModel
import cz.kotox.template.ui.analyzer.result.AnalyzerResultFragment
import cz.kotox.template.ui.analyzer.result.AnalyzerResultListFragment
import cz.kotox.template.ui.analyzer.result.AnalyzerResultListViewModel
import cz.kotox.template.ui.analyzer.result.AnalyzerResultPlayerFragment
import cz.kotox.template.ui.analyzer.result.AnalyzerResultPlayerViewModel
import cz.kotox.template.ui.analyzer.result.AnalyzerResultViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class MobileTemplateDaggerModule {

	@ContributesAndroidInjector()
	abstract fun contributeMainActivity(): MainActivity

	@ContributesAndroidInjector
	abstract fun contributeMainFragment(): MainFragment

	@Binds
	@IntoMap
	@ViewModelKey(MainViewModel::class)
	abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

	@ContributesAndroidInjector()
	abstract fun contributeAnalyzerActivity(): AnalyzerActivity

	@Binds
	@IntoMap
	@ViewModelKey(AnalyzerViewModel::class)
	abstract fun bindAnalyzerViewModel(analyzerViewModel: AnalyzerViewModel): ViewModel

	@ContributesAndroidInjector
	abstract fun contributeAnalyzerRecordFragment(): AnalyzerRecordFragment

	@Binds
	@IntoMap
	@ViewModelKey(AnalyzerRecordViewModel::class)
	abstract fun bindAnalyzerRecordViewModel(analyzerRecordViewModel: AnalyzerRecordViewModel): ViewModel

	@ContributesAndroidInjector
	abstract fun contributeAnalyzerResultFragment(): AnalyzerResultFragment

	@Binds
	@IntoMap
	@ViewModelKey(AnalyzerResultViewModel::class)
	abstract fun bindAnalyzerResultViewModel(analyzerResultViewModel: AnalyzerResultViewModel): ViewModel

	@ContributesAndroidInjector
	abstract fun contributeAnalyzerResultListFragment(): AnalyzerResultListFragment

	@Binds
	@IntoMap
	@ViewModelKey(AnalyzerResultListViewModel::class)
	abstract fun bindAnalyzerResultListViewModel(analyzerResultViewModel: AnalyzerResultListViewModel): ViewModel

	@ContributesAndroidInjector
	abstract fun contributeAnalyzerResultPlayerFragment(): AnalyzerResultPlayerFragment

	@Binds
	@IntoMap
	@ViewModelKey(AnalyzerResultPlayerViewModel::class)
	abstract fun bindAnalyzerResultPlayerViewModel(analyzerResultViewModel: AnalyzerResultPlayerViewModel): ViewModel

}