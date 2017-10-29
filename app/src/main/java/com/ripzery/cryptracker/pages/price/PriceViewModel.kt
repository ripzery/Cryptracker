package com.ripzery.cryptracker.pages.price

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ripzery.cryptracker.utils.SharePreferenceHelper

/**
 * Created by ripzery on 10/29/17.
 */
class PriceViewModel : ViewModel() {
    private val mCryptocurrencyList: MutableList<String> = SharePreferenceHelper.readCryptocurrencySetting().toMutableList()
    private val mLiveData: MutableLiveData<MutableList<String>> = MutableLiveData()
    fun getCryptocurrencyList(): MutableLiveData<MutableList<String>> {
        mLiveData.value = mCryptocurrencyList
        return mLiveData
    }

    fun refreshCryptocurrencyList() {
        mLiveData.value = SharePreferenceHelper.readCryptocurrencySetting().toMutableList()
    }
}