package com.plutoisnotaplanet.currencyconverterapp.application.data.rest.dto

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("rates")
    val currencyRatesResponse: RatesResponse
) {
}