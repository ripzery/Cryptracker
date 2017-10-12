package com.ripzery.cryptracker.network

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ripzery on 9/4/17.
 */
object NetworkProvider {
    private val BASE_URL_BX = "https://bx.in.th/api/"
    private val BASE_URL_CRYPTOWATCH = "https://api.cryptowat.ch/markets/bitfinex/omgusd/"
    val apiBx: BxApiService by lazy {
        Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL_BX)
                .build().create(BxApiService::class.java)
    }
    val apiCryptoWatch: CryptoWatchApiService by lazy {
        Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL_CRYPTOWATCH)
                .build().create(CryptoWatchApiService::class.java)
    }
}