package com.example.snapchatclone

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.items_recycler_view.view.*
import kotlinx.android.synthetic.main.snaps_item_recycler.view.*

class SnapsAdapter (val items:ArrayList<String>,val context: Context):
    RecyclerView.Adapter<SnapsAdapter.viewHolder>() {

    private lateinit var itemclick2: onItemClickListner2
    interface onItemClickListner2{
        fun onItemClick2(Po:Int)
    }

    fun setOnItemClickLisetner2(onItemClicklistner: onItemClickListner2){

        this.itemclick2=onItemClicklistner


    }


    class viewHolder(view: View,  listner:onItemClickListner2) : RecyclerView.ViewHolder(view) {

        val ll_Snap_item = view.ll_Snaps
        val tvPosition = view.tvSnapNumber
        val tvItem = view.tvSnaps_ID

        init {
            view.setOnClickListener{
                listner.onItemClick2(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        return viewHolder(
            LayoutInflater.from(context).inflate
                (R.layout.snaps_item_recycler, parent, false), itemclick2)

    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        val Snap=items.get(position)
        holder.tvPosition.text=(position+1).toString()
        holder.tvItem.text=Snap

    }

    override fun getItemCount(): Int {
        return items.size
    }

}
