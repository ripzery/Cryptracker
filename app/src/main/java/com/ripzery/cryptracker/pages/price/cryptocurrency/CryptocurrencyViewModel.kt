package com.ripzery.cryptracker.pages.price.cryptocurrency

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.ripzery.cryptracker.db.entities.LastSeenPrice
import com.ripzery.cryptracker.repository.CryptrackerRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by ripzery on 10/29/17.
 */
class CryptocurrencyViewModel(private val cryptrackerRepository: CryptrackerRepository) : ViewModel() {
    private val mHandleAPIError: (Throwable) -> Unit = { error -> Log.d("Error", error.message) }
    private val mDisposableList: CompositeDisposable = CompositeDisposable()
    private val mLiveData: MutableLiveData<LastSeenPrice> = MutableLiveData()
    private lateinit var mCryptocurrency: String
    private val INTERVAL_IN_SECOND = 100L

    fun init(cryptocurrency: String) {
        this.mCryptocurrency = cryptocurrency
    }

    fun pollingPrice(cryptocurrency: String): MutableLiveData<LastSeenPrice> {
        val d = cryptrackerRepository.updatePriceWithInterval(cryptocurrency, INTERVAL_IN_SECOND)
                .subscribe({ mLiveData.postValue(it) }, mHandleAPIError)
        mDisposableList.add(d)
        return mLiveData
    }

    override fun onCleared() {
        super.onCleared()
        mDisposableList.clear()
    }
}