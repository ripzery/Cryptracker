package com.ripzery.cryptracker.repository.remote

import com.ripzery.cryptracker.data.BxPrice
import com.ripzery.cryptracker.data.CoinMarketCapResult
import com.ripzery.cryptracker.db.entities.LastSeenPrice
import com.ripzery.cryptracker.extensions.to2Precision
import com.ripzery.cryptracker.network.NetworkProvider
import com.ripzery.cryptracker.repository.CryptrackerDataSource
import com.ripzery.cryptracker.utils.DbHelper
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.*

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

    override fun updatePriceWithInterval(cryptoCurrency: String, intervalInSecond: Long): Observable<LastSeenPrice> {
        val getAllPrice = Observable.zip(getCmcPrice(cryptoCurrency), getBxPrice(),
                BiFunction<List<CoinMarketCapResult>, BxPrice, LastSeenPrice> { cmc, bx ->
                    val cmcPrice = cmc[0].price.toDouble()
                    val lastSeenPrice = when (cryptoCurrency) {
                        "everex" -> {
                            LastSeenPrice(bx.evx.pairingId, bx.evx.lastPrice.to2Precision().toDouble(), cmcPrice.to2Precision().toDouble(), Date())
                        }
                        "omisego" -> {
                            LastSeenPrice(bx.omg.pairingId, bx.omg.lastPrice.to2Precision().toDouble(), cmcPrice.to2Precision().toDouble(), Date())
                        }
                        "ethereum" -> {
                            LastSeenPrice(bx.eth.pairingId, bx.eth.lastPrice.to2Precision().toDouble(), cmcPrice.to2Precision().toDouble(), Date())
                        }
                        "bitcoin" -> {
                            LastSeenPrice(bx.btc.pairingId, bx.btc.lastPrice.to2Precision().toDouble(), cmcPrice.to2Precision().toDouble(), Date())
                        }
                        else -> LastSeenPrice()
                    }
                    DbHelper.db.lastSeen().insert(lastSeenPrice)
                    lastSeenPrice
                })

        return getAllPrice
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
    }

    override fun getCryptoList(): List<String> = listOf()
    override fun loadCurrency(): Pair<String, String> = Pair("usd", "thb")
}