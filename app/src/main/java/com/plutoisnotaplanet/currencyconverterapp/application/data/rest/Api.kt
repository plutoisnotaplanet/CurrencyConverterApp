package com.plutoisnotaplanet.currencyconverterapp.application.data.rest

import com.plutoisnotaplanet.currencyconverterapp.application.data.rest.dto.BaseResponse
import retrofit2.http.GET

interface Api {

    @GET("api/latest.json")
    suspend fun getExchangeRates(): BaseResponse
}