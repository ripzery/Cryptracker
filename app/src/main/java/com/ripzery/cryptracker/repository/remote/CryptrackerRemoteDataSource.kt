package com.ripzery.cryptracker.repository.remote

import android.util.Log
import com.ripzery.cryptracker.data.CoinMarketCapResult
import com.ripzery.cryptracker.data.PairedCurrency
import com.ripzery.cryptracker.db.entities.LastSeenPrice
import com.ripzery.cryptracker.extensions.applyCurrency
import com.ripzery.cryptracker.extensions.to2Precision
import com.ripzery.cryptracker.network.NetworkProvider
import com.ripzery.cryptracker.repository.CryptrackerDataSource
import com.ripzery.cryptracker.utils.CurrencyConstants
import com.ripzery.cryptracker.utils.CurrencyFullnameHelper
import com.ripzery.cryptracker.utils.DbHelper
import com.ripzery.cryptracker.utils.SharePreferenceHelper
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by ripzery on 10/29/17.
 */
object CryptrackerRemoteDataSource : CryptrackerDataSource {
    private var mCurrency: Pair<String, String> = Pair(CurrencyConstants.USD, CurrencyConstants.THB)

    override fun updatePriceWithInterval(cryptoCurrency: String, intervalInSecond: Long): Observable<LastSeenPrice> {
        val getAllPrice = Observable.zip(NetworkProvider.apiCoinMarketCap.getPrice(), NetworkProvider.apiBx.getPriceList(),
                BiFunction<List<CoinMarketCapResult>, List<PairedCurrency>, LastSeenPrice> { cmc, bx ->
                    val bxchosenPrice = bx.first { cryptoCurrency == it.secondaryCurrency }
                    val cmcChosenPrice = cmc.first { it.symbol.contains(cryptoCurrency) }.price.toDouble()
                    val lastSeenPrice = LastSeenPrice(bxchosenPrice.secondaryCurrency, bxchosenPrice.lastPrice.to2Precision().toDouble(), cmcChosenPrice.to2Precision().toDouble(), Date())
                    DbHelper.db.lastSeen().insert(lastSeenPrice)
                    lastSeenPrice
                })

        return getAllPrice
                .map { it.applyCurrency(mCurrency) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
    }

    override fun getCryptoList(): List<String> = SharePreferenceHelper.readCryptocurrencySetting().toList()
    override fun loadCurrency(): Pair<String, String> {
        mCurrency = Pair(SharePreferenceHelper.readCurrencyTop(), SharePreferenceHelper.readCurrencyBottom())
        return mCurrency
    }
}