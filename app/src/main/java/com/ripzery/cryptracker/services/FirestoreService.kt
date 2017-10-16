package com.ripzery.cryptracker.services

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.provider.Settings
import com.ripzery.cryptracker.utils.Contextor
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
            val action = intent.action
            if (ACTION_SET_LAST_SEEN_PRICE == action) {
                val bxPrice = intent.getDoubleExtra(EXTRA_BX_PRICE, 0.toDouble())
                val cmcPrice = intent.getDoubleExtra(EXTRA_CMC_PRICE, 0.toDouble())
                handleActionSetLastSeenPrice(bxPrice, cmcPrice)
            }
        }
    }

    private fun handleActionSetLastSeenPrice(bxPrice: Double, cmcPrice: Double) {
        val deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        FirestoreHelper.addLastSeenOmiseGoPrice(deviceId, cmcPrice, bxPrice)
    }

    companion object {
        private val ACTION_SET_LAST_SEEN_PRICE = "com.ripzery.cryptracker.services.action.SET_LAST_SEEN_PRICE_OMG"
        private val EXTRA_BX_PRICE = "com.ripzery.cryptracker.services.extra.PARAM1"
        private val EXTRA_CMC_PRICE = "com.ripzery.cryptracker.services.extra.PARAM2"


        fun startActionSetLastSeenPriceOMG(context: Context, price: Pair<Double, Double>) {
            val intent = Intent(context, FirestoreService::class.java)
            intent.action = ACTION_SET_LAST_SEEN_PRICE
            intent.putExtra(EXTRA_CMC_PRICE, price.first)
            intent.putExtra(EXTRA_BX_PRICE, price.second)
            context.startService(intent)
        }

    }
}
