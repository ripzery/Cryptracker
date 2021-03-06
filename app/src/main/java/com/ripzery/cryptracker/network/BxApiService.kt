package com.ripzery.cryptracker.network

import com.ripzery.cryptracker.data.BxPrice
import com.ripzery.cryptracker.data.DynamicBxPrice
import com.ripzery.cryptracker.data.PairedCurrency
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by ripzery on 9/4/17.
 */
interface BxApiService {
    @GET(".")
    fun getPriceList(): Observable<List<PairedCurrency>>
}