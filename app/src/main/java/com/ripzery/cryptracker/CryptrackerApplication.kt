package com.ripzery.cryptracker

import android.app.Application
import android.support.v7.preference.PreferenceManager
import com.google.firebase.FirebaseApp
import com.ripzery.cryptracker.utils.Contextor
import com.ripzery.cryptracker.utils.SharePreferenceHelper

/**
 * Created by ripzery on 10/14/17.
 */
class CryptrackerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Contextor.context = this
        FirebaseApp.initializeApp(this)
        SharePreferenceHelper.sharePref = PreferenceManager.getDefaultSharedPreferences(this)
    }
}