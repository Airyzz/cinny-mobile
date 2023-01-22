package xyz.airyz.cinny.app.services.foreground.notifications;
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.*
import xyz.airyz.cinny.app.R
import java.util.*
import java.util.concurrent.TimeUnit

class NotificationService : Service() {
  private val CHANNEL_ID = "my_service_channel"


  private val job = Job()
  private val scope = CoroutineScope(Dispatchers.IO + job)
  private val notificationFactory = NotificationFactory()
  
  @RequiresApi(Build.VERSION_CODES.O)
  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    val notificationIntent = Intent(this, NotificationService::class.java)
    notificationIntent.action = "showToast"
    val pendingIntent = PendingIntent.getService(
      this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
    )

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val channel = NotificationChannel(
        CHANNEL_ID, "Notification Service", NotificationManager.IMPORTANCE_DEFAULT
      )
      val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
      manager.createNotificationChannel(channel)
    }

    val notification = Notification.Builder(this, CHANNEL_ID)
      .setContentTitle("My Service")
      .setContentText("Running in the background")
      .setSmallIcon(R.drawable.ic_launcher_foreground)
      .setContentIntent(pendingIntent)
      .setOngoing(true)
      .build()

    startForeground(1, notification)
    startPolling()

    return super.onStartCommand(intent, flags, startId)
  }

  override fun onStart(intent: Intent?, startId: Int) {
    super.onStart(intent, startId)
    if (intent?.action == "showToast") {
      Toast.makeText(this, "Notification Tapped", Toast.LENGTH_SHORT).show()
    }
    
    notificationFactory.create(this)
    notificationFactory.sendNotification(this, "Test", "Test Text")
  }

  private fun startPolling() {
    scope.launch {
      while (true) {
        Log.d("MyForegroundService", "Response: ASDASDLA")
        notificationFactory.sendNotification(applicationContext, "Test", "Test Text")
        delay(TimeUnit.MINUTES.toMillis(1))
      }
    }
  }

  override fun onBind(intent: Intent?): IBinder? {
    return null
  }
}
