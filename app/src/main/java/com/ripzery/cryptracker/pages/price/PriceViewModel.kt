package com.ripzery.cryptracker.pages.price

import android.arch.lifecycle.*
import android.os.Bundle
import com.ripzery.cryptracker.repository.CryptrackerRepository
import com.ripzery.cryptracker.repository.remote.CryptrackerRemoteDataSource
import com.ripzery.cryptracker.services.FirestoreService
import com.ripzery.cryptracker.utils.Contextor
import com.ripzery.cryptracker.utils.CurrencyContants
import com.ripzery.cryptracker.utils.SharePreferenceHelper

/**
 * Created by ripzery on 10/29/17.
 */
class PriceViewModel(private val cryptrackerRepository: CryptrackerRepository) : ViewModel(), LifecycleObserver {
    private val SAVED_STATE_CRYPTO_LIST = "cryptocurrency_list"
    private lateinit var mCrytoList: MutableList<String>
    private val mCryptoListLiveData: MutableLiveData<MutableList<String>> = MutableLiveData()

    /* For handle app is killed */
    fun init(savedInstanceState: Bundle?) {
        mCrytoList = savedInstanceState?.getStringArrayList(SAVED_STATE_CRYPTO_LIST)?.toMutableList() ?: cryptrackerRepository.getCryptoList().toMutableList()
        cryptrackerRepository.loadCurrency()
    }

    fun getCryptocurrencyList(): MutableLiveData<MutableList<String>> {
        mCryptoListLiveData.value = mCrytoList
        return mCryptoListLiveData
    }

    fun refresh() {
        mCrytoList = cryptrackerRepository.getCryptoList().toMutableList()
        mCryptoListLiveData.value = mCrytoList
        cryptrackerRepository.loadCurrency()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun updatePriceOnFireStore() {

    }
}