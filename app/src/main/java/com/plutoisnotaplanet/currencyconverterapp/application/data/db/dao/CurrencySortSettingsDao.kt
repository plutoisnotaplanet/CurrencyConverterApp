package com.plutoisnotaplanet.currencyconverterapp.application.data.db.dao

import androidx.room.*
import com.plutoisnotaplanet.currencyconverterapp.application.data.db.entitites.CurrencyEntity
import com.plutoisnotaplanet.currencyconverterapp.application.data.db.entitites.CurrencySortSettingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencySortSettingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currency: CurrencySortSettingsEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(currency: CurrencySortSettingsEntity)

    @Query("SELECT * FROM table_currency_sort_settings WHERE column_sortSettingsId = :id")
    suspend fun getSortSettings(id: Int): CurrencySortSettingsEntity?

    @Query("SELECT EXISTS(SELECT * FROM table_currency_sort_settings WHERE column_sortSettingsId =:id)")
    suspend fun hasSortSettings(id: Int): Boolean

    @Query("UPDATE table_currency_sort_settings SET column_selectedCurrencyCode = :currencyCode WHERE column_sortSettingsId = :id")
    suspend fun updateSelectedCountryById(id: Int, currencyCode: String)

    @Query("SELECT * FROM table_currency_sort_settings WHERE column_sortSettingsId = :id")
    fun getSortSettingsFlow(id: Int): Flow<CurrencySortSettingsEntity>

    @Transaction
    suspend fun save(settings: CurrencySortSettingsEntity) {
        if (getSortSettings(settings.id) != null) {
            update(settings)
        } else insert(settings)
    }
}
