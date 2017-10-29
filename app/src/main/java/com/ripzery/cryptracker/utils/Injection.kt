package com.ripzery.cryptracker.utils

import android.content.Context
import com.ripzery.cryptracker.repository.CryptrackerRepository
import com.ripzery.cryptracker.repository.local.CryptrackerLocalDataSource
import com.ripzery.cryptracker.repository.remote.CryptrackerRemoteDataSource

/**
 * Created by ripzery on 10/29/17.
 */
object Injection {
    fun provideCryptrackerRepository(): CryptrackerRepository {
        return CryptrackerRepository.getInstance(CryptrackerRemoteDataSource, CryptrackerLocalDataSource)
    }
}