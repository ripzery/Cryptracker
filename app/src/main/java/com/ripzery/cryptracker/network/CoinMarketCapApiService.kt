package com.ripzery.cryptracker.network

import com.ripzery.cryptracker.data.CoinMarketCapResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by ripzery on 9/4/17.
 */
interface CoinMarketCapApiService {
    @GET("?limit=0")
    fun getPrice(): Observable<List<CoinMarketCapResult>>
}