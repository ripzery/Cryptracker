package com.ripzery.cryptracker.repository.local

import com.ripzery.cryptracker.db.entities.LastSeenPrice
import com.ripzery.cryptracker.extensions.applyCurrency
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
    override fun updatePriceWithInterval(cryptoCurrency: String, intervalInSecond: Long): Observable<LastSeenPrice> {
        val lastSeen = DbHelper.db.lastSeen()
        val lastSeenObservable = when (cryptoCurrency) {
            "omisego" -> Observable.just(lastSeen.getPrice(26) ?: LastSeenPrice())
            "everex" -> Observable.just(lastSeen.getPrice(28) ?: LastSeenPrice())
            "ethereum" -> Observable.just(lastSeen.getPrice(21) ?: LastSeenPrice())
            "bitcoin" -> Observable.just(lastSeen.getPrice(1) ?: LastSeenPrice())
            else -> Observable.just(LastSeenPrice())
        }
        return lastSeenObservable.map { it.applyCurrency(mCurrency) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
    }

    override fun getCryptoList(): List<String> = SharePreferenceHelper.readCryptocurrencySetting().toList()
    override fun loadCurrency(): Pair<String, String> {
        mCurrency = Pair(SharePreferenceHelper.readCurrencyTop(), SharePreferenceHelper.readCurrencyBottom())
        return mCurrency
    }
}