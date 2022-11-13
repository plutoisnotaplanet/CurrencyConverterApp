package com.plutoisnotaplanet.currencyconverterapp.di

import com.plutoisnotaplanet.currencyconverterapp.application.domain.interactors.CurrencyInteractor
import com.plutoisnotaplanet.currencyconverterapp.application.domain.interactors.CurrencySortSettingsInteractor
import com.plutoisnotaplanet.currencyconverterapp.application.domain.usecases.CurrencySortSettingsUseCase
import com.plutoisnotaplanet.currencyconverterapp.application.domain.usecases.CurrencyUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface UseCasesBindsModule {

    @Binds
    @ViewModelScoped
    fun bindCurrencyInteractorToCurrencyUseCase(
        currencyInteractor: CurrencyInteractor
    ): CurrencyUseCase

    @Binds
    @ViewModelScoped
    fun bindCurrencySortSettingsInteractorToCurrencySortSettingsUseCase(
        currencySortSettingsInteractor: CurrencySortSettingsInteractor
    ): CurrencySortSettingsUseCase
}