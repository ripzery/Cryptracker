package com.ripzery.cryptracker.services

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.provider.Settings
import com.ripzery.cryptracker.utils.CurrencyContants
import com.ripzery.cryptracker.utils.DbHelper
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
//            val pricePair = getPriceCurrencyPair(intent)
            if(intent.action == "ALL")
                handleActionSetLastSeenPrice()
        }
    }

//    private fun getPriceCurrencyPair(intent: Intent): Pair<Double, Double> {
//        val pair: Pair<Double, Double> =
//                when (intent.action) {
//                    CurrencyContants.OMG -> {
//                        Pair(intent.getDoubleExtra(CurrencyContants.OMG_BX, 0.toDouble()), intent.getDoubleExtra(CurrencyContants.OMG_CMC, 0.toDouble()))
//                    }
//                    CurrencyContants.EVX -> {
//                        Pair(intent.getDoubleExtra(CurrencyContants.EVX_BX, 0.toDouble()), intent.getDoubleExtra(CurrencyContants.EVX_CMC, 0.toDouble()))
//                    }
//                    else -> Pair(0.toDouble(), 0.toDouble())
//                }
//        return pair
//    }

    private fun handleActionSetLastSeenPrice() {
        val deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        FirestoreHelper.addAllLastSeenPrice(deviceId)
    }

    companion object {
        fun startActionSetLastSeenPriceOMG(context: Context, price: Pair<Double, Double>) {
            val intent = Intent(context, FirestoreService::class.java)
            intent.action = CurrencyContants.OMG
            intent.putExtra(CurrencyContants.OMG_CMC, price.first)
            intent.putExtra(CurrencyContants.OMG_BX, price.second)
            context.startService(intent)
        }

        fun startActionSetLastSeenPriceEVX(context: Context, price: Pair<Double, Double>) {
            val intent = Intent(context, FirestoreService::class.java)
            intent.action = CurrencyContants.EVX
            intent.putExtra(CurrencyContants.EVX_CMC, price.first)
            intent.putExtra(CurrencyContants.EVX_BX, price.second)
            context.startService(intent)
        }

        fun startActionSetLastSeenPrice(context: Context){
            val intent = Intent(context, FirestoreService::class.java)
            intent.action = "ALL"
            context.startService(intent)
        }
    }
}
