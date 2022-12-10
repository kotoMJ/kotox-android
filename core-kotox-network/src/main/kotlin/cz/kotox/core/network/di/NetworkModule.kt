package cz.kotox.core.network.di

import cz.kotox.android.core.config.AppProperties
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    internal fun provideLoggingInterceptor(
        applicationProperties: AppProperties
    ): HttpLoggingInterceptor = HttpLoggingInterceptor { message ->
        Timber.tag("OkHttp").d(message)
    }.apply {
        level = when (applicationProperties.isDevEnvironment) {
            false -> HttpLoggingInterceptor.Level.BASIC
            true -> HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    @CommonOkHttpClient
    internal fun provideCommonOkHttpClient(
        applicationProperties: AppProperties,
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(applicationProperties.networkRequestTimeoutSec, TimeUnit.SECONDS)
        readTimeout(applicationProperties.networkRequestTimeoutSec, TimeUnit.SECONDS)
        writeTimeout(applicationProperties.networkRequestTimeoutSec, TimeUnit.SECONDS)

        retryOnConnectionFailure(false)

        addInterceptor(httpLoggingInterceptor)
    }.build()


    @Provides
    @Singleton
    internal fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    @CommonRetrofit
    internal fun provideCommonRetrofit(
        applicationProperties: AppProperties,
        @CommonOkHttpClient okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi)/*.asLenient()*/)
        .baseUrl(applicationProperties.baseUrl)
        .client(okHttpClient)
        .build()

}
