package com.example.nestedrecyclerfragment.Adapters

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nestedrecyclerfragment.ItemData
import com.example.nestedrecyclerfragment.ItemGroup
import com.example.nestedrecyclerfragment.R
import java.util.ArrayList

class MyGroupAdapter(private val context: Activity,
                     private val dataList:List<ItemGroup>?) : RecyclerView.Adapter<MyGroupAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_group,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList?.size?:0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var items: List<ItemData>? =dataList!![position].listItem
        val itemListAdapter=MyItemAdapter(context,items)
        //Log.d("Aman", items!![position].name.toString())
        holder.grpTitle.setText(dataList[position].headerTitle)
        //if(position==0) {holder.recycler_view_list.layoutParams.height=600}
        holder.recycler_view_list.setHasFixedSize(true)
        holder.recycler_view_list.layoutManager= LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)
        holder.recycler_view_list.adapter = itemListAdapter
        holder.recycler_view_list.isNestedScrollingEnabled=false

    }


    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

        var grpTitle: TextView
        var recycler_view_list: RecyclerView


        init {
            grpTitle=view.findViewById(R.id.grpTitle) as TextView
            recycler_view_list=view.findViewById(R.id.recycler_view_list) as RecyclerView
        }

    }
}