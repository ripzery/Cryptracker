package com.ripzery.cryptracker.repository.remote

import com.ripzery.cryptracker.data.CoinMarketCapResult
import com.ripzery.cryptracker.data.PairedCurrency
import com.ripzery.cryptracker.db.entities.LastSeenPrice
import com.ripzery.cryptracker.extensions.applyCurrency
import com.ripzery.cryptracker.extensions.to2Precision
import com.ripzery.cryptracker.network.NetworkProvider
import com.ripzery.cryptracker.repository.CryptrackerDataSource
import com.ripzery.cryptracker.utils.CurrencyConstants
import com.ripzery.cryptracker.utils.DbHelper
import com.ripzery.cryptracker.utils.SharePreferenceHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by ripzery on 10/29/17.
 */
object CryptrackerRemoteDataSource : CryptrackerDataSource {
    private var mCurrency: Pair<String, String> = Pair(CurrencyConstants.USD, CurrencyConstants.THB)

    override fun fetchPriceAndSave(intervalInSecond: Long): Observable<List<Pair<PairedCurrency, CoinMarketCapResult>>>? {
        val getAllPrice = Observable.zip(NetworkProvider.apiCoinMarketCap.getPrice(), NetworkProvider.apiBx.getPriceList(),
                BiFunction<List<CoinMarketCapResult>, List<PairedCurrency>, List<Pair<PairedCurrency, CoinMarketCapResult>>> { cmc, bx ->
                    val bxTHBList = bx.filter { it.primaryCurrency == "THB" }.map { it.secondaryCurrency }
                    val bxPriceList = bx.filter { it.primaryCurrency == "THB" }
                    val cmcPriceList = cmc.filter { it.id != "das" && bxTHBList.contains(it.symbol) || it.id == "dash" || it.id == "power-ledger" }
                    val mappedPrice = bxPriceList.map {
                        Pair(
                                it,
                                cmcPriceList.first { cmcPrice -> cmcPrice.symbol.contains(it.secondaryCurrency) }
                        )
                    }

                    for ((bxCurrency, cmcCurrency) in mappedPrice) {
                        val lastSeenPrice = LastSeenPrice(bxCurrency.secondaryCurrency,
                                bxCurrency.lastPrice.to2Precision().toDouble(),
                                cmcCurrency.price.toDouble().to2Precision().toDouble(),
                                Date()
                        )
                        DbHelper.db.lastSeen().insert(lastSeenPrice)
                    }

                    mappedPrice
                })

        return getAllPrice
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getCryptoList(): List<String> = SharePreferenceHelper.readCryptocurrencySetting().toList()
    override fun loadCurrency(): Pair<String, String> {
        mCurrency = Pair(SharePreferenceHelper.readCurrencyTop(), SharePreferenceHelper.readCurrencyBottom())
        return mCurrency
    }
}