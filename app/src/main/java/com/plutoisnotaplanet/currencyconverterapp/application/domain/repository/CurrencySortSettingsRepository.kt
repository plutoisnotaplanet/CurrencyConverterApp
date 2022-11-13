package com.plutoisnotaplanet.currencyconverterapp.application.domain.repository

import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.CurrencyListType
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.SortSettings
import kotlinx.coroutines.flow.Flow

interface CurrencySortSettingsRepository {

    suspend fun observeSortSettingsByListType(listType: CurrencyListType): Flow<SortSettings>

    suspend fun observeSortSettings(): Flow<Pair<SortSettings, SortSettings>>

    suspend fun getSortSettingsByListType(listType: CurrencyListType): SortSettings

    suspend fun updateSortSettings(listType: CurrencyListType, settings: SortSettings)
}