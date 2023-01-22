package xyz.airyz.cinny.app.services.foreground.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Message
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import xyz.airyz.cinny.app.R

class NotificationFactory {
  val CHANNEL_ID = "message_received"
  val CHANNEL_NAME = "Message Received!"
  val NOTIF_ID = 10

  public fun create(context: Context){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
        lightColor = Color.BLUE
        enableLights(true)
      }
      val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
      manager.createNotificationChannel(channel)
    }
  }
  
  public fun sendNotification(context: Context, title: String, message: String){

    val intent=Intent(context, NotificationService::class.java)

    val pendingIntent = PendingIntent.getService(
      context, 0, intent, PendingIntent.FLAG_IMMUTABLE
    )

    val notif = NotificationCompat.Builder(context,CHANNEL_ID)
      .setContentTitle(title)
      .setContentText(message)
      .setSmallIcon(R.drawable.ic_launcher_foreground)
      .setPriority(NotificationCompat.PRIORITY_HIGH)
      .setContentIntent(pendingIntent)
      .setOngoing(false)
      .build()

    val notifManger = NotificationManagerCompat.from(context)
    notifManger.notify(NOTIF_ID,notif)
  }
}