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
        val getAllPrice = Observable.zip(NetworkProvider.apiCoinMarketCap.getPrice(cryptoCurrency), NetworkProvider.apiBx.getPriceList(),
                BiFunction<List<CoinMarketCapResult>, List<PairedCurrency>, LastSeenPrice> { cmc, bx ->
                    val cmcPrice = cmc[0].price.toDouble()
                    val chosenPrice = bx.first { cryptoCurrency == it.secondaryCurrency }
                    val lastSeenPrice = LastSeenPrice(chosenPrice.pairingId, chosenPrice.lastPrice.to2Precision().toDouble(), cmcPrice.to2Precision().toDouble(), Date())
                    DbHelper.db.lastSeen().insert(lastSeenPrice)
                    Log.d("Result", "Fetching")
                    lastSeenPrice
//                    val lastSeenPrice = when (cryptoCurrency) {
//                        CurrencyConstants.EVX_FULL_NAME -> {
//                            LastSeenPrice(bx.evx.pairingId, bx.evx.lastPrice.to2Precision().toDouble(), cmcPrice.to2Precision().toDouble(), Date())
//                        }
//                        CurrencyConstants.OMG_FULL_NAME -> {
//                            LastSeenPrice(bx.omg.pairingId, bx.omg.lastPrice.to2Precision().toDouble(), cmcPrice.to2Precision().toDouble(), Date())
//                        }
//                        CurrencyConstants.ETH_FULL_NAME -> {
//                            LastSeenPrice(bx.eth.pairingId, bx.eth.lastPrice.to2Precision().toDouble(), cmcPrice.to2Precision().toDouble(), Date())
//                        }
//                        CurrencyConstants.BTC_FULL_NAME -> {
//                            LastSeenPrice(bx.btc.pairingId, bx.btc.lastPrice.to2Precision().toDouble(), cmcPrice.to2Precision().toDouble(), Date())
//                        }
//                        else -> LastSeenPrice()
//                    }
//                    DbHelper.db.lastSeen().insert(lastSeenPrice)
//                    lastSeenPrice
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