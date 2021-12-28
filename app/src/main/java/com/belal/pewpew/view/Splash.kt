package com.belal.pewpew.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.belal.firebaseauthantication.LoginActivity
import com.belal.pewpew.R
import com.belal.pewpew.repository.ApiServicesRepository


lateinit var handler: Handler
class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        ApiServicesRepository.init(this)
        if (Build.VERSION.SDK_INT >= 21) {
            window.navigationBarColor =
                this.resources.getColor(R.color.colororange) // this is for the navigation bar color of the android system
        }
        handler = Handler()
        handler.postDelayed({
            // Delay and Start Activity

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()

        } , 500) // here we're delaying to startActivity after 3seconds
    }

}
