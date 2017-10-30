package com.ripzery.cryptracker.repository.remote

import com.ripzery.cryptracker.data.BxPrice
import com.ripzery.cryptracker.data.CoinMarketCapResult
import com.ripzery.cryptracker.db.LastSeenPrice
import com.ripzery.cryptracker.extensions.to2Precision
import com.ripzery.cryptracker.network.NetworkProvider
import com.ripzery.cryptracker.repository.CryptrackerDataSource
import com.ripzery.cryptracker.services.FirestoreService
import com.ripzery.cryptracker.utils.Contextor
import com.ripzery.cryptracker.utils.DbHelper
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by ripzery on 10/29/17.
 */
object CryptrackerRemoteDataSource : CryptrackerDataSource {
    override fun getBxPrice(): Observable<BxPrice> {
        return NetworkProvider.apiBx.getPriceList()
    }

    override fun getCmcPrice(currency: String): Observable<List<CoinMarketCapResult>> {
        return NetworkProvider.apiCoinMarketCap.getPrice(currency)
    }

    override fun updatePriceWithInterval(cryptoCurrency: String, intervalInSecond: Long): Observable<Pair<String, String>> {
        val getAllPrice = Observable.zip(getCmcPrice(cryptoCurrency), getBxPrice(),
                BiFunction<List<CoinMarketCapResult>, BxPrice, Pair<String, String>> { cmc, bx ->
                    val cmcPrice = cmc[0].price.toDouble()
                    when (cryptoCurrency) {
                        "everex" -> {
                            val lastPriceEvx = Pair(cmcPrice, bx.evx.lastPrice)
                            DbHelper.db.lastSeen().insert(LastSeenPrice(bx.evx.pairingId, bx.evx.lastPrice, cmcPrice))
                            Pair(cmcPrice.to2Precision(), bx.evx.lastPrice.to2Precision())
                        }
                        "omisego" -> {
                            val lastPriceOmiseGo = Pair(cmcPrice, bx.omg.lastPrice)
                            DbHelper.db.lastSeen().insert(LastSeenPrice(bx.omg.pairingId, bx.omg.lastPrice, cmcPrice))
                            Pair(cmcPrice.to2Precision(), bx.omg.lastPrice.to2Precision())
                        }
                        "ethereum" -> {
                            DbHelper.db.lastSeen().insert(LastSeenPrice(bx.eth.pairingId, bx.eth.lastPrice, cmcPrice))
                            Pair(cmcPrice.to2Precision(), bx.eth.lastPrice.to2Precision())
                        }
                        "bitcoin" -> {
                            DbHelper.db.lastSeen().insert(LastSeenPrice(bx.btc.pairingId, bx.btc.lastPrice, cmcPrice))
                            Pair(cmcPrice.to2Precision(), bx.btc.lastPrice.to2Precision())
                        }
                        else -> Pair("Unknown", "Unknown")
                    }
                })

        return Observable.interval(0, intervalInSecond, TimeUnit.SECONDS)
                .flatMap { getAllPrice }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
    }

    override fun getCryptoList(): List<String> = listOf()
    override fun loadCurrency(): Pair<String, String> = Pair("usd", "thb")
}