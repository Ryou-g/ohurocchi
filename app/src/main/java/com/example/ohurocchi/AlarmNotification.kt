package com.example.ohurocchi

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.text.SimpleDateFormat
import java.util.*

class AlarmNotification : BroadcastReceiver() {
    // データを受信した
    override fun onReceive(context: Context, intent: Intent) {
        val requestCode = intent.getIntExtra("RequestCode", 0)
        val pendingIntent = PendingIntent.getActivity(
            context, requestCode, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )
        val channelId = "default"
        // app name
        val title = context.getString(R.string.app_name)
        val currentTime = System.currentTimeMillis()
        val dataFormat = SimpleDateFormat("HH:mm:ss", Locale.JAPAN)
        val cTime = dataFormat.format(currentTime)

        // メッセージ　+ 11:22:331
        val message = "お風呂の時間です！ $cTime"


        // Notification　Channel 設定
        val channel = NotificationChannel(
            channelId, title,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = message
        val notificationManager =
            //                (NotificationManager)context.getSystemService(NotificationManager.class);
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ohurocchi_logo)
            .setContentTitle("おふろっち")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        val notificationManagerCompat = NotificationManagerCompat.from(context)



        // 通知
        notificationManagerCompat.notify(R.string.app_name, builder.build())
    }
}