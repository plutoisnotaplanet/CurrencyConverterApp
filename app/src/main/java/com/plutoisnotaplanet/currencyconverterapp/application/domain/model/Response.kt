package com.plutoisnotaplanet.currencyconverterapp.application.domain.model

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

sealed class Response<out T: Any> {

    companion object {
        fun error(text: String): Error {
            return Error(Throwable(text))
        }
    }

    data class Success<out T : Any> constructor(val data: T? = null): Response<T>()
    data class Error(val throwable: Throwable) : Response<Nothing>()

    val isError: Boolean
        get() = this is Error

    val isSuccess: Boolean
        get() = this !is Error

    fun getValueOrNull(): T? {
        return if (this is Success) return this.data
        else null
    }

    fun exceptionOrNull(): Throwable? =
        if (this is Error) this.throwable
        else null


    fun getValue(): T {
        return (this as Success).data!!
    }

    fun exception(): Throwable = (this as Error).throwable

}

inline fun  <T: Any> runResulting(block: () -> T): Response<T> {
    return try {
        Response.Success(block())
    } catch (e: Throwable) {
        Response.Error(e)
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <T: Any> Response<T>.onSuccess(action: (value: T) -> Unit): Response<T> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (isSuccess) action(this.getValueOrNull() as T)
    return this
}



@OptIn(ExperimentalContracts::class)
inline fun <T: Any> Response<T>.onFailure(action: (exception: Throwable) -> Unit): Response<T> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    exceptionOrNull()?.let { action(it) }
    return this
}
