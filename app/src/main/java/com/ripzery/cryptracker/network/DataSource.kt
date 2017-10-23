package com.ripzery.cryptracker.network

import android.util.Log
import com.ripzery.cryptracker.data.BxPrice
import com.ripzery.cryptracker.data.CoinMarketCapResult
import com.ripzery.cryptracker.extensions.TAG
import com.ripzery.cryptracker.extensions.to2Precision
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
                    val cmcPrice = coinMarketCap[0].price.toDouble()
                    when (cryptoCurrency) {
                        "everex" -> {
                            lastPriceEvx = Pair(cmcPrice, bx.evx.lastPrice)
                            Pair(cmcPrice.to2Precision(), bx.evx.lastPrice.to2Precision())
                        }
                        "omisego" -> {
                            lastPriceOmiseGo = Pair(cmcPrice, bx.omg.lastPrice)
                            Pair(cmcPrice.to2Precision(), lastPriceOmiseGo!!.second.to2Precision())
                        }
                        "ethereum" -> {
                            Pair(cmcPrice.to2Precision(), bx.eth.lastPrice.to2Precision())
                        }
                        "bitcoin" -> {
                            Pair(cmcPrice.to2Precision(), bx.btc.lastPrice.to2Precision())
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