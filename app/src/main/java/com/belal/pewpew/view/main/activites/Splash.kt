package com.belal.pewpew.view.main.activites

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.belal.pewpew.R


lateinit var handler: Handler
class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (Build.VERSION.SDK_INT >= 21) {
            window.navigationBarColor =
                this.resources.getColor(R.color.colororange) // this is for the navigation bar color of the android system
        }
        // use the handler to delay the start up of the app
        handler = Handler()
        handler.postDelayed({
            // Delay and Start Activity

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()

        } , 500) // here we're delaying to startActivity after  half seconds
    }

}
