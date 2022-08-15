package com.example.snapchatclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_member_choose.*

class MemberChooseActivity : AppCompatActivity() {

    var emailsList:ArrayList<String> =ArrayList()
    var keyList:ArrayList<String> =ArrayList()
    var memberAdapter:MembersAdapter ?=null

    var flag: Boolean=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_choose)

        recycle_Members.layoutManager= LinearLayoutManager(this)
        memberAdapter=MembersAdapter(emailsList,this)

        recycle_Members.adapter=memberAdapter






        val database = Firebase.database
        database.getReference().child("Members").addChildEventListener(object:ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                val e= snapshot.child("Email").value as String

                emailsList.add(e)

                keyList.add(snapshot.key!!)

                memberAdapter?.notifyDataSetChanged()

                dataChanged()




            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }



        })

        memberAdapter!!.setOnItemClickLisetner(object: MembersAdapter.onItemClickListner{
            override fun onItemClick(pos: Int) {
                val snapMap:Map<String,String?> = mapOf(
                    "from" to FirebaseAuth.getInstance().currentUser!!.email!!,
                    "ImageName" to intent.getStringExtra("ImageName"),
                    "ImageURL"  to intent.getStringExtra("ImageURL") ,
                    "Message" to intent.getStringExtra("Message"))

                FirebaseDatabase.getInstance().getReference().child("Members").child(
                    keyList.get(pos)).child("snaps").push().setValue(snapMap)

                val intent= Intent(this@MemberChooseActivity,SnapsActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)

            }

        })














    }

    fun dataChanged(){

        if(emailsList.size>0){



            tvMembers.visibility= View.VISIBLE
            recycle_Members.visibility=View.VISIBLE
            NoData.visibility=View.GONE





        }

        else {


            tvMembers.visibility= View.GONE
            recycle_Members.visibility=View.GONE
            NoData.visibility=View.VISIBLE


        }
    }



}