package com.example.mainactivity

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mainactivity.startingactivities.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_loginfragment.*

class LoginFragment : Fragment() {


    private lateinit var firebaseAuth : FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loginfragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // animation for the login field

        inputemail?.translationX = 500f
        inputPassword?.translationX = 500f
        forgotPassoword?.translationX = 500f
        LoginButton?.translationX = 500f

        forgotPassoword?.alpha = 0f
        LoginButton?.alpha = 0f
        inputPassword?.alpha = 0f
        inputemail?.alpha = 0f

        inputemail.animate().alpha(1f).translationX(0f).setDuration(800).startDelay = 300
        inputPassword.animate().alpha(1f).translationX(0f).setDuration(800).startDelay = 500
        forgotPassoword.animate().alpha(1f).translationX(0f).setDuration(800).startDelay = 500
        LoginButton.animate().alpha(1f).translationX(0f).setDuration(800).startDelay = 700

        //check if the login field are not empty if not then signup the user

        LoginButton.setOnClickListener{



            when
            {

                TextUtils.isEmpty(inputemail.text.toString().trim { it<=' '}) ->
                {
                    Toast.makeText(activity,"Please enter the Email", Toast.LENGTH_LONG).show()
                    inputemail.error = "Email is required"
                    inputemail.requestFocus()

                }
                TextUtils.isEmpty(inputPassword.text.toString().trim { it<=' '}) ->
                {
                    Toast.makeText(activity,"Please enter the Password", Toast.LENGTH_LONG).show()

                    inputPassword.error = "Password is required"
                    inputPassword.requestFocus()

                }

                else ->
                {
                    val getEmail = inputemail.text.toString().trim()
                    val getPassword = inputPassword.text.toString().trim()

                    firebaseAuth = Firebase.auth

                    firebaseAuth.signInWithEmailAndPassword(getEmail, getPassword)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("LMessage", "signInWithEmail:success")

                                (activity as LoginActivity).slideMethod()
                                Toast.makeText(activity, "Login Successfully",
                                    Toast.LENGTH_SHORT).show()


                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("LWrong", "signInWithEmail:failure", task.exception)
                                Toast.makeText(activity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }


                }
            }
        }
    }




}