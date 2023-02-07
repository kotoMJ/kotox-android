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

    val provider = NetworkModuleProvider()

    @Provides
    @Singleton
    internal fun provideLoggingInterceptor(
        applicationProperties: AppProperties
    ): HttpLoggingInterceptor = provider.provideLoggingInterceptor(applicationProperties)

    @Provides
    @Singleton
    @CommonOkHttpClient
    internal fun provideCommonOkHttpClient(
        applicationProperties: AppProperties,
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient =
        provider.provideCommonOkHttpClient(applicationProperties, httpLoggingInterceptor)


    @Provides
    @Singleton
    internal fun provideMoshi(): Moshi = provider.provideMoshi()

    @Provides
    @Singleton
    @CommonRetrofit
    internal fun provideCommonRetrofit(
        applicationProperties: AppProperties,
        @CommonOkHttpClient okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit = provider.provideCommonRetrofit(applicationProperties, okHttpClient, moshi)

}
