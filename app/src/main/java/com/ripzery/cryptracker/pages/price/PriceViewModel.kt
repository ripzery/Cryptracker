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
    private val mCryptoListLiveData: MutableLiveData<MutableList<String>> = MutableLiveData()
    private val mCurrencyLiveData: MutableLiveData<Pair<String, String>> = MutableLiveData()
    fun getCryptocurrencyList(): MutableLiveData<MutableList<String>> {
        mCryptoListLiveData.value = SharePreferenceHelper.readCryptocurrencySetting().toMutableList()
        return mCryptoListLiveData
    }

    fun getCurrencyLiveData(): MutableLiveData<Pair<String, String>> {
        mCurrencyLiveData.value = Pair(SharePreferenceHelper.readCurrencyTop(), SharePreferenceHelper.readCurrencyBottom())
        return mCurrencyLiveData
    }

    fun refresh() {
        mCryptoListLiveData.value = SharePreferenceHelper.readCryptocurrencySetting().toMutableList()
        mCurrencyLiveData.value = Pair(SharePreferenceHelper.readCurrencyTop(), SharePreferenceHelper.readCurrencyBottom())
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