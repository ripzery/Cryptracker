package com.ripzery.cryptracker.repository

import com.ripzery.cryptracker.data.BxPrice
import com.ripzery.cryptracker.data.CoinMarketCapResult
import com.ripzery.cryptracker.db.entities.LastSeenPrice
import io.reactivex.Observable

/**
 * Created by ripzery on 10/29/17.
 */
interface CryptrackerDataSource {
    fun updatePriceWithInterval(cryptoCurrency: String, intervalInSecond: Long): Observable<LastSeenPrice>
    fun loadCurrency(): Pair<String, String>
    fun getCryptoList(): List<String>
}