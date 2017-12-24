package com.ripzery.cryptracker.network

import co.omisego.omgshop.deserialize.BxConverterFactory
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ripzery on 9/4/17.
 */
object NetworkProvider {
    private val BASE_URL_BX = "https://bx.in.th/api/"
    private val BASE_URL_COIN_MARKET_CAP = "https://api.coinmarketcap.com/v1/ticker/"

    val apiBx: BxApiService by lazy {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(logging).build()
        Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(BxConverterFactory.create())
                .client(client)
                .baseUrl(BASE_URL_BX)
                .build().create(BxApiService::class.java)
    }
    val apiCoinMarketCap: CoinMarketCapApiService by lazy {
        Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL_COIN_MARKET_CAP)
                .build().create(CoinMarketCapApiService::class.java)
    }
}