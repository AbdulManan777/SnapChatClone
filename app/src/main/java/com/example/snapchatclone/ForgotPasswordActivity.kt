package com.example.snapchatclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_main.*

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var auth1: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        auth1 = Firebase.auth

        resetButton.setOnClickListener {
            resetPassword()
        }




    }

    fun resetPassword(){

        if(resetEmail.text.toString().isEmpty()){

                resetEmail.setError("Please Enter Email First")
                resetEmail.requestFocus()
                return

            }

        if (!Patterns.EMAIL_ADDRESS.matcher(resetEmail.text).matches()) {
            resetEmail.setError("Please Enter Valid Email")
            resetEmail.requestFocus()
            return

        }

        auth1.sendPasswordResetEmail(resetEmail.text.toString()).addOnCompleteListener{

            if(it.isSuccessful){

                Toast.makeText(this,"Please Check your email for resettting password",Toast.LENGTH_LONG).show()
            }
            else{

                Toast.makeText(this,"Some Error ocurred",Toast.LENGTH_LONG).show()

            }
        }
    }
}