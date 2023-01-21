package xyz.airyz.cinny.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback

class MainActivity : TauriActivity() {

    external fun myNativeMethod(input: String): String
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        Logger.debug("ASDASADSDASDSDASDASD")

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                Toast.makeText(this@MainActivity, myNativeMethod("World!"), Toast.LENGTH_SHORT).show()
            }
        })
    }
}
