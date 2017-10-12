package com.ripzery.cryptracker.network

import com.ripzery.cryptracker.data.BxOMG
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by ripzery on 9/4/17.
 */
interface BxApiService {
    @GET(".")
    fun getOMGPrice(): Observable<BxOMG>
}