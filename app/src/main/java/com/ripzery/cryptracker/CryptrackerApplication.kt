package com.ripzery.cryptracker

import android.app.Application
import android.content.Context
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
        SharePreferenceHelper.sharePref = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
    }
}