package xyz.airyz.cinny.app

import android.Manifest
import android.app.*
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import xyz.airyz.cinny.app.services.foreground.notifications.NotificationService
import java.util.*

class MainActivity : TauriActivity() {

    external fun myNativeMethod(input: String): String
    external fun myOtherNativeMethod()
    
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                myOtherNativeMethod()
                //Toast.makeText(this@MainActivity, myNativeMethod("World!"), Toast.LENGTH_SHORT).show()
            }
        })
      
      if (ContextCompat.checkSelfPermission(this,
          Manifest.permission.FOREGROUND_SERVICE) != PackageManager.PERMISSION_GRANTED) {
        Toast.makeText(this@MainActivity, "Permission to start foreground service denied", Toast.LENGTH_SHORT).show()
      } else {
        //Toast.makeText(this@MainActivity, "Starting foreground service!", Toast.LENGTH_SHORT).show()
        //val startServiceIntent = Intent(this, NotificationService::class.java)
        //startService(startServiceIntent)
      }
    }

    public fun callMeFromRust() {
        Toast.makeText(this@MainActivity, "Called from rust!", Toast.LENGTH_SHORT).show()
    }
}
