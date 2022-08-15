package com.example.snapchatclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_snaps.*

class SnapsActivity : AppCompatActivity() {

    private lateinit var auth1: FirebaseAuth
    var snapAdapter:SnapsAdapter ?=null
    var emailsList2:ArrayList<String> =ArrayList()

    var snaps:ArrayList<DataSnapshot> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snaps)

        auth1 = Firebase.auth

        recycle_Snaps.layoutManager=LinearLayoutManager(this)
        snapAdapter= SnapsAdapter(emailsList2,this)
        recycle_Snaps.adapter=snapAdapter

        FirebaseDatabase.getInstance().getReference().child("Members").child(auth1.currentUser!!.uid).child("snaps").addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {


                emailsList2.add(snapshot.child("from").value as String)
                snaps.add(snapshot!!)
                snapAdapter!!.notifyDataSetChanged()

                snapAdded()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {
                var index:Int=0
                for(i:DataSnapshot in snaps){

                    if(i.key==snapshot.key){
                        snaps.removeAt(index)
                        emailsList2.removeAt(index)
                    }
                    index++
                }
                snapAdapter!!.notifyDataSetChanged()
                tvSnaps.visibility= View.GONE
                recycle_Snaps.visibility= View.GONE
                NoSnaps.visibility= View.VISIBLE


            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}

        })

        snapAdapter!!.setOnItemClickLisetner2(object : SnapsAdapter.onItemClickListner2{
            override fun onItemClick2(Po: Int) {

                val snapshot=snaps.get(Po)


                val intent=Intent(this@SnapsActivity,viewSnapActivity::class.java)
                intent.putExtra("ImageName",snapshot.child("ImageName").value as String)
                intent.putExtra("ImageURL",snapshot.child("ImageURL").value as String)
                intent.putExtra("Message",snapshot.child("Message").value as String)
                intent.putExtra("SnapKey",snapshot.key)

                startActivity(intent)


            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater=menuInflater
        inflater.inflate(R.menu.snapchat_menu,menu)

        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.createSnap){

            val intent= Intent(this,createSnapActivity::class.java)

            startActivity(intent)

        }
        else if(item.itemId==R.id.LogOut){

            auth1.signOut()

            finish()

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {

        super.onBackPressed()
        auth1.signOut()
    }

    fun snapAdded(){

        Log.i("Imageurl Printing",emailsList2.size.toString())


        if(emailsList2.size>0){



            tvSnaps.visibility= View.VISIBLE
            recycle_Snaps.visibility= View.VISIBLE
            NoSnaps.visibility= View.GONE





        }

        else {


            tvSnaps.visibility= View.GONE
            recycle_Snaps.visibility= View.GONE
            NoSnaps.visibility= View.VISIBLE


        }
    }
}