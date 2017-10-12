package com.ripzery.cryptracker.network

import android.util.Log
import com.ripzery.cryptracker.data.BxPrice
import com.ripzery.cryptracker.data.CoinMarketCapResult
import com.ripzery.cryptracker.extensions.TAG
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by ripzery on 9/7/17.
 */
object DataSource {
    fun getPriceForInterval(cryptoCurrency: String, intervalInSecond: Long, errorCb: (Throwable) -> Unit, successCb: (String, String) -> Unit): Disposable {
        val getAllPrice = Observable.zip(
                NetworkProvider.apiCoinMarketCap.getPrice(cryptoCurrency),
                NetworkProvider.apiBx.getPriceList(),
                BiFunction<List<CoinMarketCapResult>, BxPrice, Pair<String, String>> { coinMarketCap, bx ->
                    when (cryptoCurrency) {
                        "everex" -> Pair("%.2f".format(coinMarketCap[0].price.toDouble()), "%.2f".format(bx.evx.lastPrice))
                        "omisego" -> Pair("%.2f".format(coinMarketCap[0].price.toDouble()), "%.2f".format(bx.omg.lastPrice))
                        else -> Pair("Unknown", "Unknown")
                    }
                }
        )
        return Observable.interval(0, intervalInSecond, TimeUnit.SECONDS)
                .flatMap { getAllPrice }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG, "CoinMarketCap: ${it.first}, Bx: ${it.second}")
                    successCb(it.first, it.second)
                }, {
                    Log.e(TAG, "Error on calling an API : ")
                    it.printStackTrace()
                    errorCb(it)
                })
    }
}