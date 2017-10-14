package com.ripzery.cryptracker.services

import android.provider.Settings
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.ripzery.cryptracker.utils.FirestoreHelper

/**
 * Created by ripzery on 10/14/17.
 */
class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {
    override fun onTokenRefresh() {
        val refreshedToken = FirebaseInstanceId.getInstance().token!!
        Log.d("MyInstanceIdService", refreshedToken)
        sendRegistrationToServer(refreshedToken)
    }

    fun sendRegistrationToServer(token: String) {
        val deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        FirestoreHelper.updateRefreshedToken(deviceId, token)
    }
}