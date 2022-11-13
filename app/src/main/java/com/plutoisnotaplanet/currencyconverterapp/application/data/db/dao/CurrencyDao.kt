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

    @Query("SELECT * FROM table_currency WHERE column_currencyCode = :currencyCode")
    suspend fun getCurrency(currencyCode: String): CurrencyEntity?

    @Query("SELECT * FROM table_currency ORDER BY column_currencyCode ASC")
    fun getAllCurrencies(): Flow<List<CurrencyEntity>>

    @Transaction
    suspend fun changeFavoriteState(currencyCode: String, isFavorite: Boolean) {
        val currency = getCurrency(currencyCode) ?: return
        currency.isFavorite = isFavorite
        update(currency)
    }

    @Transaction
    suspend fun save(currency: CurrencyEntity) {
        if (getCurrency(currency.currencyCode) != null) {
            update(currency)
        } else insert(currency)
    }

    @Transaction
    suspend fun save(list: List<CurrencyEntity>) {
        if (list.isEmpty()) return

        list.forEach { save(it) }
    }
}