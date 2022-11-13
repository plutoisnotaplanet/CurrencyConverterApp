package com.plutoisnotaplanet.currencyconverterapp.di

import android.content.Context
import com.plutoisnotaplanet.currencyconverterapp.application.data.db.CurrencyConverterDatabase
import com.plutoisnotaplanet.currencyconverterapp.application.data.db.dao.CurrencyDao
import com.plutoisnotaplanet.currencyconverterapp.application.data.db.dao.CurrencySortSettingsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideCurrencyDatabase(@ApplicationContext context: Context): CurrencyConverterDatabase {
        return CurrencyConverterDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideCurrencyDao(database: CurrencyConverterDatabase): CurrencyDao {
        return database.currencyDao
    }

    @Singleton
    @Provides
    fun provideCurrencySortSettingsDao(database: CurrencyConverterDatabase): CurrencySortSettingsDao {
        return database.currencySortSettingsDao
    }
}