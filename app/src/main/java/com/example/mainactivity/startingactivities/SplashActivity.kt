package com.example.mainactivity.startingactivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.mainactivity.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // starting  activity

        val handler = Handler()
        handler.postDelayed({
           startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        },2000)



    }
}