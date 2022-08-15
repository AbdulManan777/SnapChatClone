package com.example.snapchatclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth1: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth1 = Firebase.auth

        if(auth1.currentUser!=null){

            logIn()

        }
    }

    fun signIn(view: View){

        if(Email.text.toString()==null){
            Toast.makeText(this,"Please enter the email first to continue",Toast.LENGTH_LONG).show()
        }


        else {


            auth1.signInWithEmailAndPassword(Email.text.toString(), Password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        logIn()
                    } else {


                        // If sign in fails, display a message to the user.
                        // Sign up the new user
                        auth1.createUserWithEmailAndPassword(
                            Email.text.toString(),
                            Password.text.toString()
                        ).addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val database = Firebase.database
                                val myRef = database.getReference().child("Members").child(task.result.user!!.uid).child("Email").setValue(Email.text.toString())



                                logIn()
                            } else {
                                Toast.makeText(this, "LogIn Failed", Toast.LENGTH_LONG).show()
                            }
                        }

                    }
                }
        }
    }
    fun logIn(){

        val intent= Intent(this,SnapsActivity::class.java)
        startActivity(intent)


    }
}