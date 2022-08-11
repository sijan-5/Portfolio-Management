package com.example.mainactivity.startingactivities

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mainactivity.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment() {



    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_register, container, false)
         return  view



    }

    //check to see if the register field are not empty and if not register the user

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        registerButton.setOnClickListener {
            when {

                TextUtils.isEmpty(register_email.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(activity, "Please enter email address", Toast.LENGTH_LONG).show()
                }

                TextUtils.isEmpty(register_password.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(activity, "Please enter password", Toast.LENGTH_LONG).show()
                }

                TextUtils.isEmpty(username.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(activity, "Please enter your UserName", Toast.LENGTH_LONG).show()
                }

                TextUtils.isEmpty(confirm_password.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(activity, "Please confirm your password", Toast.LENGTH_LONG)
                        .show()
                }

                else -> {

                    firebaseAuth = Firebase.auth
                    val userName = username.text.toString().trim()
                    val registerEmail = register_email.text.toString().trim()
                    val registerPassword = register_password.text.toString().trim()
                    val confirmPassword = confirm_password.text.toString().trim()

                    if(registerPassword == confirmPassword) {


                        firebaseAuth.createUserWithEmailAndPassword(registerEmail, registerPassword)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("Message", "createUserWithEmail:success")

                                    (activity as LoginActivity).slideMethod()

                                    Toast.makeText(activity,
                                        "Signed In Successfully",
                                        Toast.LENGTH_LONG)
                                        .show()
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("Wrong", "createUserWithEmail:failure", task.exception)
                                    Toast.makeText(activity, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                    else
                    {
                        Toast.makeText(activity,"Please enter the same password",Toast.LENGTH_LONG).show()
                    }
                }
            }

        }


    }



    interface SlideMe {
        fun slideMethod()
    }

     fun animateRegister() {

        register_email.translationX = 500f
        register_password.translationX = 500f
        username.translationX = 500f
        confirm_password.translationX = 500f


        register_email.alpha = 0f
        register_password.alpha = 0f
        username.alpha = 0f
        confirm_password.alpha = 0f



        register_email.animate().alpha(1f).translationX(0f).setDuration(800).startDelay=3000
        register_password.animate().alpha(1f).translationX(0f).setDuration(800).startDelay=4500
        username.animate().alpha(1f).translationX(0f).setDuration(800).startDelay=4000
        confirm_password.animate().alpha(1f).translationX(0f).setDuration(800).startDelay=4500

    }


}