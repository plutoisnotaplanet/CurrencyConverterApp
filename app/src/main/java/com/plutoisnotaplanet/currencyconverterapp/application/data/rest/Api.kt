package com.plutoisnotaplanet.currencyconverterapp.application.data.rest

import com.plutoisnotaplanet.currencyconverterapp.application.data.rest.dto.BaseResponse
import com.plutoisnotaplanet.currencyconverterapp.application.data.rest.dto.CurrencyCountryNameResponse
import retrofit2.http.GET

interface Api {

    @GET("api/latest.json")
    suspend fun getExchangeRates(): BaseResponse

    @GET("api/currencies.json")
    suspend fun getCountriesNames(): CurrencyCountryNameResponse
}