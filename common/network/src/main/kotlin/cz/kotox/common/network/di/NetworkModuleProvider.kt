package cz.kotox.common.network.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import cz.kotox.common.core.config.AppProperties
import cz.kotox.common.network.config.AppNetworkingProperties
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

class NetworkModuleProvider {

    fun provideLoggingInterceptor(
        applicationProperties: AppProperties
    ): HttpLoggingInterceptor = HttpLoggingInterceptor { message ->
        Timber.tag("OkHttp").d(message)
    }.apply {
        level = when (applicationProperties.isDevEnvironment) {
            false -> HttpLoggingInterceptor.Level.BASIC
            true -> HttpLoggingInterceptor.Level.BODY
        }
    }

    fun provideCommonOkHttpClient(
        applicationProperties: AppNetworkingProperties,
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(applicationProperties.networkRequestTimeoutSec, TimeUnit.SECONDS)
        readTimeout(applicationProperties.networkRequestTimeoutSec, TimeUnit.SECONDS)
        writeTimeout(applicationProperties.networkRequestTimeoutSec, TimeUnit.SECONDS)

        retryOnConnectionFailure(false)

        addInterceptor(httpLoggingInterceptor)
    }.build()

    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun provideCommonRetrofit(
        applicationProperties: AppNetworkingProperties,
        @CommonOkHttpClient okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi)/*.asLenient()*/)
        .baseUrl(applicationProperties.baseUrl)
        .client(okHttpClient)
        .build()
}