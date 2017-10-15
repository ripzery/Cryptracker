package com.ripzery.cryptracker

import android.app.Application
import com.google.firebase.FirebaseApp
import com.ripzery.cryptracker.utils.Contextor

/**
 * Created by ripzery on 10/14/17.
 */
class CryptrackerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Contextor.context = this
        FirebaseApp.initializeApp(this)
    }
}