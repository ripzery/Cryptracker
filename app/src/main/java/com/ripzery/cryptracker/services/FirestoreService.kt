package com.ripzery.cryptracker.services

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.provider.Settings
import com.ripzery.cryptracker.utils.FirestoreHelper

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 *
 *
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
class FirestoreService : IntentService("FirestoreService") {

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            if (intent.action == "ALL")
                handleActionSetLastSeenPrice()
        }
    }

    private fun handleActionSetLastSeenPrice() {
        val deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        FirestoreHelper.addAllLastSeenPrice(deviceId)
    }

    companion object {
        fun startActionSetLastSeenPrice(context: Context) {
            val intent = Intent(context, FirestoreService::class.java)
            intent.action = "ALL"
            context.startService(intent)
        }
    }
}
