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
        currency: Currency
    ): Response<Unit> {
        return runResulting {
            val currentSettings = currencySortSettingsRepository.getSortSettingsByListType(listType)
            if (currentSettings.selectedCurrency.name == currency.name) return@runResulting

            val updatedSettings = currentSettings.copy(selectedCurrency = currency)
            currencySortSettingsRepository.updateSortSettings(listType, updatedSettings)
        }
    }
}