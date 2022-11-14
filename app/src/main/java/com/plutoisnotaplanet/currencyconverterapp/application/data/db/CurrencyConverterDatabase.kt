package com.plutoisnotaplanet.currencyconverterapp.application.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.plutoisnotaplanet.currencyconverterapp.application.data.db.dao.CurrencyDao
import com.plutoisnotaplanet.currencyconverterapp.application.data.db.dao.CurrencySortSettingsDao
import com.plutoisnotaplanet.currencyconverterapp.application.data.db.entitites.CurrencyEntity
import com.plutoisnotaplanet.currencyconverterapp.application.data.db.entitites.CurrencySortSettingsEntity

@Database(entities = [CurrencyEntity::class, CurrencySortSettingsEntity::class], version = 1, exportSchema = false)
abstract class CurrencyConverterDatabase : RoomDatabase() {

    abstract val currencyDao: CurrencyDao
    abstract val currencySortSettingsDao: CurrencySortSettingsDao

    companion object {
        private const val DATABASE_NAME = "currency_converter.db"

        const val CURRENCY_TABLE = "table_currency"
        const val CURRENCY_SORT_SETTINGS_TABLE = "table_currency_sort_settings"

        @Volatile
        private var instance: CurrencyConverterDatabase? = null

        fun getInstance(context: Context): CurrencyConverterDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): CurrencyConverterDatabase {
            return Room.databaseBuilder(context, CurrencyConverterDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}