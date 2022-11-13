package com.plutoisnotaplanet.currencyconverterapp.application.data.rest.interceptors

import com.plutoisnotaplanet.currencyconverterapp.application.Constants
import okhttp3.*


class QueryInterceptor: Interceptor {

    companion object {
        private const val QUERY_PARAM = "app_id"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val urlWithAppId = request.url.newBuilder().addQueryParameter(QUERY_PARAM, Constants.API_ID).build()
        val newRequest = request.newBuilder().url(urlWithAppId).build()

        return chain.proceed(newRequest)
    }
}