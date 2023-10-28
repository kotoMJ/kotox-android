package cz.kotox.common.i18n.data.impl.di

import com.google.i18n.phonenumbers.PhoneNumberUtil
import cz.kotox.common.i18n.data.api.repository.CountryRepository
import cz.kotox.common.i18n.data.impl.repository.CountryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class CountryBinderModule {
    @Binds
    abstract fun bindRepository(repository: CountryRepositoryImpl): CountryRepository
}

@InstallIn(SingletonComponent::class)
@Module()
object CountryProviderModule {
    @Singleton
    @Provides
    fun providePhoneNumberUtil(): PhoneNumberUtil = PhoneNumberUtil.getInstance()
}
