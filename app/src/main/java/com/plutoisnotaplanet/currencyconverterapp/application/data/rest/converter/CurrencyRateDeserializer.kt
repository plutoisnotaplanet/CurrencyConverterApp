package com.plutoisnotaplanet.currencyconverterapp.application.data.rest.converter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.plutoisnotaplanet.currencyconverterapp.application.data.db.entitites.CurrencyEntity
import com.plutoisnotaplanet.currencyconverterapp.application.data.rest.dto.RatesResponse
import java.lang.reflect.Type

class CurrencyRateDeserializer: JsonDeserializer<RatesResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): RatesResponse {

        val listResponseObject = json?.asJsonObject

        val currenciesNameSet = listResponseObject?.keySet()

        val currenciesList = currenciesNameSet?.mapNotNull { currencyName ->
            listResponseObject.getAsJsonPrimitive(currencyName)?.asDouble?.let { exchangeRate ->
                CurrencyEntity(currencyName, exchangeRate)
            }
        } ?: emptyList()

        return RatesResponse(currenciesList)
    }

}