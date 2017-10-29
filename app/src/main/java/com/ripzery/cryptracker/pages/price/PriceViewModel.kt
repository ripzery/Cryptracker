package com.ripzery.cryptracker.pages.price

import android.arch.lifecycle.*
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
    private val mCryptocurrencyList: MutableList<String> = SharePreferenceHelper.readCryptocurrencySetting().toMutableList()
    private val mLiveData: MutableLiveData<MutableList<String>> = MutableLiveData()
    fun getCryptocurrencyList(): MutableLiveData<MutableList<String>> {
        mLiveData.value = mCryptocurrencyList
        return mLiveData
    }

    fun refreshCryptocurrencyList() {
        mLiveData.value = SharePreferenceHelper.readCryptocurrencySetting().toMutableList()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("PriceViewModel", "cleared!")
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