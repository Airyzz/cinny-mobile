package xyz.airyz.cinny.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent) {
    Toast.makeText(context, "Alarm is going off!!!!", Toast.LENGTH_SHORT).show()
  }
}