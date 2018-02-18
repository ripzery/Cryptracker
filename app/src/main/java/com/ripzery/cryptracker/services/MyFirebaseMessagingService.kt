package com.ripzery.cryptracker.services

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ripzery.cryptracker.R
import com.ripzery.cryptracker.extensions.to2Precision
import com.ripzery.cryptracker.pages.price.PriceActivity
import com.ripzery.cryptracker.utils.CurrencyFullnameHelper
import com.ripzery.cryptracker.utils.DbHelper
import com.ripzery.cryptracker.utils.SharePreferenceHelper

/**
 * Created by ripzery on 10/14/17.
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("MyMessagingService", message.from)

        // Do not show notification if user disable it
        if (!SharePreferenceHelper.readShowNotification()) return

        val data = message.data
        sendNotification(data)
    }

    @SuppressLint("NewApi")
    private fun sendNotification(data: Map<String, String>) {
        val icon: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher)

        val intent = Intent(this, PriceActivity::class.java).apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val currency = data["currency"]

        /* If the currency is not save in setting then do not notify!. */
        val currencyList = SharePreferenceHelper.readCryptocurrencySetting()
        if (currency != null && CurrencyFullnameHelper.shortToFull(currency) !in currencyList) {
            return
        }

        val price = data["price"]?.toDouble()
        val smallIcon = if (data["type"] == "up") R.drawable.ic_notification_trending_up else R.drawable.ic_notification_trending_down
        val colorForSmallIcon = ContextCompat.getColor(applicationContext, if (data["type"] == "up") R.color.colorPriceUp else R.color.colorPriceDown)

        val currentPriceText = when {
            currency != null && getPriceCurrency(currency.toUpperCase()) != null -> "Your last seen price is ${getPriceCurrency(currency.toUpperCase())}"
            else -> ""
        }

        // Sets an ID for the notification, so it can be updated.
        val notifyID = System.currentTimeMillis().toInt()
        // The id of the channel.
        val channelId = getString(R.string.app_name)

        val notificationBuilder = NotificationCompat.Builder(this, "Cryptracker")
                .setContentTitle(data["title"])
                .setContentText(data["body"]?.replace("<current_price>", currentPriceText))
                .setStyle(NotificationCompat.BigTextStyle().bigText(data["body"]?.replace("<current_price>", currentPriceText)))
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setLargeIcon(icon)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setColor(colorForSmallIcon)
                .setChannelId(channelId)
                .setSmallIcon(smallIcon)
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notifyID, notificationBuilder.build())

        // Update price in share preference
        writePriceCurrency(currency, price)
    }

    private fun getPriceCurrency(currency: String): String? {
        return DbHelper.db.lastSeen().getPrice(currency.toUpperCase())?.bxPrice?.to2Precision()
    }

    private fun writePriceCurrency(currency: String?, price: Double?) {
        if (price != null && currency != null) {
            DbHelper.db.lastSeen().update(currency.toUpperCase(), price)
        }
    }
}