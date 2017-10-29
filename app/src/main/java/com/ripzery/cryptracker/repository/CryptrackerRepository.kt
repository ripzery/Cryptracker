package com.ripzery.cryptracker.repository

import com.ripzery.cryptracker.data.BxPrice
import com.ripzery.cryptracker.data.CoinMarketCapResult
import io.reactivex.Observable

/**
 * Created by ripzery on 10/29/17.
 */
class CryptrackerRepository(val cryptrackerLocalDataSource: CryptrackerDataSource,
                            val cryptrackerRemoteDataSource: CryptrackerDataSource) : CryptrackerDataSource {

    override fun getBxPrice(): Observable<BxPrice> {
        return cryptrackerRemoteDataSource.getBxPrice()
    }

    override fun getCmcPrice(currency: String): Observable<List<CoinMarketCapResult>> {
        return cryptrackerRemoteDataSource.getCmcPrice(currency)
    }
}