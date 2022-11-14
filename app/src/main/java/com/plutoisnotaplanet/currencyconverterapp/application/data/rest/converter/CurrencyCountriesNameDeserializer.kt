package com.plutoisnotaplanet.currencyconverterapp.application.data.rest.converter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.plutoisnotaplanet.currencyconverterapp.application.data.rest.dto.CurrencyCountryNameResponse
import java.lang.reflect.Type

class CurrencyCountriesNameDeserializer: JsonDeserializer<CurrencyCountryNameResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): CurrencyCountryNameResponse {

        val listResponseObject = json?.asJsonObject

        val currenciesNameSet = listResponseObject?.keySet()

        val currenciesMap = mutableMapOf<String, String>()

         currenciesNameSet?.forEach { currencyCode ->
            listResponseObject.getAsJsonPrimitive(currencyCode)?.asString?.let { countryName ->
                currenciesMap[currencyCode] = countryName
            }
        }
        return CurrencyCountryNameResponse(currenciesMap)
    }

}