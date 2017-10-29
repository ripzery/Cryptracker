package com.ripzery.cryptracker.repository.local

import com.ripzery.cryptracker.data.BxPrice
import com.ripzery.cryptracker.data.CoinMarketCapResult
import com.ripzery.cryptracker.data.PairedCurrency
import com.ripzery.cryptracker.extensions.to2Precision
import com.ripzery.cryptracker.repository.CryptrackerDataSource
import com.ripzery.cryptracker.utils.DbHelper
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by ripzery on 10/29/17.
 */
object CryptrackerLocalDataSource : CryptrackerDataSource {

    override fun getBxPrice(): Observable<BxPrice> {
        return Observable.just(BxPrice(PairedCurrency(1, 255.5, 15.4),
                PairedCurrency(2, 33.0, 1.0),
                PairedCurrency(3, 9000.0, 1000.0),
                PairedCurrency(4, 190000.4, 10000.2)))
    }

    override fun getCmcPrice(currency: String): Observable<List<CoinMarketCapResult>> {
        return Observable.just(listOf(CoinMarketCapResult("255", "24", "omg", "1")))
    }

    override fun getAllPriceInterval(cryptoCurrency: String, intervalInSecond: Long): Observable<Pair<String, String>> {
        return Observable.interval(0, intervalInSecond, TimeUnit.SECONDS)
                .flatMap {
                    val lastSeen = DbHelper.db.lastSeen()
                    when (cryptoCurrency) {
                        "omisego" -> Observable.just(Pair(lastSeen.getPrice(26).cmcPrice.to2Precision(), lastSeen.getPrice(26).bxPrice.to2Precision()))
                        "everex" -> Observable.just(Pair(lastSeen.getPrice(28).cmcPrice.to2Precision(), lastSeen.getPrice(28).bxPrice.to2Precision()))
                        "ethereum" -> Observable.just(Pair(lastSeen.getPrice(21).cmcPrice.to2Precision(), lastSeen.getPrice(21).bxPrice.to2Precision()))
                        "bitcoin" -> Observable.just(Pair(lastSeen.getPrice(1).cmcPrice.to2Precision(), lastSeen.getPrice(1).bxPrice.to2Precision()))
                        else -> Observable.just(Pair("1", "2"))
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
    }
}