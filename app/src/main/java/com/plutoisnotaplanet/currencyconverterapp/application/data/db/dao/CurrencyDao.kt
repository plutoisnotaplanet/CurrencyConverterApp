package com.plutoisnotaplanet.currencyconverterapp.application.data.db.dao

import androidx.room.*
import com.plutoisnotaplanet.currencyconverterapp.application.data.db.entitites.CurrencyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currency: CurrencyEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(currency: CurrencyEntity)

    @Query("UPDATE table_currency SET column_exchangeRate = :exchangeRate WHERE column_currencyCode = :currencyCode")
    suspend fun updateExchangeRate(currencyCode: String, exchangeRate: Double)

    @Query("UPDATE table_currency SET column_isFavorite = :isFavorite WHERE column_currencyCode = :currencyCode")
    suspend fun updateFavorState(currencyCode: String, isFavorite: Boolean)

    @Query("SELECT * FROM table_currency WHERE column_currencyCode = :currencyCode")
    suspend fun getCurrency(currencyCode: String): CurrencyEntity?

    @Query("SELECT * FROM table_currency ORDER BY column_currencyCode ASC")
    fun getAllCurrenciesFlow(): Flow<List<CurrencyEntity>>

    @Query("SELECT * FROM table_currency ORDER BY column_currencyCode ASC")
    fun getAllCurrencies(): List<CurrencyEntity>

    @Transaction
    suspend fun save(currency: CurrencyEntity) {
        if (getCurrency(currency.currencyCode) != null) {
            updateExchangeRate(currency.currencyCode, currency.exchangeRate)
        } else insert(currency)
    }

    @Transaction
    suspend fun save(list: List<CurrencyEntity>) {
        if (list.isEmpty()) return

        list.forEach { save(it) }
    }
}