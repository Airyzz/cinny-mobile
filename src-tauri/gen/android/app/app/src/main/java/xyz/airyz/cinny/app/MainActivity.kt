package xyz.airyz.cinny.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback

class MainActivity : TauriActivity() {

    external fun myNativeMethod(input: String): String
    external fun myOtherNativeMethod()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        Logger.debug("ASDASADSDASDSDASDASD")

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                myOtherNativeMethod()
                //Toast.makeText(this@MainActivity, myNativeMethod("World!"), Toast.LENGTH_SHORT).show()
            }
        })
    }

    public fun callMeFromRust() {
        Toast.makeText(this@MainActivity, "Called from rust!", Toast.LENGTH_SHORT).show()
    }
}
