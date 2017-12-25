package com.ripzery.cryptracker.pages.price.cryptocurrency

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.ripzery.cryptracker.db.entities.LastSeenPrice
import com.ripzery.cryptracker.repository.CryptrackerRepository
import com.ripzery.cryptracker.utils.DbHelper
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by ripzery on 10/29/17.
 */
class CryptocurrencyViewModel(private val cryptrackerRepository: CryptrackerRepository) : ViewModel() {
    private val mHandleAPIError: (Throwable) -> Unit = { error -> Log.d("Error", error.message) }
    private val mDisposableList: CompositeDisposable = CompositeDisposable()
    private lateinit var mCryptocurrency: String
    private val INTERVAL_IN_SECOND = 10L

    fun init(cryptocurrency: String) {
        this.mCryptocurrency = cryptocurrency
    }

    fun pollingPrice(cryptocurrency: String): LiveData<LastSeenPrice> {
        return DbHelper.db.lastSeen().getPriceSync(cryptocurrency)
    }

    override fun onCleared() {
        super.onCleared()
        mDisposableList.clear()
    }
}