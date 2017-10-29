package com.ripzery.cryptracker.pages.price

import android.arch.lifecycle.*
import com.ripzery.cryptracker.network.DataSource
import com.ripzery.cryptracker.services.FirestoreService
import com.ripzery.cryptracker.utils.Contextor
import com.ripzery.cryptracker.utils.CurrencyContants
import com.ripzery.cryptracker.utils.SharePreferenceHelper

/**
 * Created by ripzery on 10/29/17.
 */
class PriceViewModel : ViewModel(), LifecycleObserver {
    private var mCrytoList = SharePreferenceHelper.readCryptocurrencySetting().toMutableList()
    private var mCurrency = Pair(SharePreferenceHelper.readCurrencyTop(), SharePreferenceHelper.readCurrencyBottom())
    private val mCryptoListLiveData: MutableLiveData<MutableList<String>> = MutableLiveData()
    private val mCurrencyLiveData: MutableLiveData<Pair<String, String>> = MutableLiveData()
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