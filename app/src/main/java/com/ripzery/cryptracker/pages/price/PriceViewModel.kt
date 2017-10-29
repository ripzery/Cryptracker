package com.ripzery.cryptracker.pages.price

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.ripzery.cryptracker.extensions.TAG
import com.ripzery.cryptracker.network.DataSource
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by ripzery on 10/29/17.
 */
class PriceViewModel(context: Application) : AndroidViewModel(context) {
    private val mHandleAPIError: (Throwable) -> Unit = { error -> Log.d("Error", error.message) }
    private val mDisposableList: CompositeDisposable = CompositeDisposable()
    private val mLiveData: MutableLiveData<Pair<String, String>> = MutableLiveData()

    fun pollingPrice(cryptocurrency: String): MutableLiveData<Pair<String, String>> {
        val d = DataSource.getPriceForInterval(cryptocurrency, 5, mHandleAPIError) { coinMarketCap, bx ->
            mLiveData.value = Pair(coinMarketCap, bx)
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