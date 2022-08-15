package com.example.snapchatclone

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.items_recycler_view.view.*

class MembersAdapter(val items:ArrayList<String>,val context: Context):
    RecyclerView.Adapter<MembersAdapter.viewHolder>(){

    private lateinit var itemclick:onItemClickListner
    interface onItemClickListner{

        fun onItemClick(pos:Int)
    }

    fun setOnItemClickLisetner(onItemClicklistner: onItemClickListner){

        this.itemclick=onItemClicklistner


    }


    class viewHolder(view: View, listner: onItemClickListner) : RecyclerView.ViewHolder(view) {

        val ll_Member_item=view.ll_Members
        val tvPosition=view.tvPosition
        val tvItem = view.tvUser

        init {

            view.setOnClickListener{
                listner.onItemClick(adapterPosition)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            LayoutInflater.from(context).inflate
            (R.layout.items_recycler_view,parent,false), itemclick)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val Member=items.get(position)
        holder.tvPosition.text=(position+1).toString()
        holder.tvItem.text=Member


    }

    override fun getItemCount(): Int {
        return items.size
    }
}