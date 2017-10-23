package com.ripzery.cryptracker.network

import android.util.Log
import com.ripzery.cryptracker.data.BxPrice
import com.ripzery.cryptracker.data.CoinMarketCapResult
import com.ripzery.cryptracker.extensions.TAG
import com.ripzery.cryptracker.utils.FirestoreHelper
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
    var lastPriceOmiseGo: Pair<Double, Double>? = null
    var lastPriceEvx: Pair<Double, Double>? = null
    fun getPriceForInterval(cryptoCurrency: String, intervalInSecond: Long, errorCb: (Throwable) -> Unit, successCb: (String, String) -> Unit): Disposable {
        val getAllPrice = Observable.zip(
                NetworkProvider.apiCoinMarketCap.getPrice(cryptoCurrency),
                NetworkProvider.apiBx.getPriceList(),
                BiFunction<List<CoinMarketCapResult>, BxPrice, Pair<String, String>> { coinMarketCap, bx ->
                    when (cryptoCurrency) {
                        "everex" -> {
                            lastPriceEvx = Pair(coinMarketCap[0].price.toDouble(), bx.evx.lastPrice)
                            Pair("%.2f".format(coinMarketCap[0].price.toDouble()), "%.2f".format(bx.evx.lastPrice))
                        }
                        "omisego" -> {
                            lastPriceOmiseGo = Pair(coinMarketCap[0].price.toDouble(), bx.omg.lastPrice)
                            Pair("%.2f".format(lastPriceOmiseGo!!.first), "%.2f".format(lastPriceOmiseGo!!.second))
                        }
                        else -> Pair("Unknown", "Unknown")
                    }
                }
        )
        return Observable.interval(0, intervalInSecond, TimeUnit.SECONDS)
                .flatMap { getAllPrice }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    successCb(it.first, it.second)
                }, {
                    Log.e(TAG, "Error on calling an API : ")
                    it.printStackTrace()
                    errorCb(it)
                })
    }
}