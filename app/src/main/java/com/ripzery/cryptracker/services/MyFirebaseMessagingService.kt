package com.ripzery.cryptracker.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ripzery.cryptracker.R
import com.ripzery.cryptracker.pages.PriceActivity

/**
 * Created by ripzery on 10/14/17.
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("MyMessagingService", message.from)
//        val notification = message.notification
        val data = message.data
        sendNotification(data)
    }

    private fun sendNotification(data: Map<String, String>) {
        val icon: Bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)

        val intent = Intent(this, PriceActivity::class.java).apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val smallIcon = if (data["type"] == "up") R.drawable.ic_notification_trending_up else R.drawable.ic_notification_trending_down
        val colorForSmallIcon = getColor(if (data["type"] == "up") R.color.colorPriceUp else R.color.colorPriceDown)

        val notificationBuilder = NotificationCompat.Builder(this, "Cryptracker")
                .setContentTitle(data["title"])
                .setContentText(data["body"])
                .setStyle(NotificationCompat.BigTextStyle().bigText(data["title"]))
                .setStyle(NotificationCompat.BigTextStyle().bigText(data["body"]))
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setLargeIcon(icon)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setColor(colorForSmallIcon)
                .setSmallIcon(smallIcon)
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }
}