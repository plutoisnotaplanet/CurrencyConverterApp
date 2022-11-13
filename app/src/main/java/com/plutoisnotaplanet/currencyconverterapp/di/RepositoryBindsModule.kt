package com.plutoisnotaplanet.currencyconverterapp.di

import com.plutoisnotaplanet.currencyconverterapp.application.data.repositories_impl.CurrencyRepositoryImpl
import com.plutoisnotaplanet.currencyconverterapp.application.data.repositories_impl.CurrencySortSettingsRepositoryImpl
import com.plutoisnotaplanet.currencyconverterapp.application.domain.repository.CurrencyRepository
import com.plutoisnotaplanet.currencyconverterapp.application.domain.repository.CurrencySortSettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryBindsModule {

    @Binds
    @ViewModelScoped
    fun bindCurrencyRepositoryImpl_to_CurrencyRepository(
        currencyRepositoryImpl: CurrencyRepositoryImpl
    ): CurrencyRepository

    @Binds
    @ViewModelScoped
    fun bindCurrencySortSettingsRepositoryImpl_to_CurrencySortSettingsRepository(
        currencySortSettingsRepositoryImpl: CurrencySortSettingsRepositoryImpl
    ): CurrencySortSettingsRepository
}