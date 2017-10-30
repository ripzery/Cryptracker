package com.ripzery.cryptracker.repository.local

import android.util.Log
import com.ripzery.cryptracker.data.BxPrice
import com.ripzery.cryptracker.data.CoinMarketCapResult
import com.ripzery.cryptracker.data.PairedCurrency
import com.ripzery.cryptracker.db.entities.LastSeenPrice
import com.ripzery.cryptracker.repository.CryptrackerDataSource
import com.ripzery.cryptracker.utils.DbHelper
import com.ripzery.cryptracker.utils.SharePreferenceHelper
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by ripzery on 10/29/17.
 */
object CryptrackerLocalDataSource : CryptrackerDataSource {
    private var mCurrency: Pair<String, String> = Pair("usd", "thb")
    private val USD_TO_THB = 33.23
    private val THB_TO_USD = 0.03

    override fun getBxPrice(): Observable<BxPrice> {
        return Observable.just(BxPrice(PairedCurrency(1, 255.5, 15.4),
                PairedCurrency(2, 33.0, 1.0),
                PairedCurrency(3, 9000.0, 1000.0),
                PairedCurrency(4, 190000.4, 10000.2)))
    }

    override fun getCmcPrice(currency: String): Observable<List<CoinMarketCapResult>> {
        return Observable.just(listOf(CoinMarketCapResult("255", "24", "omg", "1")))
    }

    override fun updatePriceWithInterval(cryptoCurrency: String, intervalInSecond: Long): Observable<LastSeenPrice> {
        val lastSeen = DbHelper.db.lastSeen()
        val lastSeenObservable = when (cryptoCurrency) {
            "omisego" -> Observable.just(lastSeen.getPrice(26) ?: LastSeenPrice())
            "everex" -> Observable.just(lastSeen.getPrice(28) ?: LastSeenPrice())
            "ethereum" -> Observable.just(lastSeen.getPrice(21) ?: LastSeenPrice())
            "bitcoin" -> Observable.just(lastSeen.getPrice(1) ?: LastSeenPrice())
            else -> Observable.just(LastSeenPrice())
        }
        return lastSeenObservable.map {
            it.apply {
                when ("${mCurrency.first}${mCurrency.second}") {
                    "usdusd" -> bxPrice *= THB_TO_USD
                    "usdthb" -> Unit
                    "thbthb" -> cmcPrice *= USD_TO_THB
                    "thbusd" -> {
                        bxPrice *= THB_TO_USD
                        cmcPrice *= USD_TO_THB
                    }
                    "......" -> Unit
                    else -> throw UnsupportedOperationException("Unsupported currency!")
                }
            }
        }.subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
    }

    override fun getCryptoList(): List<String> = SharePreferenceHelper.readCryptocurrencySetting().toList()
    override fun loadCurrency(): Pair<String, String> {
        mCurrency = Pair(SharePreferenceHelper.readCurrencyTop(), SharePreferenceHelper.readCurrencyBottom())
        Log.d("LocalSource", "LoadCurrency!" + mCurrency.toString())
        return mCurrency
    }
}