package com.plutoisnotaplanet.currencyconverterapp.application.data.rest

import okio.IOException

sealed class ApiException(message: String? = null) : IOException(message) {
    data class BadRequest(override val message: String? = "Ошибка соединения") : ApiException()
    data class UnauthorizedException(override val message: String? = "Обнаружена альтернативная сессия") : ApiException()
    data class TooManyRequests(override val message: String? = "Слишком много запросов") : ApiException()
    data class InternalServerError(override val message: String? = "Ошибка в работе сервера") : ApiException()
}