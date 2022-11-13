package com.plutoisnotaplanet.currencyconverterapp.application.data.rest.interceptors

import com.plutoisnotaplanet.currencyconverterapp.application.data.rest.ApiException
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class ResponseInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val bodyString = response.body!!.string()

        checkCommonErrors(response)

        if (response.isSuccessful) {
            return copyResponse(response, bodyString)
        }
        throw ApiException.BadRequest()
    }

    private fun copyResponse(response: Response, body: String): Response {
        val builder = response.newBuilder()
        response.body.use {
            it?.let {
                builder.body(body.toResponseBody(it.contentType()))
            }
        }
        return builder.build()
    }

    private fun checkCommonErrors(response: Response) {
        if (!response.isSuccessful) {
            when (response.code) {
                400 -> throw ApiException.BadRequest()
                401 -> throw ApiException.UnauthorizedException()
                429 -> throw ApiException.TooManyRequests()
                500 -> throw ApiException.InternalServerError()
            }
        }
    }

}