package com.ripzery.cryptracker.network

import com.ripzery.cryptracker.data.CryptoWatchOMG
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by ripzery on 9/4/17.
 */
interface CryptoWatchApiService {
    @GET("price")
    fun getOMGPrice(): Observable<CryptoWatchOMG>
}