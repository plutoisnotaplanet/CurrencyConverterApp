package com.plutoisnotaplanet.currencyconverterapp.di

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.plutoisnotaplanet.currencyconverterapp.application.Constants
import com.plutoisnotaplanet.currencyconverterapp.application.data.rest.Api
import com.plutoisnotaplanet.currencyconverterapp.application.data.rest.converter.CurrencyDeserializer
import com.plutoisnotaplanet.currencyconverterapp.application.data.rest.dto.RatesResponse
import com.plutoisnotaplanet.currencyconverterapp.application.data.rest.interceptors.QueryInterceptor
import com.plutoisnotaplanet.currencyconverterapp.application.data.rest.interceptors.ResponseInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(QueryInterceptor())
            .addInterceptor(ResponseInterceptor())
        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideGsonConverter(): GsonConverterFactory {
        val gsonBuilder = GsonBuilder()

        val type = object : TypeToken<RatesResponse>() {}.type
        gsonBuilder.registerTypeAdapter(type, CurrencyDeserializer())

        val gson = gsonBuilder.create()
        return GsonConverterFactory.create(gson)
    }
}