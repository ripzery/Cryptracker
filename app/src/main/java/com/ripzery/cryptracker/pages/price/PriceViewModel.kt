package com.ripzery.cryptracker.pages.price

import android.arch.lifecycle.*
import android.os.Bundle
import android.util.Log
import com.ripzery.cryptracker.network.DataSource
import com.ripzery.cryptracker.services.FirestoreService
import com.ripzery.cryptracker.utils.Contextor
import com.ripzery.cryptracker.utils.CurrencyContants
import com.ripzery.cryptracker.utils.SharePreferenceHelper

/**
 * Created by ripzery on 10/29/17.
 */
class PriceViewModel : ViewModel(), LifecycleObserver {
    private val SAVED_STATE_CRYPTO_LIST = "cryptocurrency_list"
    private lateinit var mCrytoList: MutableList<String>
    private lateinit var mCurrency: Pair<String, String>
    private val mCryptoListLiveData: MutableLiveData<MutableList<String>> = MutableLiveData()
    private val mCurrencyLiveData: MutableLiveData<Pair<String, String>> = MutableLiveData()

    /* For handle app is killed */
    fun init(savedInstanceState: Bundle?) {
        mCrytoList = savedInstanceState?.getStringArrayList(SAVED_STATE_CRYPTO_LIST)?.toMutableList() ?: SharePreferenceHelper.readCryptocurrencySetting().toMutableList()
        mCurrency = Pair(SharePreferenceHelper.readCurrencyTop(), SharePreferenceHelper.readCurrencyBottom())
    }

    fun getCryptocurrencyList(): MutableLiveData<MutableList<String>> {
        mCryptoListLiveData.value = mCrytoList
        return mCryptoListLiveData
    }

    fun getCurrencyLiveData(): MutableLiveData<Pair<String, String>> {
        mCurrencyLiveData.value = mCurrency
        return mCurrencyLiveData
    }

    fun refresh() {
        mCrytoList = SharePreferenceHelper.readCryptocurrencySetting().toMutableList()
        mCurrency = Pair(SharePreferenceHelper.readCurrencyTop(), SharePreferenceHelper.readCurrencyBottom())
        mCryptoListLiveData.value = mCrytoList
        mCurrencyLiveData.value = mCurrency
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun updatePriceOnFireStore() {
        if (DataSource.lastPriceOmiseGo != null) {
            FirestoreService.startActionSetLastSeenPriceOMG(Contextor.context, DataSource.lastPriceOmiseGo!!)
            SharePreferenceHelper.writeDouble(CurrencyContants.OMG, DataSource.lastPriceOmiseGo!!.second)
        }

        if (DataSource.lastPriceEvx != null) {
            FirestoreService.startActionSetLastSeenPriceEVX(Contextor.context, DataSource.lastPriceEvx!!)
            SharePreferenceHelper.writeDouble(CurrencyContants.EVX, DataSource.lastPriceEvx!!.second)
        }
    }
}