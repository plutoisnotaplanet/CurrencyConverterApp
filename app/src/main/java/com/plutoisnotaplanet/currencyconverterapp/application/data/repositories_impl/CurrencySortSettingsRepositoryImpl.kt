package com.plutoisnotaplanet.currencyconverterapp.application.data.repositories_impl

import com.plutoisnotaplanet.currencyconverterapp.application.data.db.dao.CurrencyDao
import com.plutoisnotaplanet.currencyconverterapp.application.data.db.dao.CurrencySortSettingsDao
import com.plutoisnotaplanet.currencyconverterapp.application.data.db.entitites.CurrencySortSettingsEntity
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.Currency
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.CurrencyListType
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.SortBy
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.SortSettings
import com.plutoisnotaplanet.currencyconverterapp.application.domain.repository.CurrencySortSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrencySortSettingsRepositoryImpl @Inject constructor(
    private val currencySortSettingsDao: CurrencySortSettingsDao,
    private val currencyDao: CurrencyDao
) : CurrencySortSettingsRepository {

    override suspend fun observeSortSettingsByListType(listType: CurrencyListType): Flow<SortSettings> {
        val listTypeId = listType.ordinal
        val settingsFlow = if (currencySortSettingsDao.hasSortSettings(listTypeId)) {
            currencySortSettingsDao.getSortSettingsFlow(listTypeId)
        } else {
            val emptySortSettings = CurrencySortSettingsEntity(
                id = listTypeId,
                currencyCode = "USD",
                sortByName = SortBy.None.ordinal,
                sortByRate = SortBy.None.ordinal,
            )
            currencySortSettingsDao.save(emptySortSettings)
            currencySortSettingsDao.getSortSettingsFlow(listTypeId)
        }
        return settingsFlow.map { settingsEntity ->
            settingsEntity.toSortSettings(
                currency = currencyDao.getCurrency(settingsEntity.currencyCode)?.toModel()
                    ?: Currency.getUsdCurrency()
            )
        }
    }

    override suspend fun observeSortSettings(): Flow<Pair<SortSettings, SortSettings>> {
        return observeSortSettingsByListType(CurrencyListType.POPULAR).combine(
            observeSortSettingsByListType(CurrencyListType.FAVORITE)
        ) { popularSettings, favoriteSettings ->
            popularSettings to favoriteSettings
        }.distinctUntilChanged { old, new ->
            old.first == new.first && old.second == new.second
        }
    }

    override suspend fun updateSortSettings(listType: CurrencyListType, settings: SortSettings) {
        val entity = settings.toCurrencySortSettingsEntity(listType.ordinal)
        currencySortSettingsDao.save(entity)
    }

    override suspend fun updateSelectedCurrency(listType: CurrencyListType, currencyCode: String) {
        currencySortSettingsDao.updateSelectedCountryById(listType.ordinal, currencyCode)
    }
}