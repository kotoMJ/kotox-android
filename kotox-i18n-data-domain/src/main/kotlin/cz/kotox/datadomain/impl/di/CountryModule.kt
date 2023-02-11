package cz.kotox.datadomain.impl.di

import com.google.i18n.phonenumbers.PhoneNumberUtil
import cz.kotox.datadomain.impl.CountryRepository
import cz.kotox.datadomain.impl.CountryRepositoryImpl
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
