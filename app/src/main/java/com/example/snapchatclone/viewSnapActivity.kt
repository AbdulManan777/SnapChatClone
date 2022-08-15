package com.example.snapchatclone

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_create_snap.*
import kotlinx.android.synthetic.main.activity_view_snap.*
import java.net.HttpURLConnection
import java.net.URL

class viewSnapActivity : AppCompatActivity() {

    private lateinit var auth1: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_snap)

        auth1 = Firebase.auth

        tvMessage.text=intent.getStringExtra("Message")

        val task=ImageDownloader()
        val imagemine:Bitmap
        try{

            imagemine=task.execute(intent.getStringExtra("ImageURL")).get()
            tvSnapImage?.setImageBitmap(imagemine)
        }
        catch(e:Exception){
            e.printStackTrace()
        }
    }


    inner class ImageDownloader: AsyncTask<String, Void, Bitmap>(){
        override fun doInBackground(vararg p0: String?): Bitmap? {
            try{

                val url=URL(p0[0])

                val connection=url.openConnection() as HttpURLConnection
                connection.connect()
                val ini=connection.inputStream
                return BitmapFactory.decodeStream(ini)

            }
            catch (e:Exception){
                e.printStackTrace()
                return null
            }
        }


    }


    override fun onBackPressed() {
        super.onBackPressed()

        FirebaseDatabase.getInstance().getReference().child(
            "Members").child(
            auth1.currentUser?.uid!!).child(
            "snaps").child(
            intent.getStringExtra(
                "SnapKey")!!).removeValue()

        FirebaseStorage.getInstance().getReference().child("images").child(intent.getStringExtra("ImageName")!!).delete()
    }
}