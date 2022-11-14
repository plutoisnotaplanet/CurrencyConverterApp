package com.plutoisnotaplanet.currencyconverterapp.application.domain.interactors

import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.*
import com.plutoisnotaplanet.currencyconverterapp.application.domain.repository.CurrencySortSettingsRepository
import com.plutoisnotaplanet.currencyconverterapp.application.domain.usecases.CurrencySortSettingsUseCase
import javax.inject.Inject

class CurrencySortSettingsInteractor @Inject constructor(
    private val currencySortSettingsRepository: CurrencySortSettingsRepository
): CurrencySortSettingsUseCase {

    override suspend fun updateSortSettings(
        listType: CurrencyListType,
        settings: SortSettings
    ): Response<Unit> {
        return runResulting {
            currencySortSettingsRepository.updateSortSettings(listType, settings)
        }
    }

    override suspend fun updateSelectedCurrency(
        listType: CurrencyListType,
        currency: CurrencyViewItem
    ): Response<Unit> {
        return runResulting {
            currencySortSettingsRepository.updateSelectedCurrency(listType, currency.name)
        }
    }
}