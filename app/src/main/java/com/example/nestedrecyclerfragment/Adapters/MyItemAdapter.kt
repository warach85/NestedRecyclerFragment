package com.example.nestedrecyclerfragment.Adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.nestedrecyclerfragment.*


class MyItemAdapter(private val context: Activity,
                    private val itemList:List<ItemData>?): RecyclerView.Adapter<MyItemAdapter.MyViewHolder>() {

lateinit var navController:NavController


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.layout_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList?.size?:0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txt_title.setText(itemList!!.reversed()[position].name)

        GlideApp.with(context).load(itemList.reversed()[position].image).into(holder.img_item)

        holder.setClick(object:IItemClickListener{
            override fun onItemClickListener(view: View, position: Int) {
               /* val sendIntent= Intent(context,MainActivity::class.java).apply {
                    putExtra("dbImageUrl",itemList[position].image)
                    putExtra("desc",itemList!![position].name)
                }*/
                val args = Bundle()
                //args.putStringArrayList("itemClickKey", arrayListOf("dbImageUrl","desc"))
                args.putString("dbImageUrl",itemList.reversed()[position].image)
                args.putString("desc",itemList.reversed()[position].name)
                context.findNavController(R.id.my_recycler_view).navigate(R.id.action_firstFragment_to_thirdFragment,args)
                //context.startActivity(sendIntent)

            }

        } )
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {

        var txt_title: TextView
        var img_item: ImageView
        lateinit var iItemClickListener: IItemClickListener

        fun setClick(iItemClickListener: IItemClickListener){
            this.iItemClickListener=iItemClickListener
        }

        init {
            txt_title=view.findViewById(R.id.tvTitle) as TextView
            img_item = view.findViewById(R.id.itemImage) as ImageView

            view.setOnClickListener(this)
        }


        override fun onClick(view: View?) {
            iItemClickListener.onItemClickListener(view!!,adapterPosition)
        }

    }
}