package com.ripzery.cryptracker

import android.annotation.TargetApi
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.content.ContextCompat
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

        createNotificationChannel()
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        // The user-visible name of the channel.
        val channelName = getString(R.string.notification_channel_name)
        // The user-visible description of the channel.
        val channelDescription = getString(R.string.notification_channel_description)
        val channelId = getString(R.string.app_name)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            mChannel.description = channelDescription
            mChannel.enableLights(true)
            mChannel.lightColor = ContextCompat.getColor(this, R.color.colorBxBackground)
            notificationManager.createNotificationChannel(mChannel)
        }
    }
}