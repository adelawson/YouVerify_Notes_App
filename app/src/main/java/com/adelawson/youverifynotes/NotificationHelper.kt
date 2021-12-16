package com.adelawson.youverifynotes

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.provider.Settings.Global.getString
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.adelawson.youverifynotes.data.localSource.Task
import com.adelawson.youverifynotes.ui.AlarmReceiver


const val TASK_NOTIF_CHANNEL = "task_notif_channel"
const val TASK_NOTIF_DESC = "task notification channel"
const val NOTIFICATION_ID = "notif_id"
const val NOTIFICATION_KEY =  "notif_key"

class NotificationHelper {

    fun createTaskNotification(task:Task, time:Long, context: Context){

        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(NOTIFICATION_ID,1)
        intent.putExtra(NOTIFICATION_KEY, createNotification(task.taskName, task.taskDescription, context))


        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val futureInMillis = System.currentTimeMillis() + time
        val alarmManager = context.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent)
        }


    fun createNotification(title:String, description:String, context: Context,
                           channelID: String = TASK_NOTIF_CHANNEL ):Notification{

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelID).apply {
            setSmallIcon(R.drawable.alarm_ic)
            .setContentTitle(title)
            .setContentText(description)
            .setAutoCancel(true).setStyle(NotificationCompat.BigTextStyle().bigText(description))
            .setSound(alarmSound)
            .setDefaults(NotificationCompat.DEFAULT_SOUND)
            priority = NotificationCompat.PRIORITY_HIGH
        }

        return builder.build()
    }


     fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "youverify"
            val descriptionText = TASK_NOTIF_CHANNEL
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(TASK_NOTIF_CHANNEL, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }}
}