package com.ripzery.cryptracker.repository

import com.ripzery.cryptracker.data.BxPrice
import com.ripzery.cryptracker.data.CoinMarketCapResult
import io.reactivex.Observable

/**
 * Created by ripzery on 10/29/17.
 */
class CryptrackerRepository(private val cryptrackerLocalDataSource: CryptrackerDataSource,
                            private val cryptrackerRemoteDataSource: CryptrackerDataSource) : CryptrackerDataSource {

    override fun getBxPrice(): Observable<BxPrice> {
        return cryptrackerRemoteDataSource.getBxPrice()
    }

    override fun getCmcPrice(currency: String): Observable<List<CoinMarketCapResult>> {
        return cryptrackerRemoteDataSource.getCmcPrice(currency)
    }

    override fun getAllPriceInterval(cryptoCurrency: String, intervalInSecond: Long): Observable<Pair<String, String>> {
        val d = cryptrackerRemoteDataSource.getAllPriceInterval(cryptoCurrency, intervalInSecond).subscribe({ }, { error -> error.printStackTrace() })
        return cryptrackerLocalDataSource.getAllPriceInterval(cryptoCurrency, intervalInSecond)
                .doOnDispose { d.dispose() }
    }

    override fun getCryptoList(): List<String> = cryptrackerLocalDataSource.getCryptoList()
    override fun loadCurrency(): Pair<String, String> = cryptrackerLocalDataSource.loadCurrency()

    companion object {
        private var INSTANCE: CryptrackerRepository? = null

        @JvmStatic
        fun getInstance(cryptrackerLocalDataSource: CryptrackerDataSource,
                        cryptrackerRemoteDataSource: CryptrackerDataSource) =
                INSTANCE ?: synchronized(CryptrackerRepository::class.java) {
                    INSTANCE ?: CryptrackerRepository(cryptrackerLocalDataSource, cryptrackerRemoteDataSource).also { INSTANCE = it }
                }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}