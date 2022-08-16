package com.example.snapchatclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth1: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth1 = Firebase.auth


        registerButton.setOnClickListener {


            signIn()




        }


    }

    fun signIn(){



        if (tvName.text.toString().isEmpty()) {

            tvName.setError("Please Enter full Name")
            tvName.requestFocus()
            return


        }
        if (tvregisterEMail.text.toString().isEmpty()) {

            tvregisterEMail.setError("Please Enter Email")
            tvregisterEMail.requestFocus()
            return


        }

        if (tvRegisterPassword.text.toString().isEmpty()) {

            tvRegisterPassword.setError("Please Enter Password")
            tvRegisterPassword.requestFocus()
            return

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(tvregisterEMail.text).matches()) {
            tvregisterEMail.setError("Please Enter Valid Email")
            tvregisterEMail.requestFocus()
            return

        }

        auth1.createUserWithEmailAndPassword(tvregisterEMail.text.toString(), tvRegisterPassword.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {



                        val database = Firebase.database
                        val myRef =
                            database.getReference().child("Members").child(task.result.user!!.uid)
                                .child("Email").setValue(tvregisterEMail.text.toString()).addOnCompleteListener{
                                    if(it.isSuccessful){

                                        Toast.makeText(this,"Succesfully Registered",Toast.LENGTH_LONG).show()
                                    }
                                    else{

                                        Toast.makeText(this, "Registration Failed 1", Toast.LENGTH_LONG).show()

                                    }
                                }





                    } else {
                                Toast.makeText(this, "Registration Failed 2", Toast.LENGTH_LONG).show()
                            }
                        // Sign in success, update UI with the signed-in user's information

                    }

                    }



}



