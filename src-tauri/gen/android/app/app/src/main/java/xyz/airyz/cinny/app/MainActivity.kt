package xyz.airyz.cinny.app

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle

class MainActivity : TauriActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Hello, World!")
    }
}
