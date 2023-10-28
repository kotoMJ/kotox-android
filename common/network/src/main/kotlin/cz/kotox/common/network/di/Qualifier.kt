package cz.kotox.common.network.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class CommonOkHttpClient

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class CommonRetrofit
