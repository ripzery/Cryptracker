package com.ripzery.cryptracker.pages.price.cryptocurrency

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.ripzery.cryptracker.repository.CryptrackerRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by ripzery on 10/29/17.
 */
class CryptocurrencyViewModel(private val cryptrackerRepository: CryptrackerRepository) : ViewModel() {
    private val mHandleAPIError: (Throwable) -> Unit = { error -> Log.d("Error", error.message) }
    private val mDisposableList: CompositeDisposable = CompositeDisposable()
    private val mLiveData: MutableLiveData<Pair<String, String>> = MutableLiveData()
    private lateinit var mCryptocurrency: String

    fun init(cryptocurrency: String) {
        this.mCryptocurrency = cryptocurrency
    }

    fun pollingPrice(cryptocurrency: String): MutableLiveData<Pair<String, String>> {
        val d = cryptrackerRepository.getAllPriceInterval(cryptocurrency, 5)
                .subscribe({ mLiveData.postValue(it) }, mHandleAPIError)
        mDisposableList.add(d)
        return mLiveData
    }

    override fun onCleared() {
        super.onCleared()
        mDisposableList.clear()
    }
}