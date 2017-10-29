package com.ripzery.cryptracker.repository.local

import com.ripzery.cryptracker.data.BxPrice
import com.ripzery.cryptracker.data.CoinMarketCapResult
import com.ripzery.cryptracker.data.PairedCurrency
import com.ripzery.cryptracker.repository.CryptrackerDataSource
import io.reactivex.Observable

/**
 * Created by ripzery on 10/29/17.
 */
class CryptrackerLocalDataSource : CryptrackerDataSource {
    override fun getBxPrice(): Observable<BxPrice> {
        return Observable.just(BxPrice(PairedCurrency(1, 255.5, 15.4),
                PairedCurrency(2, 33.0, 1.0),
                PairedCurrency(3, 9000.0, 1000.0),
                PairedCurrency(4, 190000.4, 10000.2)))
    }

    override fun getCmcPrice(currency: String): Observable<List<CoinMarketCapResult>> {
        return Observable.just(listOf(CoinMarketCapResult("255", "24", "omg", "1")))
    }
}