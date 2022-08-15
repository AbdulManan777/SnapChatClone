package com.example.snapchatclone

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_create_snap.*
import java.io.ByteArrayOutputStream
import java.util.*

class createSnapActivity : AppCompatActivity() {

    val imageName=UUID.randomUUID().toString()+".jpg"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_snap)
    }

    fun getPhoto(){

        val intent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent,1)
    }

    fun chooseImageClicked(view: View){

        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }
        else{
            getPhoto()
        }


    }

    fun nextClicked(view:View){

        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()



        var ref =    FirebaseStorage.getInstance().getReference().child("images").child(imageName)
       val uploadTask= ref.putBytes(data)
        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl

        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result.toString()


                val intent=Intent(this,MemberChooseActivity::class.java)
                intent.putExtra("ImageName",imageName)

                intent.putExtra("ImageURL",downloadUri)
                intent.putExtra("Message",message.text.toString())
                startActivity(intent)


            } else {

                Toast.makeText(this,"Upload Failed",Toast.LENGTH_LONG).show()

                // ...
            }
        }


            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.


            // val finalurl=downloadURL.getResult().toString()


        }





    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        super.onActivityResult(requestCode, resultCode, data)

        val selectedImage=data!!.data

        if(requestCode==1 && resultCode== Activity.RESULT_OK&&data!=null){

            try{

                val bitmap=MediaStore.Images.Media.getBitmap(this.contentResolver,selectedImage)
                imageView.setImageBitmap(bitmap)
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode==1){
            if(grantResults.size>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getPhoto()
            }
        }
    }
}