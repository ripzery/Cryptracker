package com.ripzery.cryptracker.network

import android.util.Log
import com.ripzery.cryptracker.data.BxOMG
import com.ripzery.cryptracker.data.CryptoWatchOMG
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
    fun getOMGPriceForInterval(intervalInSecond: Long, errorCb: (Throwable) -> Unit, successCb: (String, String) -> Unit): Disposable {
        val getAllOMGPrice = Observable.zip(
                NetworkProvider.apiCryptoWatch.getOMGPrice(),
                NetworkProvider.apiBx.getOMGPrice(),
                BiFunction<CryptoWatchOMG, BxOMG, Pair<String, String>> { cryptoOmg, bxOmg ->
                    Pair("%.2f".format(cryptoOmg.result.price), "%.2f".format(bxOmg.omg.lastPrice))
                }
        )
        return Observable.interval(0, intervalInSecond, TimeUnit.SECONDS)
                .flatMap { getAllOMGPrice }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG, "Cryptowatch: ${it.first}, Bx: ${it.second}")
                    successCb(it.first, it.second)
                }, {
                    Log.e(TAG, "Error on calling an API : ")
                    it.printStackTrace()
                    errorCb(it)
                })
    }
}