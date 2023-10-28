package cz.kotox.common.network.di

import cz.kotox.common.core.config.AppProperties
import com.squareup.moshi.Moshi
import cz.kotox.common.network.config.AppNetworkingProperties
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
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
        applicationProperties: AppNetworkingProperties,
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
        applicationProperties: AppNetworkingProperties,
        @CommonOkHttpClient okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit = provider.provideCommonRetrofit(applicationProperties, okHttpClient, moshi)

}
