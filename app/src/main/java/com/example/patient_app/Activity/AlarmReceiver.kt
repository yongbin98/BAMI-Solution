package com.example.patient_app.Activity

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat


class AlarmReceiver : BroadcastReceiver() {
    private lateinit var context: Context
    private val channelId = "alarm_channel"

    override fun onReceive(context: Context, intent: Intent?) {
        this.context = context
        val busRouteIntent = Intent(context, MainActivity::class.java)

        val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addNextIntentWithParentStack(busRouteIntent)

        val busRoutePendingIntent: PendingIntent =
            stackBuilder.getPendingIntent(1, PendingIntent.FLAG_IMMUTABLE)

        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, channelId)
                .setSmallIcon(com.example.patient_app.R.mipmap.bami_icon_round).setDefaults(Notification.DEFAULT_ALL)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true)
                .setContentTitle("BAMI Solution")
                .setContentText("오늘의 설문을 진행해 주세요!")
                .setContentIntent(busRoutePendingIntent)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val id = System.currentTimeMillis().toInt()
        notificationManager.notify(id, notificationBuilder.build())
    }

}