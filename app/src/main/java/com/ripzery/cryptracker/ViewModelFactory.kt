package com.ripzery.cryptracker

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.ripzery.cryptracker.pages.price.PriceViewModel
import com.ripzery.cryptracker.pages.price.cryptocurrency.CryptocurrencyViewModel
import com.ripzery.cryptracker.repository.CryptrackerRepository
import com.ripzery.cryptracker.utils.Injection

/**
 * Created by ripzery on 10/29/17.
 */
class ViewModelFactory private constructor(
        private val cryptrackerRepository: CryptrackerRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(CryptocurrencyViewModel::class.java) -> CryptocurrencyViewModel(cryptrackerRepository)
                    isAssignableFrom(PriceViewModel::class.java) -> PriceViewModel()
                    else -> throw IllegalStateException("Unknown ViewModel class ${modelClass.name}")
                }
            } as T

    companion object {
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance() = INSTANCE ?: synchronized(ViewModelFactory::class.java) {
            INSTANCE ?: ViewModelFactory(Injection.provideCryptrackerRepository()).also { INSTANCE = it }
        }
    }
}