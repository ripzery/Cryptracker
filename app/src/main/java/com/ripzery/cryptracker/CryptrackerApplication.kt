package com.ripzery.cryptracker

import android.app.Application
import android.util.Log
import android.util.Log.d
import com.google.firebase.FirebaseApp
import com.ripzery.cryptracker.utils.Contextor

/**
 * Created by ripzery on 10/14/17.
 */
class CryptrackerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Contextor.context = this
        Log.d("Application", "Initialize")
        FirebaseApp.initializeApp(this)
    }
}