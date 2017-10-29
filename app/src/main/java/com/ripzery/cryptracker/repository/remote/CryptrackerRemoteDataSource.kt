package com.ripzery.cryptracker.repository.remote

import com.ripzery.cryptracker.data.BxPrice
import com.ripzery.cryptracker.data.CoinMarketCapResult
import com.ripzery.cryptracker.network.NetworkProvider
import com.ripzery.cryptracker.repository.CryptrackerDataSource
import io.reactivex.Observable

/**
 * Created by ripzery on 10/29/17.
 */
class CryptrackerRemoteDataSource : CryptrackerDataSource {
    override fun getBxPrice(): Observable<BxPrice> {
        return NetworkProvider.apiBx.getPriceList()
    }

    override fun getCmcPrice(currency: String): Observable<List<CoinMarketCapResult>> {
        return NetworkProvider.apiCoinMarketCap.getPrice(currency)
    }
}