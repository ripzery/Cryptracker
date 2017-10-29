package com.ripzery.cryptracker.pages.price.cryptocurrency

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.ripzery.cryptracker.extensions.TAG
import com.ripzery.cryptracker.extensions.to2Precision
import com.ripzery.cryptracker.network.DataSource
import com.ripzery.cryptracker.repository.CryptrackerRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by ripzery on 10/29/17.
 */
class CryptocurrencyViewModel(private val cryptrackerRepository: CryptrackerRepository? = null) : ViewModel() {
    private val mHandleAPIError: (Throwable) -> Unit = { error -> Log.d("Error", error.message) }
    private val mDisposableList: CompositeDisposable = CompositeDisposable()
    private val mLiveData: MutableLiveData<Pair<String, String>> = MutableLiveData()
    private val USD_TO_THB = 33.23
    private val THB_TO_USD = 0.03
    private lateinit var mCurrencyTop: String
    private lateinit var mCurrencyButtom: String
    private lateinit var mCryptocurrency: String

    fun init(cryptocurrency: String, currencyTop: String, currencyBottom: String) {
        this.mCryptocurrency = cryptocurrency
        this.mCurrencyTop = currencyTop
        this.mCurrencyButtom = currencyBottom
    }

    fun pollingPrice(cryptocurrency: String): MutableLiveData<Pair<String, String>> {
        val d = DataSource.getPriceForInterval(cryptocurrency, 5, mHandleAPIError) { coinMarketCap, bx ->
            val price: Pair<String, String> = when {
                mCurrencyTop == "usd" && mCurrencyButtom == "thb" -> Pair(coinMarketCap, bx)
                mCurrencyTop == "usd" && mCurrencyButtom == "usd" -> Pair(coinMarketCap, (bx.toFloat() * THB_TO_USD).to2Precision())
                mCurrencyTop == "thb" && mCurrencyButtom == "thb" -> Pair((coinMarketCap.toFloat() * USD_TO_THB).to2Precision(), bx)
                mCurrencyTop == "thb" && mCurrencyButtom == "usd" -> Pair((coinMarketCap.toFloat() * USD_TO_THB).to2Precision(), (bx.toFloat() * THB_TO_USD).to2Precision())
                else -> throw UnsupportedOperationException("Unsupported currency!")
            }
            mLiveData.value = price
        }
        mDisposableList.add(d)
        return mLiveData
    }

    override fun onCleared() {
        super.onCleared()
        mDisposableList.clear()
        Log.d(TAG, "Disposable cleared!")
    }
}