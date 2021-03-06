package com.ripzery.cryptracker.repository.local

import android.util.Log
import com.ripzery.cryptracker.data.CoinMarketCapResult
import com.ripzery.cryptracker.data.PairedCurrency
import com.ripzery.cryptracker.db.entities.LastSeenPrice
import com.ripzery.cryptracker.extensions.applyCurrency
import com.ripzery.cryptracker.repository.CryptrackerDataSource
import com.ripzery.cryptracker.utils.CurrencyConstants
import com.ripzery.cryptracker.utils.DbHelper
import com.ripzery.cryptracker.utils.SharePreferenceHelper
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by ripzery on 10/29/17.
 */
object CryptrackerLocalDataSource : CryptrackerDataSource {
    private var mCurrency: Pair<String, String> = Pair(CurrencyConstants.USD, CurrencyConstants.THB)

    override fun fetchPriceAndSave(intervalInSecond: Long): Observable<List<Pair<PairedCurrency, CoinMarketCapResult>>>? {
        Log.d("Local", "Hey, I can\'t do that!!")
        return null
    }
    override fun getCryptoList(): List<String> = SharePreferenceHelper.readCryptocurrencySetting().toList()
    override fun loadCurrency(): Pair<String, String> {
        mCurrency = Pair(SharePreferenceHelper.readCurrencyTop(), SharePreferenceHelper.readCurrencyBottom())
        return mCurrency
    }
}