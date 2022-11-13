package com.plutoisnotaplanet.currencyconverterapp.application.data.rest.dto

import com.plutoisnotaplanet.currencyconverterapp.application.data.db.entitites.CurrencyEntity

data class RatesResponse(val currenciesList: List<CurrencyEntity>)