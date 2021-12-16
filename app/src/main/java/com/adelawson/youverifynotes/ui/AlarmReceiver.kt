package com.adelawson.youverifynotes.ui

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.adelawson.youverifynotes.NOTIFICATION_ID
import com.adelawson.youverifynotes.NOTIFICATION_KEY
import com.adelawson.youverifynotes.NotificationHelper

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, intent: Intent?) {
        var notificationManager: NotificationManager = p0?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(intent != null) {
            try {
                var notification: Notification? = intent.getParcelableExtra(NOTIFICATION_KEY)
                var id: Int = intent.getIntExtra(NOTIFICATION_ID, 0)
                notificationManager.notify(id, notification)
            } catch (e: Exception) {
                Log.d("Notification publisher", "Notification error" + e.message)
            }
        }
    }
}