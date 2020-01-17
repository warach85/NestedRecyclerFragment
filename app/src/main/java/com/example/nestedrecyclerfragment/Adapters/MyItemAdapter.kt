package com.example.nestedrecyclerfragment.Adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.nestedrecyclerfragment.*
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class MyItemAdapter(private val context: Activity,
                    private val itemListHash:HashMap<String,ItemData>?, private val grpNo: Int): RecyclerView.Adapter<MyItemAdapter.MyViewHolder>() {

lateinit var navController:NavController
    val sortedItemList = makeSortedListWithDate() // Copied on 17/01/2020


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.layout_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemListHash?.size?:0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txt_date.setText(sortedItemList[position].date)
        holder.txt_freq.setText(sortedItemList[position].frequency)
        holder.txt_venue.setText(sortedItemList[position].venue)
        Log.d("AmanUp",holder.adapterPosition.toString())

        GlideApp.with(context).load(sortedItemList[position].image).into(holder.img_item)

        holder.setClick(object:IItemClickListener{
            override fun onItemClickListener(view: View, position: Int) {
               /*  To Start an activity from the adapter
               val sendIntent=Intent(context,ResultActivity::class.java).apply {
                    putExtra("imageUrl",sortedItemList[position].image)
                    putExtra("desc",sortedItemList[position].desc)
                }
                context.startActivity(sendIntent)*/

                // Region  To Start a fragment from an Recyclerview adapter
                val bundle = Bundle()
                bundle.apply {
                    putString("dbImageUrl",sortedItemList[position].image)
                    putString("desc",sortedItemList[position].desc)
                }

                val navController = view.findNavController()
                navController.navigate(R.id.action_firstFragment_to_thirdFragment,bundle)
                /*End Start a fragment from an Recyclerview adapter*/
            }

        } )
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {

        var card_item: CardView
        var txt_date:TextView
        var txt_freq:TextView
        var img_item:ImageView
        var txt_venue:TextView
        lateinit var iItemClickListener: IItemClickListener

        fun setClick(iItemClickListener: IItemClickListener){
            this.iItemClickListener=iItemClickListener
        }

        init {
            card_item=view.findViewById(R.id.itemCard)
            txt_date=view.findViewById(R.id.tvDate)      as TextView
            txt_freq=view.findViewById(R.id.tvFreq)      as TextView
            img_item = view.findViewById(R.id.itemImage) as ImageView
            txt_venue = view.findViewById(R.id.txtVenue) as TextView

            view.setOnClickListener(this)
        }


        override fun onClick(view: View?) {
            iItemClickListener.onItemClickListener(view!!,adapterPosition)
        }

    }

    fun makeSortedListWithDate():List<ItemData>{
        if(grpNo!=2) { // that is the outer list is not of Regular events
            for (item in itemListHash!!.values) {
                item.date_date =
                    LocalDate.parse(item.date, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            }
            val sortedItemList = itemListHash.values.sortedWith(compareBy { it.date_date })
            return sortedItemList
        }else return itemListHash!!.values.toList() // return this when outer list is of regularevents w/o dates
    }

}