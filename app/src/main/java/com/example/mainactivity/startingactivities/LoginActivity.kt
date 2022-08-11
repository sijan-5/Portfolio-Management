package com.example.mainactivity.startingactivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mainactivity.MainActivity
import com.example.mainactivity.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(),RegisterFragment.SlideMe {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // for sliding singup and register fragment connecting with adapter

        vpager.adapter = PageAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(vpager)

        tabLayout.translationX = 500f
        tabLayout.alpha = 0f


        tabLayout.animate().alpha(1f).translationX(0f).duration = 1000


    }

    override fun slideMethod() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        overridePendingTransition(R.anim.right_in,R.anim.left_out)
    }

    //checks if user has been already logged in or not
    override fun onStart() {
        super.onStart()

        auth = Firebase.auth
        val currentUser = auth.currentUser

        if(currentUser!=null)
        {
            slideMethod()

        }

    }


}