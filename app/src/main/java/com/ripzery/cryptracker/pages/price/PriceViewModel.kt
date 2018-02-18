package com.ripzery.cryptracker.pages.price

import android.arch.lifecycle.*
import android.os.Bundle
import android.util.Log
import com.google.firebase.crash.FirebaseCrash
import com.ripzery.cryptracker.data.CoinMarketCapResult
import com.ripzery.cryptracker.data.PairedCurrency
import com.ripzery.cryptracker.repository.CryptrackerRepository
import com.ripzery.cryptracker.services.FirestoreService
import com.ripzery.cryptracker.utils.Contextor
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by ripzery on 10/29/17.
 */
class PriceViewModel(private val cryptrackerRepository: CryptrackerRepository) : ViewModel(), LifecycleObserver {
    private val SAVED_STATE_CRYPTO_LIST = "cryptocurrency_list"
    private lateinit var mCrytoList: MutableList<String>
    private val mDisposableList: CompositeDisposable = CompositeDisposable()
    private val mCryptoListLiveData: MutableLiveData<MutableList<String>> = MutableLiveData()
    private val mAllCurrencyLiveData: MutableLiveData<MutableList<Pair<PairedCurrency, CoinMarketCapResult>>> = MutableLiveData()


    /* For handle app is killed */
    fun init(savedInstanceState: Bundle?) {
        mCrytoList = savedInstanceState?.getStringArrayList(SAVED_STATE_CRYPTO_LIST)?.toMutableList() ?: cryptrackerRepository.getCryptoList().toMutableList()
        cryptrackerRepository.loadCurrency()
    }

    fun getCryptocurrencyList(): MutableLiveData<MutableList<String>> {
        mCryptoListLiveData.value = mCrytoList
        return mCryptoListLiveData
    }

    fun polling(): MutableLiveData<MutableList<Pair<PairedCurrency, CoinMarketCapResult>>> {
        val d = cryptrackerRepository.fetchPriceAndSave(10L)?.subscribe({
            mAllCurrencyLiveData.value = it.toMutableList()
        }, {
            Log.w("Error", it.message)
            FirebaseCrash.report(it)
        })

        if (d != null)
            mDisposableList.add(d)

        return mAllCurrencyLiveData
    }

    fun refresh() {
        mCrytoList = cryptrackerRepository.getCryptoList().toMutableList()
        mCryptoListLiveData.value = mCrytoList
        cryptrackerRepository.loadCurrency()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun updatePriceOnFireStore() {
        FirestoreService.startActionSetLastSeenPrice(Contextor.context)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopPolling() {
        mDisposableList.clear()
    }
}