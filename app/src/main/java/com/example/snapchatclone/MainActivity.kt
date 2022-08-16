package com.example.snapchatclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth1: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth1 = Firebase.auth

        buttonLogin.setOnClickListener {

            checkLogin()



        }

        tvForgot.setOnClickListener {
            val intent=Intent(this,ForgotPasswordActivity::class.java)
            startActivity(intent)
        }



        tvRegister.setOnClickListener{

            val intent=Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }


    }

    fun checkLogin(){






        if (Email.text.toString().isEmpty()) {

            Email.setError("Please Enter Email")
            Email.requestFocus()
            return


        }

        if (Password.text.toString().isEmpty()) {

            Password.setError("Please Enter Password")
            Password.requestFocus()
            return

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Email.text).matches()) {
            Email.setError("Please Enter Valid Email")
            Email.requestFocus()
            return

        }


        auth1.signInWithEmailAndPassword(Email.text.toString(),Password.text.toString()).addOnCompleteListener{

            if(it.isSuccessful){

                logIn()
            }
            else{

                Toast.makeText(this,"Not a valid Member",Toast.LENGTH_LONG).show()


            }
        }

    }



    fun logIn(){

        val intent= Intent(this,SnapsActivity::class.java)
        startActivity(intent)


    }
}